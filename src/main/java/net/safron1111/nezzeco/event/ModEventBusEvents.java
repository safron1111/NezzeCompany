package net.safron1111.nezzeco.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.entity.ModEntities;
import net.safron1111.nezzeco.entity.custom.UberArrow;

@Mod.EventBusSubscriber(modid = NezzeCo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    //@SubscribeEvent
    //public static void registerAttributes(EntityAttributeCreationEvent event) {
    //    event.put(ModEntities.UBER_ARROW.get(), UberArrow.createAttributes().build());
    //}
}
