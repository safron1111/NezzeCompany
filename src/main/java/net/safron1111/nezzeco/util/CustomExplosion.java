package net.safron1111.nezzeco.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CustomExplosion {
    private final boolean fire;
    private final boolean particles;
    private final Level level;
    private double x;
    private double y;
    private double z;
    private final Entity sourceEntity;
    private final float blastRadius;
    private final float blastPower;
    private final float blastPressure;
    private final ObjectArrayList<BlockPos> toExplode = new ObjectArrayList<>();
    private final Map<Player, Vec3> hitPlayers = Maps.newHashMap();
    private Vec3 position;
    private final Random random = new Random();
    private final Explosion.BlockInteraction blockInteraction;

    public CustomExplosion(Level pLevel, @Nullable Entity pSource, double pX, double pY, double pZ, float pRadius, float pPower, float pPressure, boolean pFire, boolean pParticles, Explosion.BlockInteraction blockInteraction) {
        this.level = pLevel;
        this.x = pX;
        this.y = pY;
        this.z = pZ;
        this.sourceEntity = pSource;
        this.fire = pFire;
        this.particles = pParticles;
        this.blastRadius = pRadius;
        this.blastPower = pPower;
        this.blastPressure = pPressure;
        this.blockInteraction = blockInteraction;
        this.position = new Vec3(this.x,this.y,this.z);
    }

    public void explode(float damageModifier) {
        if (!this.level.isClientSide()) {
            Set<BlockPos> currentBlow = Sets.newHashSet();
            Set<Entity> currentEntities = Sets.newHashSet();
            float upperThreshold = blastRadius-(blastRadius/16);
            for (float eX = -blastRadius; eX <= blastRadius; eX = eX + 0.5f) {
                for (float eY = -blastRadius; eY <= blastRadius; eY = eY + 0.5f) {
                    for (float eZ = -blastRadius; eZ <= blastRadius; eZ = eZ + 0.5f) {
                        if (Math.abs(eX) >= upperThreshold || Math.abs(eY) >= upperThreshold || Math.abs(eZ) >= upperThreshold) {
                            Vec3 ePos = new Vec3(this.x+eX,this.y+eY,this.z+eZ);
                            float intensity = blastPower * random.nextFloat(0.7f,1.3f);
                            Vec3 dir = new Vec3(ePos.x-this.x,ePos.y-this.y,ePos.z-this.z).normalize();
                            double distance = Math.sqrt(Math.pow(ePos.x-x,2) + Math.pow(ePos.y-y,2) + Math.pow(ePos.z-z,2));

                            for (float d = 0f; d < (distance*blastPower) && intensity > 0; d = d + (1f/3f)) {
                                Vec3 curPos = new Vec3(x+(dir.x*d),y+(dir.y*d),z+(dir.z*d));
                                BlockPos curBlockPos = BlockPos.containing(curPos.x,curPos.y,curPos.z);
                                BlockState curBlockState = this.level.getBlockState(curBlockPos);
                                float curBlastRes = curBlockState.getBlock().getExplosionResistance();

                                if (!level.isInWorldBounds(curBlockPos)) {
                                    break;
                                }

                                if (!curBlockState.isAir()) {
                                    intensity = intensity - ((curBlastRes + 0.3f)  * 0.3f);
                                }

                                AABB aabb = new AABB(curPos.x-0.5,curPos.y-0.5,curPos.z-0.5,curPos.x+0.5,curPos.y+0.5,curPos.z+0.5);
                                currentEntities.addAll(level.getEntities(sourceEntity,aabb));

                                if(!curBlockState.isAir()) {
                                    currentBlow.add(curBlockPos);
                                }
                                intensity = intensity - 0.225f;
                            }
                        }
                    }
                }
            }
            this.toExplode.addAll(currentBlow);
            for (Entity entity : currentEntities) {
                if (!entity.ignoreExplosion()) {
                    double entDist = Math.sqrt(entity.distanceToSqr(position));
                    double distMod = (1 - entDist / (2 * blastPower));

                    //come back here later
                    double exposure = 1;
                    double velMagnitude = distMod * exposure * blastPressure;
                    float damageMod = (damageModifier >= 0f) ? damageModifier : 1;
                    float damage = (float) ((Math.pow(distMod * exposure, 2) + distMod * exposure) + 1) * damageMod;

                    Vec3 dir = new Vec3(entity.getX() - this.x, entity.getBlockY() - this.y, entity.getZ() - this.z).normalize();
                    Vec3 moveVec = new Vec3(dir.x * velMagnitude, dir.y * velMagnitude, dir.z * velMagnitude);
                    if (entity instanceof LivingEntity livingEntity) {
                        double newMag = ProtectionEnchantment.getExplosionKnockbackAfterDampener(livingEntity, velMagnitude);
                        moveVec = new Vec3(dir.x * newMag, dir.y * newMag, dir.z * newMag);
                        if (livingEntity.getType() == EntityType.PLAYER || livingEntity instanceof Player) {
                            Player player = (Player) livingEntity;
                            if (level.getDifficulty() == Difficulty.PEACEFUL) {
                                damage = 0;
                            } else if (level.getDifficulty() == Difficulty.EASY) {
                                damage = Math.min(damage / 2 + 1, damage);
                            } else if (level.getDifficulty() == Difficulty.HARD) {
                                damage = damage * 3 / 2;
                            }
                            if (!player.isSpectator() && (!player.isCreative()) || !player.getAbilities().flying) {
                                this.hitPlayers.put(player, player.getDeltaMovement().add(moveVec));
                            }
                        }
                    }
                    Entity indirectEntity = this.getIndirectSourceEntity();
                    entity.hurt(level.damageSources().explosion(indirectEntity, sourceEntity), damage);

                    entity.setDeltaMovement(entity.getDeltaMovement().add(moveVec));
                }
            }
        }
        finalizeExplosion();
    }

    public void finalizeExplosion() {
        if (level.isClientSide()) {
            this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
        }

        boolean interacts = this.interactsWithBlocks();
        if (particles) {
            if (!(this.blastPower < 2.0f) && interacts) {
                this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
            } else {
                this.level.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
            }
        }

        if (!this.level.isClientSide()) {
            if (interacts) {
                ObjectArrayList<Pair<ItemStack, BlockPos>> objectArrayList = new ObjectArrayList<>();
                boolean isIndirectSourcePlayer = this.getIndirectSourceEntity() instanceof Player;
                Util.shuffle(toExplode,this.level.random);

                Explosion tempClassExplosion = new Explosion(level, sourceEntity, x, y, z, blastPower, fire, blockInteraction);
                for (BlockPos blockPos : this.toExplode) {
                    BlockState blockState = level.getBlockState(blockPos);
                    Block block = blockState.getBlock();
                    if (!blockState.isAir()) {
                        BlockPos blockPosImmutable = blockPos.immutable();
                        if (blockState.canDropFromExplosion(level,blockPos,tempClassExplosion)) {
                            if (level instanceof ServerLevel serverLevel) {
                                BlockEntity blockEntity = blockState.hasBlockEntity() ? this.level.getBlockEntity(blockPos) : null;
                                //tnt code idk
                                LootParams.Builder lootparams$builder = (new LootParams.Builder(serverLevel)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockEntity).withOptionalParameter(LootContextParams.THIS_ENTITY, this.sourceEntity);
                                if (this.blockInteraction == Explosion.BlockInteraction.DESTROY_WITH_DECAY) {
                                    lootparams$builder.withParameter(LootContextParams.EXPLOSION_RADIUS, this.blastPower);
                                }

                                blockState.spawnAfterBreak(serverLevel, blockPos, ItemStack.EMPTY, isIndirectSourcePlayer);
                                blockState.getDrops(lootparams$builder).forEach((ItemStack) -> {
                                    addBlockDrops(objectArrayList, ItemStack, blockPosImmutable);
                                });
                            }
                        }
                        blockState.onBlockExploded(level, blockPos, tempClassExplosion);
                    }
                }
                tempClassExplosion.clearToBlow();
                tempClassExplosion = null;

                for(Pair<ItemStack, BlockPos> pair : objectArrayList) {
                    Block.popResource(this.level, pair.getSecond(), pair.getFirst());
                }
            }

            if (this.fire) {
                for(BlockPos fireBlockPos : this.toExplode) {
                    int nextInt = this.random.nextInt(3);
                    boolean isAirBlock = this.level.getBlockState(fireBlockPos).isAir();
                    boolean isSolidBelow = this.level.getBlockState(fireBlockPos.below()).isSolidRender(this.level, fireBlockPos.below());
                    if (nextInt == 0 && isAirBlock && isSolidBelow) {
                        this.level.setBlockAndUpdate(fireBlockPos, BaseFireBlock.getState(this.level, fireBlockPos));
                    }
                }
            }
        }
    }

    @Nullable
    public LivingEntity getIndirectSourceEntity() {
        if (this.sourceEntity == null) {
            return null;
        } else {
            Entity entity = this.sourceEntity;
            if (entity instanceof PrimedTnt primedTnt) {
                return primedTnt.getOwner();
            } else if (entity instanceof LivingEntity livingEntity) {
                return livingEntity;
            } else if (entity instanceof Projectile projectile) {
                entity = projectile.getOwner();
                if (entity instanceof LivingEntity livingEntityOwner) {
                    return livingEntityOwner;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public boolean interactsWithBlocks() {
        return this.blockInteraction != Explosion.BlockInteraction.KEEP;
    }

    private static void addBlockDrops(ObjectArrayList<Pair<ItemStack, BlockPos>> pDropPositionArray, ItemStack pItemStack, BlockPos pBlockPos) {
        int size = pDropPositionArray.size();

        for(int index = 0; index < size; ++index) {
            Pair<ItemStack, BlockPos> pair = pDropPositionArray.get(index);
            ItemStack itemstack = pair.getFirst();
            if (ItemEntity.areMergable(itemstack, pItemStack)) {
                ItemStack itemStackMerged = ItemEntity.merge(itemstack, pItemStack, 16);
                pDropPositionArray.set(index, Pair.of(itemStackMerged, pair.getSecond()));
                if (pItemStack.isEmpty()) {
                    return;
                }
            }
        }

        pDropPositionArray.add(Pair.of(pItemStack, pBlockPos));
    }

    public void setPos(Vec3 vec3) {
        position = vec3;
        x = vec3.x;
        y = vec3.y;
        z = vec3.z;
    }

    public void setPos(int pX, int pY, int pZ) {
        x = pX;
        y = pY;
        z = pZ;
        position = new Vec3(pX,pY,pZ);
    }
}
