package com.seraphjack.voterestart.network;

import com.seraphjack.voterestart.VoteRestart;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;

public class MessageRestartInfo implements IMessage {
    public int votes;
    public int currentPlayers;
    public double votesToRestart;

    @Override
    public void fromBytes(ByteBuf buf) {
        votesToRestart = buf.readDouble();
        votes = buf.readInt();
        currentPlayers = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(votesToRestart);
        buf.writeInt(votes);
        buf.writeInt(currentPlayers);
    }

    public static class MessageHandler implements IMessageHandler<MessageRestartInfo, IMessage> {
        @Override
        public IMessage onMessage(MessageRestartInfo message, MessageContext ctx) {
            VoteRestart.server.getConfigurationManager().sendChatMsg(new ChatComponentTranslation(StatCollector.translateToLocalFormatted("voterestart.info.voteInfo", ((double)message.votes/(double)message.currentPlayers *100),message.votesToRestart*100)));
            return null;
        }
    }
}
