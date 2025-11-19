package net.safron1111.nezzeco.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NezzeCo.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(Tags.Blocks.ORES)
                .add(ModBlocks.IRIDIUM_STONE_ORE.get())
                .add(ModBlocks.IRIDIUM_DEEPSLATE_ORE.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.TONE_BLOCK.get())
                .add(ModBlocks.TRAMPOLINE_BLOCK.get())
                .add(ModBlocks.IRIDIUM_BLOCK.get())
                .add(ModBlocks.IRIDIUM_STONE_ORE.get())
                .add(ModBlocks.RAW_IRIDIUM_BLOCK.get())
                .add(ModBlocks.IRIDIUM_DEEPSLATE_ORE.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.IRIDIUM_STONE_ORE.get())
                .add(ModBlocks.IRIDIUM_DEEPSLATE_ORE.get())
                .add(ModBlocks.IRIDIUM_BLOCK.get())
                .add(ModBlocks.RAW_IRIDIUM_BLOCK.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.TRAMPOLINE_BLOCK.get())
                .add(ModBlocks.TONE_BLOCK.get());
    }
}
