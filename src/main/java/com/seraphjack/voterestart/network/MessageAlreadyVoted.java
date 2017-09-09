package com.seraphjack.voterestart.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;

public class MessageAlreadyVoted implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class MessageHandler implements IMessageHandler<MessageAlreadyVoted, IMessage> {
        @Override
        public IMessage onMessage(MessageAlreadyVoted message, MessageContext ctx) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentTranslation(StatCollector.translateToLocal("voterestart.alreadyVoted")));
            return null;
        }
    }
}
