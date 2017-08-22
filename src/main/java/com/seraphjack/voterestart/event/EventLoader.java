package com.seraphjack.voterestart.event;

import com.seraphjack.voterestart.VoteRestart;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.common.MinecraftForge;

public class EventLoader {
    public EventLoader() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent e) {
        VoteRestart.deVote(e.player);
    }
}
