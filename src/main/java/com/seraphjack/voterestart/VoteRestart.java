package com.seraphjack.voterestart;

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

@Mod(modid = VoteRestart.MODID,version = VoteRestart.VERSION,name = VoteRestart.NAME)
public class VoteRestart {
    public static final String MODID = "voterestart";
    public static final String VERSION = "@version@";
    public static final String NAME = "VoteRestart";
    private static Logger  logger;
    private static int votes;
    private static String voteList[];
    private static MinecraftServer server;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        logger = e.getModLog();
        votes=0;
        logger.info("Vote Restart loaded");
        new ConfigLoader(e);
        new ItemLoader();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e){
        server = e.getServer();
        voteList = new String[server.getMaxPlayers()];
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
        new CraftingLoader();
        new AchievementLoader();
    }

    public static void vote(EntityPlayer player, World world){
        if(player instanceof FakePlayer){
            server.getConfigurationManager().sendChatMsg(new ChatComponentTranslation("Do not use right clicker to vote!"));
            player.clearItemInUse();
            return;
        }

        int i;
        for (i = 0; voteList[i] != null; i++) {
            if (player.getGameProfile().getName().equals(voteList[i])) {
                player.addChatMessage(new ChatComponentTranslation("You have already voted!!!"));
                if (votes >= Math.ceil((double)server.getCurrentPlayerCount()*ConfigLoader.votes))
                    server.stopServer();
                return;
            }
        }

        voteList[i] = player.getGameProfile().getName();
        votes++;
        String info = player.getGameProfile().getName()+StatCollector.translateToLocal("voterestart.info.display0")
                +votes+StatCollector.translateToLocal("voterestart.info.display1")
                +server.getCurrentPlayerCount()+StatCollector.translateToLocal("voterestart.info.display2");
        String info2 = StatCollector.translateToLocal("voterestart.info.display3")
                +(int)Math.ceil((double)server.getCurrentPlayerCount()*ConfigLoader.votes)+StatCollector.translateToLocal("voterestart.info.display4");
        logger.info(info);
        server.getConfigurationManager().sendChatMsg(new ChatComponentTranslation(info));
        server.getConfigurationManager().sendChatMsg(new ChatComponentTranslation(info2));
        if(votes >= Math.ceil((double)server.getCurrentPlayerCount()*ConfigLoader.votes))
            server.stopServer();
    }
}
