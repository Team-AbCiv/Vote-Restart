package com.seraphjack.voterestart.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;

public class MessageVoteInfo implements IMessage {
    public String player;

    @Override
    public void fromBytes(ByteBuf buf) {
        player = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, player);
    }

    public static class MessageHandler implements IMessageHandler<MessageVoteInfo,IMessage>{

        @Override
        public IMessage onMessage(MessageVoteInfo message, MessageContext ctx) {
            MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentTranslation(StatCollector.translateToLocalFormatted("voterestart.playerVoteInfo",message.player)));
            return null;
        }
    }
}
