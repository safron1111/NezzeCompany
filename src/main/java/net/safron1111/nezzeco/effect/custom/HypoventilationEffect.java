package net.safron1111.nezzeco.effect.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class HypoventilationEffect extends MobEffect {
    public HypoventilationEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }
    protected int c = 0;

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration >= 1;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level().isClientSide()) {
            if (pLivingEntity.isAlive()) {
                c += 1;
                pLivingEntity.setAirSupply(pLivingEntity.getAirSupply()-(pAmplifier+1)*4);
                if (c==2) {
                    c = 0;
                    pLivingEntity.setAirSupply(pLivingEntity.getAirSupply()-(pAmplifier+1));
                }
                LogUtils.getLogger().info(String.valueOf(pLivingEntity.getAirSupply()));
            }
        } else {
            super.applyEffectTick(pLivingEntity, pAmplifier);
        }
    }
}
