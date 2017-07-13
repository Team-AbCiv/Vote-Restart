package com.seraphjack.voterestart;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigLoader{
    public static Configuration config;
    public static double votes;
    public ConfigLoader(FMLPreInitializationEvent e){
        config = new Configuration(e.getSuggestedConfigurationFile());
        config.load();
        load();
    }
    public static void load(){
        String comment;
        comment = "How many players vote restart to restart.(%)";
        votes = config.get(Configuration.CATEGORY_GENERAL, "votePercent", 50, comment).getInt()/100;
        config.save();
    }
}
