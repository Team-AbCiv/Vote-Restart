package com.seraphjack.voterestart;

import com.seraphjack.voterestart.event.EventLoader;
import com.seraphjack.voterestart.items.ItemLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
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
    private static MinecraftServer server;
    private static boolean isRestarting = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        votes = 0;
        logger.info("Vote Restart loaded");
        new ConfigLoader(e);
        new ItemLoader();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
        server = e.getServer();
        voteList = new LinkedList<String>();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        new CraftingLoader();
        new AchievementLoader();
        new EventLoader();
    }

    public static void vote(EntityPlayer player, World world) {
        if (player instanceof FakePlayer) {
            player.clearItemInUse();
            return;
        }

        int i;
        for (i = 0; i < voteList.size(); i++) {
            if (player.getGameProfile().getName().equals(voteList.get(i))) {
                player.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("voterestart.alreadyVoted")));
                if (votes >= Math.ceil((double) server.getCurrentPlayerCount() * ConfigLoader.votes) && !isRestarting)
                    restartServer();
                return;
            }
        }

        voteList.add(player.getGameProfile().getName());
        votes++;
        String info = player.getGameProfile().getName() + StatCollector.translateToLocal("voterestart.info.display0")
                + votes + StatCollector.translateToLocal("voterestart.info.display1")
                + server.getCurrentPlayerCount() + StatCollector.translateToLocal("voterestart.info.display2");
        String info2 = StatCollector.translateToLocal("voterestart.info.display3")
                + (int) Math.ceil((double) server.getCurrentPlayerCount() * ConfigLoader.votes) + StatCollector.translateToLocal("voterestart.info.display4");
        logger.info(info);
        server.getConfigurationManager().sendChatMsg(new ChatComponentTranslation(info));
        server.getConfigurationManager().sendChatMsg(new ChatComponentTranslation(info2));
        if (votes >= Math.ceil((double) server.getCurrentPlayerCount() * ConfigLoader.votes) && !isRestarting) {
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
                    server.getConfigurationManager().sendChatMsg(new ChatComponentTranslation(StatCollector.translateToLocalFormatted("voterestart.stopInfo", i)));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                server.stopServer();
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
                server.getConfigurationManager().sendChatMsg(new ChatComponentTranslation(StatCollector.translateToLocalFormatted("voterestart.devoteInfo", player.getGameProfile().getName(), votes)));
            }
        }
    }
}
