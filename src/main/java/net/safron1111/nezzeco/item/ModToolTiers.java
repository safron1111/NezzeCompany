package net.safron1111.nezzeco.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.util.ModTags;

import java.util.List;

@SuppressWarnings("removal")
public class ModToolTiers {
    public static final Tier IRIDIUM = TierSortingRegistry.registerTier(
            new ForgeTier(3,1782,8.2F,3.5F,13,
                    ModTags.Blocks.NEEDS_IRIDIUM_TOOL, () -> Ingredient.of(ModItems.IRIDIUM.get())),
            new ResourceLocation(NezzeCo.MOD_ID, "iridium"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));
}
