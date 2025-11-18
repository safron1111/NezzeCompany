package net.safron1111.nezzeco.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class PropulsionGelBlock extends Block {
    public PropulsionGelBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (pEntity.isSuppressingBounce()) {
            super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
        } else {
            pEntity.causeFallDamage(pFallDistance, 0.5F, pLevel.damageSources().fall());
        }
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        Vec3 vec3 = pEntity.getDeltaMovement();
        Vec3 append = new Vec3(vec3.x*0.075f,0.0f,vec3.z*0.075f);
        pEntity.addDeltaMovement(append);
        super.stepOn(pLevel, pPos, pState, pEntity);
    }
}
