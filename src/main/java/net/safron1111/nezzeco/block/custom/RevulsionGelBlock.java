package net.safron1111.nezzeco.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class RevulsionGelBlock extends Block {
    public RevulsionGelBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (pEntity.isSuppressingBounce()) {
            super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
        } else {
            pEntity.causeFallDamage(pFallDistance, 0.8F, pLevel.damageSources().fall());
        }
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        Vec3 vec3 = pEntity.getDeltaMovement();
        double abs = Math.abs(vec3.x)+Math.abs(vec3.y)+Math.abs(vec3.z);
        if (abs >= 0.1f) {
            Vec3 newSpeed = new Vec3(vec3.x/128,vec3.y/12,vec3.z/128);
            pEntity.setDeltaMovement(newSpeed);
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }
}
