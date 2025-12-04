package net.safron1111.nezzeco.item.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.block.custom.MillstoneBlock;

public class CoarseRyeDoughItem extends Item {
    private static final String tagName = MillstoneBlock.tagName;
    public CoarseRyeDoughItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pStack.hasTag()) {
            CompoundTag nbt = pStack.getTag();
            if (nbt != null && nbt.contains(NezzeCo.MOD_ID + ":" + tagName)) {
                CompoundTag compoundTag = nbt.getCompound(NezzeCo.MOD_ID+":"+tagName);
                int effectID = compoundTag.getInt("effectID");
                int effectDuration = compoundTag.getInt("effectDuration");
                LogUtils.getLogger().info(String.valueOf(effectDuration));
                MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffect.byId(effectID),effectDuration);
                pLivingEntity.addEffect(mobEffectInstance);
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
