package com.seraphjack.voterestart;

import com.seraphjack.voterestart.event.EventLoader;
import com.seraphjack.voterestart.items.ItemLoader;
import com.seraphjack.voterestart.network.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.FakePlayer;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

@Mod(modid = VoteRestart.MODID, version = VoteRestart.VERSION, name = VoteRestart.NAME)
public class VoteRestart {
    public static final String MODID = "voterestart";
    public static final String VERSION = "@version@";
    public static final String NAME = "VoteRestart";
    private static Logger logger;
    private static int votes;
    private static List<String> voteList;
    public static MinecraftServer server;
    private static boolean isRestarting = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        votes = 0;
        logger.info("Vote Restart loaded");
        new ConfigLoader(e);
        new ItemLoader();
        new NetworkLoader();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
        voteList = new LinkedList<String>();
        server = e.getServer();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        new CraftingLoader();
        new EventLoader();
    }

    public static void vote(EntityPlayer player) {
        if (player instanceof FakePlayer) {
            player.clearItemInUse();
            return;
        }

        for (String aVoteList : voteList) {
            if (player.getGameProfile().getName().equals(aVoteList)) {
                NetworkLoader.instance.sendTo(new MessageAlreadyVoted(), (EntityPlayerMP) player);
                return;
            }
        }
        MessageVoteInfo msg = new MessageVoteInfo();
        msg.player = player.getGameProfile().getName();
        NetworkLoader.instance.sendToAll(msg);

        voteList.add(player.getGameProfile().getName());
        votes++;
        update();
    }

    private static void update() {
        MessageRestartInfo msg = new MessageRestartInfo();
        msg.votesToRestart = ConfigLoader.votes;
        msg.votes = votes;
        msg.currentPlayers = server.getCurrentPlayerCount();
        NetworkLoader.instance.sendToAll(msg);
        if ((double)votes/(double)server.getCurrentPlayerCount() >= ConfigLoader.votes) {
            restartServer();
        }
    }

    private static void restartServer() {
        isRestarting = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 10;
                for (; i >= 0; i--) {
                    MessageRestarting msg = new MessageRestarting();
                    msg.seconds = i;
                    NetworkLoader.instance.sendToAll(msg);
                    logger.info(StatCollector.translateToLocalFormatted("voterestart.stopInfo", i));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                logger.info("Voted player list:");
                for(String aVoteList : voteList)
                    logger.info(aVoteList);
                MinecraftServer.getServer().initiateShutdown();
            }
        }).start();
    }

    public static void deVote(EntityPlayer player) {
        if (isRestarting)
            return;
        for (int i = 0; i < voteList.size(); i++) {
            if (voteList.get(i).equals(player.getGameProfile().getName())) {
                votes--;
                voteList.remove(i);
                MessageCancelVote msg = new MessageCancelVote();
                msg.player = player.getGameProfile().getName();
                msg.votes = votes;
                NetworkLoader.instance.sendToAll(msg);
                update();
            }
        }
    }
}
