package net.safron1111.nezzeco.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.block.ModBlocks;
import net.safron1111.nezzeco.item.ModItems;
import net.safron1111.nezzeco.util.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.ComponentSampleModel;

@SuppressWarnings("deprecation")
public class MillstoneBlock extends HorizontalDirectionalBlock {
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,8,16);
    public static final String tagName = "ergotism";
    private static final MobEffect effect = MobEffects.WITHER;
    private static final int duration = 12;

    public MillstoneBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    private static boolean isMillableCoarse(ItemStack pStack) {
        return pStack.is(ModTags.Items.MILLABLE_COARSE);
    }
    private static boolean isMillable(ItemStack pStack) {
        return pStack.is(ModTags.Items.MILLABLE);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        rotate(pPlayer,pLevel,pPos,pState);
        pLevel.playSound(pPlayer,pPos,SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS,1,1);
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if ((isMillable(stack) |  isMillableCoarse(stack)) && (pState.getValue(FACING) == Direction.NORTH  | pState.getValue(FACING) == Direction.SOUTH)) {
            Vec3 vec3 = pPos.getCenter();
            ItemEntity stackEntity = new ItemEntity(pLevel,vec3.x,vec3.y,vec3.z,stack);
            if (isMillableCoarse(stack)) {
                if (stack.is(ModItems.ERGOT_RYE.get())) {
                    ItemStack poisonousFlourStack = new ItemStack(ModItems.COARSE_RYE_FLOUR.get());

                    CompoundTag ergotism = poisonousFlourStack.getOrCreateTagElement(NezzeCo.MOD_ID+":"+tagName);
                    ergotism.putInt("effectID",MobEffect.getId(effect));
                    ergotism.putInt("effectDuration",duration*20);

                    stackEntity.spawnAtLocation(poisonousFlourStack);
                } else {
                    stackEntity.spawnAtLocation(ModItems.COARSE_RYE_FLOUR.get());
                }
            } else if (isMillable(stack)) {
                if (stack.hasTag()) {
                    CompoundTag nbt = stack.getTag();
                    if (nbt != null && nbt.contains(NezzeCo.MOD_ID+":"+tagName)) {
                        ItemStack poisonousFlourStack = new ItemStack(ModItems.RYE_FLOUR.get());

                        CompoundTag ergotism = poisonousFlourStack.getOrCreateTagElement(NezzeCo.MOD_ID+":"+tagName);
                        ergotism.putInt("effectID",MobEffect.getId(MobEffects.WITHER));
                        ergotism.putInt("effectDuration",duration*20);

                        stackEntity.spawnAtLocation(poisonousFlourStack);
                    }
                } else {
                    stackEntity.spawnAtLocation(ModItems.RYE_FLOUR.get());
                }
            }
            if (!pPlayer.isCreative()) {
                stack.setCount(stack.getCount()-1);
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void rotate(LivingEntity user, Level level, BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(FACING) == Direction.NORTH) {
            level.setBlockAndUpdate(blockPos, ModBlocks.MILLSTONE.get().defaultBlockState().setValue(FACING, Direction.EAST));
        } else if (blockState.getValue(FACING) == Direction.EAST) {
            level.setBlockAndUpdate(blockPos, ModBlocks.MILLSTONE.get().defaultBlockState().setValue(FACING, Direction.SOUTH));
        } else if (blockState.getValue(FACING) == Direction.SOUTH) {
            level.setBlockAndUpdate(blockPos, ModBlocks.MILLSTONE.get().defaultBlockState().setValue(FACING, Direction.WEST));
        } else if (blockState.getValue(FACING) == Direction.WEST) {
            level.setBlockAndUpdate(blockPos, ModBlocks.MILLSTONE.get().defaultBlockState().setValue(FACING, Direction.NORTH));
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
