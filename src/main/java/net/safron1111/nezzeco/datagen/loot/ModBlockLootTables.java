package net.safron1111.nezzeco.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.safron1111.nezzeco.block.ModBlocks;
import net.safron1111.nezzeco.item.ModItems;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.IRIDIUM_BLOCK.get());
        this.dropSelf(ModBlocks.RAW_IRIDIUM_BLOCK.get());
        this.dropSelf(ModBlocks.TRAMPOLINE_BLOCK.get());
        this.dropSelf(ModBlocks.TONE_BLOCK.get());
        this.dropSelf(ModBlocks.EXPONENTIAL_GEL.get());
        this.dropSelf(ModBlocks.REPULSION_GEL.get());
        this.dropSelf(ModBlocks.PROPULSION_GEL.get());
        this.dropSelf(ModBlocks.REVULSION_GEL.get());

        this.add(ModBlocks.IRIDIUM_STONE_ORE.get(),
                block -> createOreDrop(ModBlocks.IRIDIUM_STONE_ORE.get(), ModItems.RAW_IRIDIUM.get()));
        this.add(ModBlocks.IRIDIUM_DEEPSLATE_ORE.get(),
                block -> createOreDrop(ModBlocks.IRIDIUM_DEEPSLATE_ORE.get(), ModItems.RAW_IRIDIUM.get()));
    }

    protected LootTable.Builder createVariedOreDrops(Block pBlock, Item pItem, float pMin, float pMax) {
        // Copper is 2-5, Lapis is 4-9, Redstone is 4-5
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(pItem)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(pMin, pMax)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
