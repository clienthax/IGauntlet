package com.igauntlet.proxy;

import com.igauntlet.client.util.ModKeyBinds;
import com.igauntlet.init.ModEntities;
import com.igauntlet.util.handlers.RenderHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {

    public static void registerRenders() {
    }


    @Override
    public void preInit(FMLPreInitializationEvent event) {
        RenderHandler.registerEntityRenders();
        ModEntities.registerEntities();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        ModKeyBinds.init();
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

}
