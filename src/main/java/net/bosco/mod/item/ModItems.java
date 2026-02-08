package net.bosco.mod.item;

import net.bosco.mod.BoscoMod;
import net.bosco.mod.entity.ModEntities;
import net.bosco.mod.item.custom.BoscoSpawnItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item TEST_ITEM = registerItem("test_item",
            new Item(new Item.Settings()));

    // Keep the spawn egg for creative/testing
    public static final Item BOSCO_SPAWN_EGG = registerItem("bosco_spawn_egg",
            new SpawnEggItem(ModEntities.BOSCO, 0x000000, 0xFFFFFF, new Item.Settings()));

    // Add the custom spawner that sets the owner
    public static final Item BOSCO_SPAWNER = registerItem("bosco_spawner",
            new BoscoSpawnItem(new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(BoscoMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        BoscoMod.LOGGER.info("Registering Mod Items for " + BoscoMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(TEST_ITEM);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            //entries.add(BOSCO_SPAWN_EGG); // removed as we have a custom spawner item now
            entries.add(BOSCO_SPAWNER);
        });
    }
}