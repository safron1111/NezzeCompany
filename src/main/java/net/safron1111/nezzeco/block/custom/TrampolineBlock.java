package net.safron1111.nezzeco.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class TrampolineBlock extends Block {
    public TrampolineBlock(Properties pProperties) {
        super(pProperties);
    }
    float movementForce = 0.2f;

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (pEntity.isSuppressingBounce()) {
            super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
            pEntity.causeFallDamage(pFallDistance, 0.35f, pLevel.damageSources().fall());
        } else {
            pEntity.causeFallDamage(pFallDistance, 0.0f, pLevel.damageSources().fall());
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

    private void bounce(Entity pEntity) {
        Vec3 Velocity = pEntity.getDeltaMovement();
        if (Velocity.y < 0.1f) {
            float loss = pEntity instanceof LivingEntity ? 1.0f : 0.95f;
            pEntity.setDeltaMovement(Velocity.x, (-Velocity.y/loss)+(movementForce/loss), Velocity.z);
        }
    }
}
