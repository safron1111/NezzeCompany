package net.safron1111.nezzeco.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.entity.custom.PrimedArrowTnt;
import net.safron1111.nezzeco.entity.custom.PrimedTatb;
import net.safron1111.nezzeco.entity.custom.UberArrow;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NezzeCo.MOD_ID);

    public static final RegistryObject<EntityType<UberArrow>> UBER_ARROW =
            ENTITY_TYPES.register("uber_arrow", () -> EntityType.Builder.<UberArrow>of(UberArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("uber_arrow"));
    public static final RegistryObject<EntityType<PrimedArrowTnt>> PRIMED_ARROW_TNT =
            ENTITY_TYPES.register("primed_arrow_tnt", () -> EntityType.Builder.<PrimedArrowTnt>of(PrimedArrowTnt::new, MobCategory.MISC)
                    .fireImmune().sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(10).build("primed_arrow_tnt"));
    public static final RegistryObject<EntityType<PrimedTatb>> PRIMED_TATB =
            ENTITY_TYPES.register("primed_tatb", () -> EntityType.Builder.<PrimedTatb>of(PrimedTatb::new, MobCategory.MISC)
                    .fireImmune().sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(10).build("primed_tatb"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
