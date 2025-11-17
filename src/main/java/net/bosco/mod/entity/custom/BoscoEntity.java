package net.bosco.mod.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.world.World;

public class BoscoEntity extends FlyingEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimer = 0;

    public BoscoEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        // SwimGoal: ensure the entity can swim/leave water when submerged
        this.goalSelector.add(0, new SwimGoal(this));
        // FlyGoal: basic flying movement behavior
        this.goalSelector.add(1, new FlyGoal(this));
        // WanderAroundFarGoal: random wandering while flying (speed 1.0)
        this.goalSelector.add(2, new );
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        // LookAroundGoal: idle looking behavior
        this.goalSelector.add(6, new LookAroundGoal(this));
    }
}
