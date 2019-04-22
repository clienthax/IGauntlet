package com.jmteam.igauntlet.network.packets;

import com.jmteam.igauntlet.common.blocks.InfinityBlocks;
import com.jmteam.igauntlet.common.damage.IDamageSource;
import com.jmteam.igauntlet.common.items.InfinityItems;
import com.jmteam.igauntlet.init.InfinityConfig;
import com.jmteam.igauntlet.util.handlers.SoundsHandler;
import com.jmteam.igauntlet.util.helpers.PlayerHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.jmteam.igauntlet.common.function.gems.GemPower.WriteAsh;


public class MessageSnap implements IMessage {

    public static boolean snap;
    public static final List<EntityLiving> SNAPENTITY = new ArrayList<EntityLiving>();

    public MessageSnap() {
    }

    public MessageSnap(boolean snap) {
        this.snap = snap;
    }

    public void fromBytes(ByteBuf buf) {
        this.snap = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.snap);
    }

    public static class Handler implements IMessageHandler<MessageSnap, IMessage> {

        @Override
        public IMessage onMessage(MessageSnap message, MessageContext ctx) {
            ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                EntityPlayerMP playerIn = ctx.getServerHandler().player;
                ItemStack stack = playerIn.getActiveItemStack();
                NBTTagCompound nbt = stack.getTagCompound();
                boolean CanSnap = InfinityConfig.Gauntlet.Snap;
                boolean Snapinit = false;
                int extend = InfinityConfig.Gauntlet.ExtensionRange;
                int passentity = 0;
                Random random = new Random();
                int r = random.nextInt(10);
                boolean ticklimit = true;

                //May need a merge as it should be possible
                if (CanSnap && PlayerHelper.getPDataInt(playerIn, "snapped") == 0) {

                    // Entity Counter
                    if (!(playerIn.getHeldItemMainhand().getItem() == InfinityItems.infinity_gauntlet)) return;
                    for (EntityLiving targetentity : playerIn.world.getEntitiesWithinAABB(EntityLiving.class, playerIn.getEntityBoundingBox().grow(extend, extend, extend))) {
                        SNAPENTITY.add(targetentity);
                        Snapinit = true;
                        passentity++;
                    }

                    // Snap
                    if (Snapinit) {
                        for (EntityLiving e : playerIn.world.getEntitiesWithinAABB(EntityLiving.class, playerIn.getEntityBoundingBox().grow(extend, extend, extend))) {

                            int halfentity = passentity / 2;

                            if (halfentity > 0) {
                                EntityLiving targetentity = SNAPENTITY.get(halfentity);

                                if (targetentity instanceof EntityDragon) {
                                    targetentity.setDead();
                                    return;
                                }

                                EntityLiving entity = targetentity;
                                if (!targetentity.getIsInvulnerable()) {
                                    Block blk = InfinityBlocks.ash_pile;
                                    BlockPos pos0 = new BlockPos(targetentity.posX, targetentity.posY, targetentity.posZ);
                                    IBlockState state0 = blk.getDefaultState();
                                    targetentity.world.setBlockState(pos0, state0);
                                    WriteAsh(pos0, playerIn.world, entity);
                                    targetentity.attackEntityFrom(IDamageSource.SNAP, targetentity.getMaxHealth());
                                    passentity--;
                                }
                            }
                            if (SNAPENTITY.size() > 1) {
                                if (ticklimit) {
                                    playerIn.world.playSound(null, playerIn.getPosition(), SoundsHandler.SNAP, SoundCategory.HOSTILE, 1F, 1F);
                                    PlayerHelper.setPDataInt(playerIn, "snapped", InfinityConfig.Gauntlet.SnapCooldown * 20);
                                    if (r == 3) {
                                        playerIn.world.playSound(null, playerIn.getPosition(), SoundsHandler.IDONTFEELGOOD, SoundCategory.AMBIENT, 1F, 1F);
                                    }
                                    ticklimit = false;
                                }
                            } else {
                                PlayerHelper.sendMessage(playerIn, "gauntlet.snap.notenough", true);
                            }
                        }
                    }
                    SNAPENTITY.clear();
                    passentity = 0;
                }
            });
            return null;
        }
    }
}
