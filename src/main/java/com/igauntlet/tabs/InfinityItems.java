package com.igauntlet.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class InfinityItems extends CreativeTabs {
    public InfinityItems(String label) {
        super(label);
        this.setBackgroundImageName("igauntlet.png");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(com.igauntlet.init.InfinityItems.INFINITY_GAUNTLET);
    }
}

