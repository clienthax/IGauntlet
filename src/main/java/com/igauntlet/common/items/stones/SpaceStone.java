package com.igauntlet.common.items.stones;

import com.igauntlet.Infinity;
import com.igauntlet.config.ModConfig;
import com.igauntlet.init.ModItems;
import com.igauntlet.network.NetworkHandler;
import com.igauntlet.network.packets.MessageSpaceDrift;
import com.igauntlet.tabs.InfinityTabs;
import com.igauntlet.util.handlers.helpers.IHasModel;
import com.igauntlet.util.helpers.PlayerHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpaceStone extends Item implements IHasModel {

    public SpaceStone(String name) {
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(InfinityTabs.infinityTabs);
        setMaxStackSize(1);
        setMaxDamage(4500);

        ModItems.ITEMS.add(this);
    }


    int timeout = 0;

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);


        EntityPlayer player = (EntityPlayer) entityIn;
        int maxtimeout = ModConfig.Gauntlet.SpaceStone.SpaceDriftTimeout * 20;

        if (worldIn.isRemote) {
            if (isSelected) {
                timeout++;


                if (timeout == 50% maxtimeout) {
                    PlayerHelper.sendMessageClient(player,"stones.space.donthold", true);
                }

                if (timeout == maxtimeout) {
                    PlayerHelper.sendMessageClient(player,"stones.space.drifted", true);
                }

            }

            if (timeout > maxtimeout) {
                timeout = 0;
                int max = ModConfig.Gauntlet.SpaceStone.MaximumDriftRange;
                int min = ModConfig.Gauntlet.SpaceStone.MinimumDriftRange;
                int random = (int) (Math.random() * max + min);
                BlockPos pos1 = new BlockPos(random, random, random);
                BlockPos pos2 = worldIn.getTopSolidOrLiquidBlock(pos1);
                NetworkHandler.NETWORK.sendToServer(new MessageSpaceDrift(pos2, player.getEntityId()));
            }
        }
    }


    @Override
    public void registerModels() {
        Infinity.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
