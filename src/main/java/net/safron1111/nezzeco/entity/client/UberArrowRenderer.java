package net.safron1111.nezzeco.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.entity.custom.UberArrow;

@SuppressWarnings("removal")
@OnlyIn(Dist.CLIENT)
public class UberArrowRenderer extends ArrowRenderer<UberArrow> {
    public UberArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(UberArrow pEntity) {
        return new ResourceLocation(NezzeCo.MOD_ID,"textures/entity/uber_arrow.png");
    }
}
