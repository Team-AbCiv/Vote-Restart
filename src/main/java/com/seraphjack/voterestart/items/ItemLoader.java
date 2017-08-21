package com.seraphjack.voterestart.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemLoader {
    public static Item itemVoter;

    public ItemLoader() {
        itemVoter = new ItemVoter();
        GameRegistry.registerItem(itemVoter, "item_voter");
    }
}
