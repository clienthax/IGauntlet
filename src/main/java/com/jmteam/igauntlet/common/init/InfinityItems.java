package com.jmteam.igauntlet.common.init;

import com.jmteam.igauntlet.Infinity;
import com.jmteam.igauntlet.common.items.ItemBase;
import com.jmteam.igauntlet.common.items.ItemInfinityGauntlet;
import com.jmteam.igauntlet.common.items.ItemMixTape;
import com.jmteam.igauntlet.common.items.clothing.ItemEyeOfAgamotto;
import com.jmteam.igauntlet.common.items.stones.*;
import com.jmteam.igauntlet.common.items.tools.ItemDwarfhammer;
import com.jmteam.igauntlet.tabs.InfinityTabs;
import com.jmteam.igauntlet.util.handlers.SoundsHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class InfinityItems {
    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static Item infinity_gauntlet = registerItem(new ItemInfinityGauntlet("infinity_gauntlet"), true);
    public static Item mixtape = registerItem(new ItemMixTape("awesome_mix", SoundsHandler.AWESOMEMIX), true);
    public static Item uru_ingot = registerItem(new ItemBase("uru_ingot"), true);
    public static Item mind_stone = registerItem(new ItemMindStone("mind_stone"), true);
    public static Item reality_stone = registerItem(new ItemRealityStone("reality_stone"), true);
    public static Item time_stone = registerItem(new ItemTimeStone("time_stone"), true);
    public static Item space_stone = registerItem(new ItemSpaceStone("space_stone"), true);
    public static Item power_stone = registerItem(new ItemPowerStone("power_stone"), true);
    public static Item soul_stone = registerItem(new ItemSoulStone("soul_stone"), true);
    public static Item dwarf_hammer = registerItem(new ItemDwarfhammer("dwarf_hammer"), true);
    public static Item necklace = registerItem(new ItemEyeOfAgamotto("eye_agamotto"), true);

    public static void registerRenders() {
        for(Item item : ITEMS) {
            registerRender(item);
        }
    }

    public static Item registerItem(Item item, boolean tab) {
        if (tab)
            item.setCreativeTab(InfinityTabs.infinityTabs);
        ITEMS.add(item);
        return item;
    }

    public static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    public static void registerRenderMeta(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Infinity.MODID, name), "inventory"));
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(InfinityItems.ITEMS.toArray(new Item[0]));
    }
}





