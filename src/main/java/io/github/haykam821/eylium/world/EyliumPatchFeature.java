package io.github.haykam821.eylium.world;

import java.util.Random;

import com.mojang.serialization.Codec;

import io.github.haykam821.eylium.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class EyliumPatchFeature extends Feature<DefaultFeatureConfig> {
	private static final BlockState GRASS_STATE = ModBlocks.EYLIUM.getBlock().getDefaultState();
	private static final BlockState SPROUTS_STATE = ModBlocks.END_SPROUTS.getBlock().getDefaultState();
	private static final BlockState TALL_SPROUTS_LOWER_STATE = ModBlocks.TALL_END_SPROUTS.getBlock().getDefaultState();
	private static final BlockState TALL_SPROUTS_UPPER_STATE = TALL_SPROUTS_LOWER_STATE.with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER);

	public EyliumPatchFeature(Codec<DefaultFeatureConfig> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos startPos, DefaultFeatureConfig config) {
		int size = random.nextInt(3) + 3;
		int radius2 = size * size;

		Iterable<BlockPos> iterable = BlockPos.iterate(startPos.add(-size, -3, -size), startPos.add(size, 3, size));
		for (BlockPos pos : iterable) {
			BlockPos topPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos).down();
			if (Math.pow(pos.getX() - startPos.getX(), 2) + Math.pow(pos.getZ() - startPos.getZ(), 2) <= radius2) {
				BlockState state = world.getBlockState(topPos);

				BlockPos upPos = topPos.up();
				BlockState upState = world.getBlockState(upPos);

				if (state.isOf(Blocks.END_STONE) && upState.isAir()) {			
					this.setBlockState(world, topPos, GRASS_STATE);

					if (random.nextInt(10) == 0) {
						BlockPos upUpPos = upPos.up();
						BlockState upUpState = world.getBlockState(upUpPos);
			
						if (random.nextBoolean() && upUpState.isAir()) {
							this.setBlockState(world, upPos, TALL_SPROUTS_LOWER_STATE);
							this.setBlockState(world, upUpPos, TALL_SPROUTS_UPPER_STATE);
						} else {
							this.setBlockState(world, upPos, SPROUTS_STATE);
						}
					}
				}
			}
		}

		return true;
	}
}