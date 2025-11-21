package net.safron1111.nezzeco.block.custom;

import com.mojang.logging.LogUtils;
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
        //set newX in case if-else statement doesn't work, and to make it accessible to code outside the if-else statement
        //statement conserves negatives by default, but isn't powered
        double newX = vec3.x*0.075f*E;
        if (vec3.x < 0) {
            //if vec3.x is negative, powered it to the exponent, but make the whole statement negative
            newX = -(Math.pow(vec3.x, E)*0.075f);
        } else {
            //otherwise power it to the exponent like normal
            newX = Math.pow(vec3.x, E)*0.075f;
        }
        //repeat for Z
        double newZ = vec3.z*0.075f*E;
        if (vec3.z < 0) {
            newZ = -(Math.pow(vec3.z, E)*0.075f);
        } else {
            newZ = Math.pow(vec3.z, E)*0.075f;
        }
        //don't add y velocity
        Vec3 append = new Vec3(newX,0.0f,newZ);
        LogUtils.getLogger().info(String.valueOf(vec3));
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
