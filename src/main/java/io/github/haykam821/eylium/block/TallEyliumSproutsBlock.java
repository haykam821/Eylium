package io.github.haykam821.eylium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;

public class TallEyliumSproutsBlock extends TallPlantBlock {
	private static final BlockState AIR_STATE = Blocks.AIR.getDefaultState();
	private static final BlockState RESULT_STATE = ModBlocks.END_SPROUTS.getBlock().getDefaultState();

	public TallEyliumSproutsBlock(Block.Settings settings) {
		super(settings);
	}

	@Override
	public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isOf(ModBlocks.EYLIUM.getBlock()) || floor.isOf(Blocks.END_STONE);
	}

	@Override
 	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!entity.canAvoidTraps() && !world.isClient()) {
			ScaleData scaleData = ScaleData.of(entity, ScaleType.HEIGHT);

			if (scaleData.getScale() > 0.5) {
				scaleData.setScaleTickDelay(0);
				scaleData.setTargetScale(scaleData.getScale() * 0.99f);

				scaleData.markForSync();

				// Break block when matching its height
				if (scaleData.getScale() <= 0.51 && state.get(TallPlantBlock.HALF) == DoubleBlockHalf.LOWER) {
					world.setBlockState(pos.up(), AIR_STATE);
					world.setBlockState(pos, RESULT_STATE);
					world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1, 1);
				}
			}
		}

		super.onEntityCollision(state, world, pos, entity);
	}
}