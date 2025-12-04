package net.safron1111.nezzeco.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.block.custom.*;
import net.safron1111.nezzeco.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, NezzeCo.MOD_ID);

    public static final RegistryObject<Block> IRIDIUM_BLOCK = registerBlock("iridium_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final RegistryObject<Block> RAW_IRIDIUM_BLOCK = registerBlock("raw_iridium_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> IRIDIUM_STONE_ORE = registerBlock("iridium_stone_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops(), UniformInt.of(3,6)));
    public static final RegistryObject<Block> IRIDIUM_DEEPSLATE_ORE = registerBlock("iridium_deepslate_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE).requiresCorrectToolForDrops(), UniformInt.of(3,6)));

    public static final RegistryObject<Block> TONE_BLOCK = registerBlock("tone_block",
            () -> new ToneBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));
    public static final RegistryObject<Block> TRAMPOLINE_BLOCK = registerBlock("trampoline_block",
            () -> new TrampolineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL).jumpFactor(1.35f)));
    public static final RegistryObject<Block> REPULSION_GEL = registerBlock("repulsion_gel",
            () -> new RepulsionGelBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.SLIME_BLOCK).jumpFactor(1.75f)));
    public static final RegistryObject<Block> PROPULSION_GEL = registerBlock("propulsion_gel",
            () -> new PropulsionGelBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.SLIME_BLOCK).friction(0.995f)));
    public static final RegistryObject<Block> REVULSION_GEL = registerBlock("revulsion_gel",
            () -> new RevulsionGelBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.SLIME_BLOCK).friction(0.55f)));
    public static final RegistryObject<Block> EXPONENTIAL_GEL = registerBlock("exponential_gel",
            () -> new ExponentialGelBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).sound(SoundType.SLIME_BLOCK).jumpFactor(1.75f).friction(0.995f)));
    public static final RegistryObject<Block> ARROW_TNT_BLOCK = registerBlock("arrow_tnt",
            () -> new ArrowTntBlock(BlockBehaviour.Properties.copy(Blocks.TNT)));
    public static final RegistryObject<Block> TATB_BLOCK = registerBlock("tatb",
            () -> new TatbBlock(BlockBehaviour.Properties.copy(Blocks.TNT).sound(SoundType.MOSS)));

    public static final RegistryObject<Block> MILLSTONE = registerBlock("millstone",
            () -> new MillstoneBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE).noOcclusion()));

    //Crops use "BLOCKS.register" instead of "registerBlock" because they have a block, but not an item
    public static final RegistryObject<Block> RYE_CROP = BLOCKS.register("rye_crop",
            () -> new RyeCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
