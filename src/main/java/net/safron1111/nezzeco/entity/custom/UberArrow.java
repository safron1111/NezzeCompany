package net.safron1111.nezzeco.entity.custom;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.safron1111.nezzeco.entity.ModEntities;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class UberArrow extends AbstractArrow {
    public UberArrow(EntityType<? extends UberArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public UberArrow(Level pLevel) {
        super(ModEntities.UBER_ARROW.get(), pLevel);
    }
    public UberArrow(Level pLevel, LivingEntity pLivingEntity) {
        super(ModEntities.UBER_ARROW.get(),pLivingEntity,pLevel);
    }
    int ExplosionRadius = 12;
    Random rand = new Random();
    int extraExplosionsBound = 4;

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        explodeOnHit(this.level(),ExplosionRadius,0);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        explodeOnHit(this.level(),ExplosionRadius,0);
    }

    private void explodeOnHit(Position pOffsetPos, Level pLevel, float pRadius, int pEI) {
        if (!pLevel.isClientSide()) {
            if (pEI < 0) {
                pEI = 0;
            }
            Level.ExplosionInteraction explosionInteraction = Level.ExplosionInteraction.NONE;
            boolean fire = false;
            if (this.isOnFire()) {
                fire = true;
            }

            if (pEI == 0) {
                explosionInteraction = Level.ExplosionInteraction.TNT;
            } else if (pEI == 1) {
                explosionInteraction = Level.ExplosionInteraction.BLOCK;
            } else if (pEI == 2) {
                explosionInteraction = Level.ExplosionInteraction.MOB;
            }

            pLevel.explode(this, this.getX(), this.getY(), this.getZ(), pRadius, fire, explosionInteraction);
            if (rand.nextInt(0,extraExplosionsBound+1) == 1) {
                pLevel.explode(this,this.getX()+pOffsetPos.x(),this.getY()+pOffsetPos.y(),this.getZ()+pOffsetPos.z(),pRadius,fire,explosionInteraction);
            } else {
                pLevel.explode(this,this.getX()+pOffsetPos.x(),this.getY()+pOffsetPos.y(),this.getZ()+pOffsetPos.z(),pRadius,fire,explosionInteraction);
                this.kill();
            }
        }
    }

    private void explodeOnHit(Level pLevel, float pRadius, int pEI) {
        if (!pLevel.isClientSide()) {
            if (pEI < 0) {
                pEI = 0;
            }
            Level.ExplosionInteraction explosionInteraction = Level.ExplosionInteraction.NONE;
            boolean fire = false;
            if (this.isOnFire()) {
                fire = true;
            }

            if (pEI == 0) {
                explosionInteraction = Level.ExplosionInteraction.TNT;
            } else if (pEI == 1) {
                explosionInteraction = Level.ExplosionInteraction.BLOCK;
            } else if (pEI == 2) {
                explosionInteraction = Level.ExplosionInteraction.MOB;
            }

            if (rand.nextInt(0, extraExplosionsBound + 1) == 1) {
                pLevel.explode(this, this.getX(), this.getY(), this.getZ(), pRadius, fire, explosionInteraction);
            } else {
                pLevel.explode(this, this.getX(), this.getY(), this.getZ(), pRadius, fire, explosionInteraction);
                this.kill();
            }
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }
}
