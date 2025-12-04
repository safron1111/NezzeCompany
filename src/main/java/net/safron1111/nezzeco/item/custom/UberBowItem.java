package net.safron1111.nezzeco.item.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.safron1111.nezzeco.entity.custom.UberArrow;
import org.checkerframework.checker.units.qual.A;
import org.jline.utils.Log;

import java.util.Random;

public class UberBowItem extends BowItem {
    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, pStack) > 0;
            ItemStack itemstack = player.getProjectile(pStack);

            int i = this.getUseDuration(pStack) - pTimeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = newGetPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, pStack, player));
                    if (!pLevel.isClientSide) {
                        Random rand = new Random();
                        int count = 20;
                        int maxAdd = 200;
                        int minAdd = 180;
                        int result = count + rand.nextInt(minAdd,maxAdd+1+i);
                        for (int a = 0; a <= result; a++) {
                            UberArrow uberArrow = new UberArrow(pLevel,pEntityLiving);
                            uberArrow = uberArrow(uberArrow);
                            uberArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 0.2F+((float)result/count/2));
                            double currentDamage = uberArrow.getBaseDamage();
                            if (f >= 0.8F) {
                                uberArrow.setCritArrow(true);
                            }

                            int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, pStack);
                            if (j > 0) {
                                currentDamage = currentDamage + (double)j * 0.5D + 0.5D;
                                uberArrow.setBaseDamage(currentDamage);
                            }

                            LogUtils.getLogger().info("currentDamage Value: " + String.valueOf(currentDamage) + " abstractarrow.getBaseDamage Value: " + String.valueOf(uberArrow.getBaseDamage()));
                            currentDamage = currentDamage+(double)i/6;
                            LogUtils.getLogger().info("currentDamage Value: " + String.valueOf(currentDamage) + " abstractarrow.getBaseDamage Value: " + String.valueOf(uberArrow.getBaseDamage()));

                            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, pStack);
                            if (k > 0) {
                                uberArrow.setKnockback(k*2);
                            }

                            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, pStack) > 0) {
                                uberArrow.setSecondsOnFire(200);
                            }

                            if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                                uberArrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }

                            pLevel.addFreshEntity(uberArrow);
                        }
                    }

                    pLevel.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public static float newGetPowerForTime(int pCharge) {
        float f = (float)pCharge / 10.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 2.0F) {
            f = 2.0F;
        }

        return f;
    }

    public UberArrow uberArrow(UberArrow arrow) {
        return arrow;
    }

    public UberBowItem(Properties pProperties) {
        super(pProperties);
    }
}
