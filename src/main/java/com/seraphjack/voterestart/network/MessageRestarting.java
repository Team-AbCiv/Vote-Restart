package com.seraphjack.voterestart.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;

public class MessageRestarting implements IMessage {
    public int seconds;

    @Override
    public void fromBytes(ByteBuf buf) {
        seconds = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(seconds);
    }

    public static class MessageHandler implements IMessageHandler<MessageRestarting, IMessage> {

        @Override
        public IMessage onMessage(MessageRestarting message, MessageContext ctx) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentTranslation(StatCollector.translateToLocalFormatted("voterestart.stopInfo", message.seconds)));
            return null;
        }
    }
}
