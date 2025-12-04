package net.safron1111.nezzeco.item.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AcceleratorItem extends Item {
    public AcceleratorItem(Properties pProperties) {
        super(pProperties);
    }
    float force = 10;

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        Vec3 dir = pPlayer.getLookAngle();
        Vec3 forceVec = new Vec3(dir.x*force,dir.y*force,dir.z*force);

        if (!pPlayer.isCreative() && itemStack.getCount() > 0) {
            itemStack.setCount(itemStack.getCount()-1);
        }
        Vec3 delta = pPlayer.getDeltaMovement();
        Vec3 finalVec = new Vec3(delta.x+forceVec.x,delta.y+forceVec.y,delta.z+forceVec.z);

        pPlayer.setDeltaMovement(finalVec);
        return InteractionResultHolder.success(itemStack);
    }
}
