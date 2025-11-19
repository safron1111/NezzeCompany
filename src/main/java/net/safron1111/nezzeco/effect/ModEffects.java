package net.safron1111.nezzeco.effect;

import net.minecraft.core.UUIDUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.effect.custom.HypoventilationEffect;

import java.util.UUID;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, NezzeCo.MOD_ID);

    public static final RegistryObject<MobEffect> HYPOVENTILATION_EFFECT = EFFECTS.register("hypoventilation_effect",
            () -> new HypoventilationEffect(MobEffectCategory.HARMFUL,8102084)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, "44097ffe-e59a-A31c-Bd62-ff8ae5f24b21", (double) -0.05f, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
