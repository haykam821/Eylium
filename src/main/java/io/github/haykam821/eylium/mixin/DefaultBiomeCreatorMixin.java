package io.github.haykam821.eylium.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import io.github.haykam821.eylium.Main;
import net.minecraft.world.biome.DefaultBiomeCreator;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;

@Mixin(DefaultBiomeCreator.class)
public class DefaultBiomeCreatorMixin {
	@ModifyVariable(method = "createEndMidlands", ordinal = 0, at = @At(value = "TAIL", shift = At.Shift.BEFORE))
	private static GenerationSettings.Builder addEyliumPatchToEndMidlands(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, Main.UNCOMMON_EYLIUM_PATCH);
		return builder;
	}

	@ModifyVariable(method = "createEndHighlands", ordinal = 0, at = @At(value = "TAIL", shift = At.Shift.BEFORE))
	private static GenerationSettings.Builder addEyliumPatchToEndHighlands(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, Main.COMMON_EYLIUM_PATCH);
		return builder;
	}
}