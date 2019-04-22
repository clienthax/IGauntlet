package com.jmteam.igauntlet.common.items.tools;

import com.jmteam.igauntlet.tabs.InfinityTabs;
import net.minecraft.item.Item;

public class ItemDwarfhammer extends Item {

    public ItemDwarfhammer(String name) {
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(InfinityTabs.infinityTabs);
        setMaxStackSize(1);
        setMaxDamage(4500);
    }
}