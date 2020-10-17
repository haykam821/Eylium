package io.github.haykam821.eylium.block;

import io.github.haykam821.eylium.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;

public class TallEndSproutsBlock extends TallPlantBlock {
	private static final BlockState AIR_STATE = Blocks.AIR.getDefaultState();
	private static final BlockState RESULT_STATE = ModBlocks.END_SPROUTS.getBlock().getDefaultState();

	public TallEndSproutsBlock(Block.Settings settings) {
		super(settings);
	}

	private void shorten(World world, BlockPos pos) {
		world.setBlockState(pos.up(), AIR_STATE);
		world.setBlockState(pos, RESULT_STATE);
		world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1, 1);
	}

	private boolean canCauseShortening(World world, Entity entity) {
		return entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
	}

	@Override
	public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isIn(Main.END_SPROUTS_PLANTABLE_ON);
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
				if (scaleData.getScale() <= 0.51 && this.canCauseShortening(world, entity) && state.get(TallPlantBlock.HALF) == DoubleBlockHalf.LOWER) {
					this.shorten(world, pos);
				}
			}
		}

		super.onEntityCollision(state, world, pos, entity);
	}
}