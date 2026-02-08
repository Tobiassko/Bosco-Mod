package net.bosco.mod.entity;

import net.bosco.mod.BoscoMod;
import net.bosco.mod.entity.custom.BoscoEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<BoscoEntity> BOSCO = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(BoscoMod.MOD_ID, "bosco"),
            EntityType.Builder.create(BoscoEntity::new, SpawnGroup.CREATURE)
                            .dimensions(0.6f, 1f).build());

    public static void registerModEntities() {
        BoscoMod.LOGGER.info("Registering Mod Entities for "+ BoscoMod.MOD_ID);
    }
}
