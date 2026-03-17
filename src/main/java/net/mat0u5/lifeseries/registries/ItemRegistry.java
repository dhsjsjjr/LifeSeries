package net.mat0u5.lifeseries.registries;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.mat0u5.lifeseries.Main;
import net.mat0u5.lifeseries.item.ValentinesFlowerItem;
import net.mat0u5.lifeseries.utils.other.IdentifierHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class ItemRegistry {
    public static final Item VALENTINES_FLOWER = new ValentinesFlowerItem(new FabricItemSettings().maxCount(1));

    public static void registerItems() {
        Registry.register(BuiltInRegistries.ITEM, IdentifierHelper.mod("valentines_flower"), VALENTINES_FLOWER);
    }
}