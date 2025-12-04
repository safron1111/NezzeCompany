package net.safron1111.nezzeco.entity.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.safron1111.nezzeco.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

public class PrimedArrowTnt extends PrimedTnt {
    public PrimedArrowTnt(EntityType<? extends PrimedArrowTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    private LivingEntity owner;
    double radius = 1;

    public PrimedArrowTnt(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
        this(ModEntities.PRIMED_ARROW_TNT.get(), pLevel);
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
    protected void explode() {
        for (int z = -90; z < 90; z = z + 9) {
            for (int d = -180; d < 180; d = d + 18) {
                double radians = Math.toRadians(d);
                double radiansZ = Math.toRadians(z);
                LogUtils.getLogger().info("Degrees: " + String.valueOf(d) + " Radians: " + String.valueOf(radians));

                //double xC = this.getX() + radius * Math.sin(radiansZ) * Math.cos(radians);
                //double yC = this.getY() + radius * Math.sin(radiansZ) * Math.sin(radians);
                //double zC = this.getZ() + radius * Math.cos(radiansZ);

                double xC = this.getX() + radius * Math.sin(radiansZ) * Math.cos(radians);
                double yC = this.getY() + radius * Math.cos(radiansZ);
                double zC = this.getZ() + radius * Math.sin(radiansZ) * Math.sin(radians);

                Vec3 cPoint = new Vec3(xC, yC, zC);
                LogUtils.getLogger().info("xC: " + String.valueOf(xC) + " yC: " + String.valueOf(yC));

                Vec3 pos = new Vec3(this.getX(), this.getY(), this.getZ());
                Vec3 dir = new Vec3(cPoint.x - pos.x, cPoint.y - pos.y, cPoint.z - pos.z);
                LogUtils.getLogger().info("dir: " + String.valueOf(dir));

                Arrow arrow = new Arrow(this.level(), this.getX(), this.getY(), this.getZ());
                arrow.setDeltaMovement(dir);
                arrow.setPos(cPoint);

                this.level().addFreshEntity(arrow);
            }
        }
        super.explode();
    }

    @Override
    public @Nullable LivingEntity getOwner() {
        return owner;
    }
}
