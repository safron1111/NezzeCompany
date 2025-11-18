package net.safron1111.nezzeco.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ExponentialGelBlock extends Block {
    public ExponentialGelBlock(Properties pProperties) {
        super(pProperties);
    }

    int E = 2;

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (pEntity.isSuppressingBounce()) {
            super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
        } else {
            pEntity.causeFallDamage(pFallDistance, 0.0F, pLevel.damageSources().fall());
        }
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter pLevel, Entity pEntity) {
        if (pEntity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(pLevel, pEntity);
        } else {
            this.bounce(pEntity);
        }
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        Vec3 vec3 = pEntity.getDeltaMovement();
        Vec3 append = new Vec3(vec3.x*0.075f*E,0.0f,vec3.z*0.075f*E);
        pEntity.addDeltaMovement(append);
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    private void bounce(Entity pEntity) {
        Vec3 vec3 = pEntity.getDeltaMovement();
        if (vec3.y < 0.0f) {
            pEntity.setDeltaMovement((vec3.x+(vec3.x*0.1))*E,(-vec3.y-(vec3.y*0.25))*E,(vec3.z+(vec3.z*0.1))*E);
        }
    }
}
