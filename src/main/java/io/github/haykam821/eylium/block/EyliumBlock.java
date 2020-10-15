package io.github.haykam821.eylium.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NyliumBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EyliumBlock extends NyliumBlock {
	private static final BlockState DEAD_STATE = Blocks.END_STONE.getDefaultState();
	private static final BlockState SPROUTS_STATE = ModBlocks.END_SPROUTS.getBlock().getDefaultState();
	private static final BlockState TALL_SPROUTS_LOWER_STATE = ModBlocks.TALL_END_SPROUTS.getBlock().getDefaultState();
	private static final BlockState TALL_SPROUTS_UPPER_STATE = TALL_SPROUTS_LOWER_STATE.with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER);

	public EyliumBlock(Block.Settings settings) {
		super(settings);
	}

	private boolean stayAliveInEnd(BlockState state, ServerWorld world, BlockPos pos) {
		return world.getLightLevel(pos.up()) > 3 || world.getRegistryKey() == World.END;
	}

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!this.stayAliveInEnd(state, world, pos)) {
			world.setBlockState(pos, DEAD_STATE);
		}
	}

	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return world.isAir(pos.up());
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		BlockPos upPos = pos.up();
		BlockPos upUpPos = upPos.up();

		if (random.nextBoolean() && world.isAir(upUpPos)) {
			world.setBlockState(upPos, TALL_SPROUTS_LOWER_STATE);
			world.setBlockState(upUpPos, TALL_SPROUTS_UPPER_STATE);
		} else {
			world.setBlockState(upPos, SPROUTS_STATE);
		}
	}
}