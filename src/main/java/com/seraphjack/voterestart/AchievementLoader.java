package com.seraphjack.voterestart;

import com.seraphjack.voterestart.items.ItemLoader;
import net.minecraft.stats.Achievement;

public class AchievementLoader {
    public static Achievement saltyfish;
    public AchievementLoader(){
        saltyfish = new Achievement("achievement.voterestart.saltyfish","voterestart.saltyfish",-8,-6, ItemLoader.itemVoter,null);
        saltyfish.setSpecial();
        saltyfish.registerStat();
    }
}
