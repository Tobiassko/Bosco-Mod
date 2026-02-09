package net.bosco.mod.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BoscoEntity extends FlyingEntity {
    private static final TrackedData<Boolean> ATTACKING =
            DataTracker.registerData(BoscoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;
    private int idleAnimationTimeout = 0;

    @Nullable
    private UUID ownerUuid;

    public BoscoEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    // ==================== Owner Logic ====================

    public void setOwner(PlayerEntity player) {
        this.ownerUuid = player.getUuid();
    }

    @Nullable
    public UUID getOwnerUuid() {
        return this.ownerUuid;
    }

    @Nullable
    public PlayerEntity getOwner() {
        if (this.ownerUuid == null) return null;
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            Entity entity = serverWorld.getEntity(this.ownerUuid);
            if (entity instanceof PlayerEntity player) {
                return player;
            }
        }
        return null;
    }

    // ==================== Save/Load Owner ====================

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
        }
    }

    // ==================== Navigation ====================

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    // ==================== Goals ====================

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new BoscoMeleeAttackGoal(this, 2));
        this.goalSelector.add(2, new BoscoFollowOwnerGoal(this, 5, 20.0F, 6.0F));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, new BoscoTrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new BoscoAttackWithOwnerGoal(this));
    }

    // ==================== Attributes ====================

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 5)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5)
                .add(EntityAttributes.GENERIC_ARMOR, 15)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48);
    }
    // ==================== Ignore I-Frames ====================

    @Override
    public boolean tryAttack(Entity target) {
        // Reset the target's invincibility frames so our damage always goes through
        if (target instanceof LivingEntity livingTarget) {
            livingTarget.hurtTime = 0;
            livingTarget.timeUntilRegen = 0;
        }

        float damage = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        DamageSource damageSource = this.getDamageSources().mobAttack(this);

        boolean success = target.damage(damageSource, damage);

        if (success && target instanceof LivingEntity livingTarget) {
            // Apply knockback
            double knockback = this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
            if (knockback > 0) {
                livingTarget.takeKnockback(
                        knockback * 0.5,
                        Math.sin(this.getYaw() * ((float) Math.PI / 180F)),
                        -Math.cos(this.getYaw() * ((float) Math.PI / 180F))
                );
            }

            // Reset i-frames again AFTER the hit so the next attack also goes through
            livingTarget.hurtTime = 0;
            livingTarget.timeUntilRegen = 0;
        }

        return success;
    }

    // ==================== Prevent Fall Damage ====================

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    // ==================== Don't Attack Owner ====================

    @Override
    public boolean canTarget(LivingEntity target) {
        if (target instanceof PlayerEntity player) {
            if (player.getUuid().equals(this.ownerUuid)) {
                return false;
            }
        }
        return super.canTarget(target);
    }

    // ==================== Animation ====================

    private void setupAnimationStates() {
        // Idle animation
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }

        // Attack animation - use the synced state
        if (this.isAttackingState() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 15;
            attackAnimationState.start(this.age);
        } else {
            --this.attackAnimationTimeout;
        }
        if (!this.isAttackingState()) {
            attackAnimationState.stop();
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ATTACKING, false);
    }
    public void setAttackingState(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }

    public boolean isAttackingState() {
        return this.dataTracker.get(ATTACKING);
    }

    @Override
    public void tick() {
        super.tick();

        // Auto-assign nearest player as owner if no owner set
        if (!this.getWorld().isClient() && this.ownerUuid == null) {
            PlayerEntity nearestPlayer = this.getWorld().getClosestPlayer(this, 10.0);
            if (nearestPlayer != null) {
                this.setOwner(nearestPlayer);
            }
        }

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }

        // debug info - shows current HP, attack damage, speed, and current goal (attacking/following/idle)
        boolean debugMode = false;

        if (!this.getWorld().isClient() && debugMode) {
            String currentGoal = "None";
            if (this.getTarget() != null) {
                currentGoal = "Attacking: " + this.getTarget().getName().getString();
            } else if (this.getOwner() != null) {
                double dist = this.squaredDistanceTo(this.getOwner());
                currentGoal = "Following (dist: " + String.format("%.1f", Math.sqrt(dist)) + ")";
            }

            String debugText = String.format("HP: %.1f | ATK: %.1f | SPD: %.2f | %s",
                    this.getHealth(),
                    this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE),
                    this.getVelocity().length(),
                    currentGoal
            );

            this.setCustomName(net.minecraft.text.Text.literal(debugText));
            this.setCustomNameVisible(true);
        }

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    // ==================== Custom Goals ====================

    /**
     * MeleeAttackGoal requires a PathAwareEntity, but FlyingEntity extends MobEntity, not PathAwareEntity
     * thus this custom melee attack goal is implemented to allow the Bosco to attack while flying without needing to implement PathAwareEntity
     */

    static class BoscoMeleeAttackGoal extends Goal {
        private final BoscoEntity bosco;
        private final double speed;
        private int cooldown;

        public BoscoMeleeAttackGoal(BoscoEntity bosco, double speed) {
            this.bosco = bosco;
            this.speed = speed;
        }

        @Override
        public boolean canStart() {
            return this.bosco.getTarget() != null && this.bosco.getTarget().isAlive();
        }

        @Override
        public boolean shouldContinue() {
            return this.bosco.getTarget() != null && this.bosco.getTarget().isAlive();
        }

        @Override
        public void stop() {
            this.bosco.setAttacking(false);
            this.bosco.setAttackingState(false);
            this.bosco.getNavigation().stop();
        }

        @Override
        public void tick() {
            LivingEntity target = this.bosco.getTarget();
            if (target == null) return;

            this.bosco.getLookControl().lookAt(target, 30.0F, 30.0F);
            this.bosco.getNavigation().startMovingTo(target, this.speed);

            double distanceSq = this.bosco.squaredDistanceTo(target);
            double attackReach = (this.bosco.getWidth() * 2.0F) * (this.bosco.getWidth() * 2.0F) + target.getWidth();

            this.cooldown--;

            if (distanceSq <= attackReach + 4.0) {
                this.bosco.setAttacking(true);
                this.bosco.setAttackingState(true);  // Sync to client
                if (this.cooldown <= 0) {
                    this.cooldown = 4;
                    this.bosco.tryAttack(target);
                }
            } else {
                this.bosco.setAttacking(false);
                this.bosco.setAttackingState(false);  // Sync to client
            }
        }
    }

    /**
     * Follow the owner
     */
    static class BoscoFollowOwnerGoal extends Goal {
        private final BoscoEntity bosco;
        private final double speed;
        private final float maxDistance;
        private final float minDistance;
        @Nullable
        private PlayerEntity owner;

        public BoscoFollowOwnerGoal(BoscoEntity bosco, double speed, float maxDistance, float minDistance) {
            this.bosco = bosco;
            this.speed = speed;
            this.maxDistance = maxDistance;
            this.minDistance = minDistance;
        }

        @Override
        public boolean canStart() {
            PlayerEntity owner = this.bosco.getOwner();
            if (owner == null) return false;
            if (this.bosco.squaredDistanceTo(owner) < (double) (this.minDistance * this.minDistance)) return false;
            this.owner = owner;
            return true;
        }

        @Override
        public boolean shouldContinue() {
            if (this.owner == null) return false;
            if (this.bosco.getNavigation().isIdle()) return false;
            return this.bosco.squaredDistanceTo(this.owner) > (double) (this.minDistance * this.minDistance);
        }

        @Override
        public void start() {
            this.bosco.getNavigation().startMovingTo(this.owner, this.speed);
        }

        @Override
        public void stop() {
            this.owner = null;
            this.bosco.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (this.owner == null) return;

            this.bosco.getLookControl().lookAt(this.owner, 10.0F, (float) this.bosco.getMaxLookPitchChange());

            if (this.bosco.squaredDistanceTo(this.owner) >= (double) (this.maxDistance * this.maxDistance)) {
                // Teleport if too far
                this.bosco.teleport(
                        this.owner.getX() + (this.bosco.getRandom().nextDouble() - 0.5) * 2,
                        this.owner.getY() + 1,
                        this.owner.getZ() + (this.bosco.getRandom().nextDouble() - 0.5) * 2,
                        false
                );
            } else {
                this.bosco.getNavigation().startMovingTo(this.owner, this.speed);
            }
        }
    }

    /**
     * Retaliate against mobs that attack the owner
     */
    static class BoscoTrackOwnerAttackerGoal extends TrackTargetGoal {
        private final BoscoEntity bosco;
        private LivingEntity attacker;
        private int lastAttackedTime;

        public BoscoTrackOwnerAttackerGoal(BoscoEntity bosco) {
            super(bosco, false);
            this.bosco = bosco;
        }

        @Override
        public boolean canStart() {
            PlayerEntity owner = this.bosco.getOwner();
            if (owner == null) return false;
            this.attacker = owner.getAttacker();
            if (this.attacker == null) return false;
            int lastAttackedTime = owner.getLastAttackedTime();
            if (lastAttackedTime == this.lastAttackedTime) return false;
            // Don't attack the owner or other Boscos
            if (this.attacker instanceof BoscoEntity) return false;
            if (this.attacker == owner) return false;
            this.lastAttackedTime = lastAttackedTime;
            return true;
        }

        @Override
        public void start() {
            this.mob.setTarget(this.attacker);
            super.start();
        }
    }

    /**
     * Attack whatever the owner attacks
     */
    static class BoscoAttackWithOwnerGoal extends TrackTargetGoal {
        private final BoscoEntity bosco;
        private LivingEntity attacking;
        private int lastAttackTime;

        public BoscoAttackWithOwnerGoal(BoscoEntity bosco) {
            super(bosco, false);
            this.bosco = bosco;
        }

        @Override
        public boolean canStart() {
            PlayerEntity owner = this.bosco.getOwner();
            if (owner == null) return false;
            this.attacking = owner.getAttacking();
            if (this.attacking == null) return false;
            int lastAttackTime = owner.getLastAttackTime();
            if (lastAttackTime == this.lastAttackTime) return false;
            // Don't attack the owner or other Boscos
            if (this.attacking instanceof BoscoEntity) return false;
            if (this.attacking == owner) return false;
            this.lastAttackTime = lastAttackTime;
            return true;
        }

        @Override
        public void start() {
            this.mob.setTarget(this.attacking);
            super.start();
        }
    }
}