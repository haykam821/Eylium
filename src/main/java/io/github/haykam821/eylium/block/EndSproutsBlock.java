package io.github.haykam821.eylium.block;

import java.util.Random;

import io.github.haykam821.eylium.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FernBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class EndSproutsBlock extends FernBlock {
	public EndSproutsBlock(Block.Settings settings) {
		super(settings);
	}

	@Override
	public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isIn(Main.END_SPROUTS_PLANTABLE_ON);
	}

	private TallPlantBlock getGrowthBlock() {
		return (TallPlantBlock) ModBlocks.TALL_END_SPROUTS.getBlock();
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		TallPlantBlock growthBlock = this.getGrowthBlock();
		if (growthBlock.getDefaultState().canPlaceAt(world, pos) && world.isAir(pos.up())) {
			growthBlock.placeAt(world, pos, 2);
		}
	}
}