package net.safron1111.nezzeco.entity.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.safron1111.nezzeco.entity.ModEntities;
import net.safron1111.nezzeco.util.CustomExplosion;
import org.jetbrains.annotations.Nullable;

public class PrimedTatb extends PrimedTnt {
    public PrimedTatb(EntityType<? extends PrimedTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    private LivingEntity owner;
    private final float blastRadius = 7.0f;
    private final float blastPower = 4.68f;
    private final float blastPressure = 1.86f;

    public PrimedTatb(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
        this(ModEntities.PRIMED_TATB.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.owner = pOwner;
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            this.explode();
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void explode() {
        LogUtils.getLogger().info(String.valueOf(this.level().isClientSide));
        CustomExplosion exp = new CustomExplosion(this.level(), this, this.xo, this.yo, this.zo, blastRadius, blastPower, blastPressure, false, true, Explosion.BlockInteraction.DESTROY);
        exp.explode(1);
    }

    @Override
    public @Nullable LivingEntity getOwner() {
        return owner;
    }
}
