package com.jmteam.igauntlet.network;

import com.jmteam.igauntlet.Infinity;
import com.jmteam.igauntlet.network.packets.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Infinity.MODID);

    public static void init() {
        NETWORK.registerMessage(MessageStone.Handler.class, MessageStone.class, 1, Side.SERVER);
        NETWORK.registerMessage(MessageNotAdded.Handler.class, MessageNotAdded.class, 2, Side.SERVER);
        NETWORK.registerMessage(MessageSpace.Handler.class, MessageSpace.class, 3, Side.SERVER);
        NETWORK.registerMessage(MessageNoCoords.Handler.class, MessageNoCoords.class, 4, Side.SERVER);
        NETWORK.registerMessage(MessageSnap.Handler.class, MessageSnap.class, 5, Side.SERVER);
        NETWORK.registerMessage(MessageWritePortal.Handler.class, MessageWritePortal.class, 6, Side.SERVER);
        NETWORK.registerMessage(MessagePortalTeleport.Handler.class, MessagePortalTeleport.class, 7, Side.SERVER);
    }
}
