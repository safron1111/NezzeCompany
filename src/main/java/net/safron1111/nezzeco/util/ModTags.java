package net.safron1111.nezzeco.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.safron1111.nezzeco.NezzeCo;

@SuppressWarnings("removal")
public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> IRIDIUM_ORE = tag("iridium_ore");
        public static final TagKey<Block> NEEDS_IRIDIUM_TOOL = tag("needs_iridium_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(NezzeCo.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> MILLABLE = tag("millable");
        public static final TagKey<Item> MILLABLE_COARSE = tag("millable_coarse");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(NezzeCo.MOD_ID, name));
        }
    }
}
