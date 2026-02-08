package net.bosco.mod.item.custom;

import net.bosco.mod.entity.ModEntities;
import net.bosco.mod.entity.custom.BoscoEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BoscoSpawnItem extends Item {
    public BoscoSpawnItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            // Spawn Bosco 2 blocks in front of the player
            Vec3d lookDir = player.getRotationVector();
            double spawnX = player.getX() + lookDir.x * 2;
            double spawnY = player.getY() + 1;
            double spawnZ = player.getZ() + lookDir.z * 2;

            BoscoEntity bosco = new BoscoEntity(ModEntities.BOSCO, serverWorld);
            bosco.refreshPositionAndAngles(spawnX, spawnY, spawnZ, 0, 0);
            bosco.setOwner(player);
            serverWorld.spawnEntity(bosco);

            // Optionally consume the item
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        return TypedActionResult.success(stack, world.isClient());
    }
}