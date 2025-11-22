package net.safron1111.nezzeco.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.block.ModBlocks;
import net.safron1111.nezzeco.item.ModItems;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter, IRIDIUM_SMELTABLES, RecipeCategory.MISC, ModItems.IRIDIUM.get(), 1.3f, 200, "iridium");
        oreBlasting(pWriter, IRIDIUM_SMELTABLES, RecipeCategory.MISC, ModItems.IRIDIUM.get(), 1.3f, 100, "iridium");

        cookingRecipes(pWriter,"",RecipeSerializer.SMELTING_RECIPE,200,0.35F);
        cookingRecipes(pWriter,"smoking",RecipeSerializer.SMOKING_RECIPE,100,0.35F);
        cookingRecipes(pWriter,"campfire_cooking",RecipeSerializer.CAMPFIRE_COOKING_RECIPE,600,0.35F);

        pack3By3IntoBlock(RecipeCategory.MISC, ModBlocks.IRIDIUM_BLOCK.get(), ModItems.IRIDIUM.get(), pWriter);
        blockToItemAmount(RecipeCategory.MISC, ModBlocks.IRIDIUM_BLOCK.get(), ModItems.IRIDIUM.get(), 9, pWriter);

        breadItem(RecipeCategory.FOOD, ModItems.RYE.get(), ModItems.RYE_BREAD.get(), pWriter);
        // fourPointStarRecipe example, used to make a cheeseburger, order is Top, Bottom, Left, Right, Center, Output
        fourPointStarRecipe(RecipeCategory.FOOD,
                Items.BREAD, Items.BREAD, Items.DRIED_KELP, ModItems.CHEESE.get(), Items.COOKED_BEEF, ModItems.CHEESEBURGER.get(),
                pWriter);
    }

    // Add cooked food recipes here, allows for automatically making smelting, smoking, and campfire recipes
    protected static void cookingRecipes(Consumer<FinishedRecipe> pWriter, String pCookingMethod, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, int pCookingTime, float pExperience) {
        simpleCookingRecipe(pWriter, pCookingMethod, pCookingSerializer, pCookingTime, Items.MILK_BUCKET, ModItems.CHEESE.get(), pExperience);
        simpleCookingRecipe(pWriter, pCookingMethod, pCookingSerializer, pCookingTime, ModItems.RYE_DOUGH.get(), ModItems.RYE_BREAD.get(), pExperience);
        simpleCookingRecipe(pWriter, pCookingMethod, pCookingSerializer, pCookingTime, ModItems.COARSE_RYE_DOUGH.get(), ModItems.PUMPERNICKEL.get(), pExperience);

    }

    // Various Lists for Recipes \/

    private static final List<ItemLike> IRIDIUM_SMELTABLES = List.of(
            ModItems.RAW_IRIDIUM.get(),
            ModBlocks.IRIDIUM_DEEPSLATE_ORE.get(),
            ModBlocks.IRIDIUM_STONE_ORE.get()
    );

    // Various Methods for Recipes \/

    // Crafting ==
    protected static void pack3By3IntoBlock(RecipeCategory pCategory, Block pBlock, Item pItem, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(pCategory,pBlock)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .define('#', pItem)
                        .unlockedBy(getHasName(pItem), has(pItem))
                        .save(pWriter);
    }
    protected static void pack3By3IntoItem(RecipeCategory pCategory, Item inputItem, Item outputItem, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(pCategory,outputItem)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', inputItem)
                .unlockedBy(getHasName(inputItem), has(inputItem))
                .save(pWriter);
    }
    protected static void fourPointStarRecipe(RecipeCategory pCategory, Item inputItemTop, Item inputItemBottom, Item inputItemLeft, Item inputItemRight, Item inputItemCenter, Item outputItem, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(pCategory,outputItem)
                .pattern(" @ ")
                .pattern("$&%")
                .pattern(" # ")
                .define('@', inputItemTop)
                .define('#', inputItemBottom)
                .define('$', inputItemLeft)
                .define('%', inputItemRight)
                .define('&', inputItemCenter)
                .unlockedBy(getHasName(inputItemCenter), has(inputItemCenter))
                .save(pWriter);
    }
    protected static void threeInARowIntoItem(RecipeCategory pCategory, Item inputItem, Item outputItem, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(pCategory,outputItem)
                .pattern("###")
                .define('#', inputItem)
                .unlockedBy(getHasName(inputItem), has(inputItem))
                .save(pWriter);
    }
    protected static void breadItem(RecipeCategory pCategory, Item inputItem, Item outputItem, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(pCategory,outputItem)
                .pattern("###")
                .define('#', inputItem)
                .unlockedBy(getHasName(inputItem), has(inputItem))
                .save(pWriter,
                        NezzeCo.MOD_ID + ":" + getItemName(outputItem) + "_from_" + getItemName(inputItem));
    }

    protected static void blockToItemAmount(RecipeCategory pCategory, Block pBlock, Item pItem, int count, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(pCategory,pItem,count)
                .requires(pBlock)
                .unlockedBy(getHasName(pBlock), has(pBlock))
                .save(pWriter);
    }
    protected static void itemToItemAmount(RecipeCategory pCategory, Item inputItem, Item outputItem, int count, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(pCategory,outputItem,count)
                .requires(inputItem)
                .unlockedBy(getHasName(inputItem), has(inputItem))
                .save(pWriter);
    }

    // Smelting ==
    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory,
                    pResult, pExperience, pCookingTime,
                    pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer,
                    NezzeCo.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }

    protected static void simpleCookingRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, String pCookingMethod, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, int pCookingTime, ItemLike pIngredient, ItemLike pResult, float pExperience) {
        if (!pCookingMethod.isEmpty()){
            SimpleCookingRecipeBuilder.generic(Ingredient.of(pIngredient),
                            RecipeCategory.FOOD, pResult, pExperience, pCookingTime, pCookingSerializer)
                    .unlockedBy(getHasName(pIngredient),
                            has(pIngredient)).save(pFinishedRecipeConsumer,
                            NezzeCo.MOD_ID + ":" + getItemName(pResult) + "_from_" + pCookingMethod);
        } else {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(pIngredient),
                            RecipeCategory.FOOD, pResult, pExperience, pCookingTime, pCookingSerializer)
                    .unlockedBy(getHasName(pIngredient),
                            has(pIngredient)).save(pFinishedRecipeConsumer);
        }
    }
}
