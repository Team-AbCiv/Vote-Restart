package com.seraphjack.voterestart.items;

import com.seraphjack.voterestart.AchievementLoader;
import com.seraphjack.voterestart.VoteRestart;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemVoter extends Item{
    public ItemVoter(){
        setCreativeTab(CreativeTabs.tabTools);
        setUnlocalizedName("item_voter");
        setTextureName(VoteRestart.MODID+":item_voter");
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if(!world.isRemote) {
            VoteRestart.vote(player, world);
            player.triggerAchievement(AchievementLoader.saltyfish);
        }
        return stack;
    }
}
