package com.seraphjack.voterestart.items;

import com.seraphjack.voterestart.VoteRestart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemVoter extends Item {
    public ItemVoter() {
        setCreativeTab(CreativeTabs.tabTools);
        setUnlocalizedName("item_voter");
        setTextureName(VoteRestart.MODID + ":item_voter");
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (!player.isSneaking())
                VoteRestart.vote(player);
            else
                VoteRestart.deVote(player);
        }
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        String info = StatCollector.translateToLocal("item.item_voter.tooltip");
        int i;
        for (i = 0; i < info.length(); i++) {
            if (info.charAt(i) == ',') {
                StringBuilder buf = new StringBuilder();
                for (int j = 0; j <= i; j++)
                    buf.append(info.charAt(j));
                list.add(buf.toString());
                buf = new StringBuilder();
                for (int j = i + 1; j < info.length(); j++)
                    buf.append(info.charAt(j));
                list.add(buf.toString());
                return;
            }
        }
        list.add(info);
    }
}
