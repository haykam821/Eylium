package io.github.haykam821.eylium.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FernBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class EyliumSproutsBlock extends FernBlock {
	public EyliumSproutsBlock(Block.Settings settings) {
		super(settings);
	}

	@Override
	public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isOf(ModBlocks.EYLIUM.getBlock());
	}
}