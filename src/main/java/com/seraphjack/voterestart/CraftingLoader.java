package com.seraphjack.voterestart;

import com.seraphjack.voterestart.items.ItemLoader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CraftingLoader {
    public CraftingLoader(){
        GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.itemVoter), "a  ","b  ",'a', Blocks.stone_button,'b',Blocks.stone);
    }
}
