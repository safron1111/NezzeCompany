package net.safron1111.nezzeco.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.entity.client.ModModelLayers;
import net.safron1111.nezzeco.entity.client.UberArrowModel;

@Mod.EventBusSubscriber(modid = NezzeCo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.UBER_ARROW_LAYER, UberArrowModel::createBodyLayer);
    }
}
