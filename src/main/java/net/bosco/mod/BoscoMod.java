package net.bosco.mod;

import net.bosco.mod.block.ModBlocks;
import net.bosco.mod.entity.ModEntities;
import net.bosco.mod.entity.custom.BoscoEntity;
import net.bosco.mod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoscoMod implements ModInitializer {
	public static final String MOD_ID = "boscomod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModEntities.registerModEntities();
        FabricDefaultAttributeRegistry.register(ModEntities.BOSCO, BoscoEntity.createAttributes());
	}
}