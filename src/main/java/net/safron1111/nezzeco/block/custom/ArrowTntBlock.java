package net.safron1111.nezzeco.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.safron1111.nezzeco.entity.custom.PrimedArrowTnt;
import org.jetbrains.annotations.Nullable;

public class ArrowTntBlock extends TntBlock {
    public ArrowTntBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onCaughtFire(BlockState state, Level pLevel, BlockPos pPos, @Nullable Direction face, @Nullable LivingEntity pEntity) {
        if (!pLevel.isClientSide) {
            PrimedArrowTnt primedArrowTnt = new PrimedArrowTnt(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, pEntity);
            pLevel.addFreshEntity(primedArrowTnt);
            pLevel.playSound((Player)null, primedArrowTnt.getX(), primedArrowTnt.getY(), primedArrowTnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            pLevel.gameEvent(pEntity, GameEvent.PRIME_FUSE, pPos);
        }
    }

    @Override
    public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {
        if (!pLevel.isClientSide) {
            PrimedArrowTnt primedArrowTnt = new PrimedArrowTnt(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, pExplosion.getIndirectSourceEntity());
            int i = primedArrowTnt.getFuse();
            primedArrowTnt.setFuse((short)(pLevel.random.nextInt(i / 4) + i / 8));
            pLevel.addFreshEntity(primedArrowTnt);
        }
    }
}
