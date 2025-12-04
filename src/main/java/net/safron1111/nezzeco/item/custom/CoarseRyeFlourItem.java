package net.safron1111.nezzeco.item.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.block.custom.MillstoneBlock;
import net.safron1111.nezzeco.item.ModItems;

public class CoarseRyeFlourItem extends Item {
    public CoarseRyeFlourItem(Properties pProperties) {
        super(pProperties);
    }
    private static final String tagName = MillstoneBlock.tagName;

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.level().isClientSide()) {
            BlockState blockState = entity.getBlockStateOn();
            if (blockState.is(Blocks.WATER) | blockState.is(Blocks.WATER_CAULDRON)) {
                Vec3 vec3d = entity.getDeltaMovement();
                ItemStack dough = new ItemStack(ModItems.COARSE_RYE_DOUGH.get());

                if (stack.hasTag()) {
                    CompoundTag nbt = stack.getTag();
                    if (nbt != null && nbt.contains(NezzeCo.MOD_ID+":"+tagName)) {
                        CompoundTag compoundTag = nbt.getCompound(NezzeCo.MOD_ID+":"+tagName);
                        int effectID = compoundTag.getInt("effectID");
                        int effectDuration = compoundTag.getInt("effectDuration");

                        CompoundTag ergotism = dough.getOrCreateTagElement(NezzeCo.MOD_ID+":"+tagName);
                        ergotism.putInt("effectID", effectID);
                        ergotism.putInt("effectDuration",effectDuration);
                    }
                }

                dough.setCount(stack.getCount());
                entity.spawnAtLocation(dough).setDeltaMovement(vec3d);
                entity.kill();
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
