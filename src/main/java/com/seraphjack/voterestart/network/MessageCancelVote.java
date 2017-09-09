package com.seraphjack.voterestart.network;

import com.seraphjack.voterestart.VoteRestart;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;

public class MessageCancelVote implements IMessage {
    public int votes;
    public String player;

    @Override
    public void fromBytes(ByteBuf buf) {
        votes = buf.readInt();
        player = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(votes);
        ByteBufUtils.writeUTF8String(buf, player);
    }

    public static class MessageHandler implements IMessageHandler<MessageCancelVote, IMessage> {

        @Override
        public IMessage onMessage(MessageCancelVote message, MessageContext ctx) {
            VoteRestart.server.getConfigurationManager().sendChatMsg(new ChatComponentTranslation(StatCollector.translateToLocalFormatted("voterestart.devoteInfo",message.player,message.votes)));
            return null;
        }
    }
}
