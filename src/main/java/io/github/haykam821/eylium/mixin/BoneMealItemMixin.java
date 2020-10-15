package io.github.haykam821.eylium.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.haykam821.eylium.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
	@Unique
	private static final BlockState SPREAD_STATE = ModBlocks.EYLIUM.getBlock().getDefaultState();

	@Inject(method = "useOnFertilizable", at = @At("HEAD"), cancellable = true)
	private static void useOnEndStone(ItemStack stack, World world, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
		BlockState state = world.getBlockState(pos);
		if (state.isOf(Blocks.END_STONE) && world instanceof ServerWorld) {
			Iterator<BlockPos> iterator = BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1)).iterator();
			while (iterator.hasNext()) {
				BlockPos spreadPos = iterator.next();
				if (world.getBlockState(spreadPos).isOf(ModBlocks.EYLIUM.getBlock())) {
					world.setBlockState(pos, SPREAD_STATE, 3);
					stack.decrement(1);

					ci.setReturnValue(true);
					break;
				}
			}
		}
	}
}