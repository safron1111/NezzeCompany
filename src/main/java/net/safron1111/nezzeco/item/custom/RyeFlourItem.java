package net.safron1111.nezzeco.item.custom;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.safron1111.nezzeco.item.ModItems;

public class RyeFlourItem extends Item {
    public RyeFlourItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.level().isClientSide()) {
            BlockState blockState = entity.getBlockStateOn();
            if (blockState.is(Blocks.WATER) | blockState.is(Blocks.WATER_CAULDRON)) {
                Vec3 vec3d = entity.getDeltaMovement();
                ItemStack dough = new ItemStack(ModItems.RYE_DOUGH.get());
                dough.setCount(stack.getCount());
                entity.spawnAtLocation(dough).setDeltaMovement(vec3d);
                entity.kill();
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
