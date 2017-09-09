package com.seraphjack.voterestart.network;

import com.seraphjack.voterestart.VoteRestart;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkLoader {
    public static SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(VoteRestart.MODID);

    private static int messageId = 0;

    public NetworkLoader() {
        registerMessage(MessageAlreadyVoted.MessageHandler.class, MessageAlreadyVoted.class, Side.CLIENT);
        registerMessage(MessageCancelVote.MessageHandler.class, MessageCancelVote.class, Side.CLIENT);
        registerMessage(MessageRestartInfo.MessageHandler.class, MessageRestartInfo.class, Side.CLIENT);
        registerMessage(MessageRestarting.MessageHandler.class, MessageRestarting.class, Side.CLIENT);
        registerMessage(MessageVoteInfo.MessageHandler.class, MessageVoteInfo.class, Side.CLIENT);

    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
        instance.registerMessage(messageHandler, requestMessageType, messageId++, side);
    }
}
