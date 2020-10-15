package io.github.haykam821.eylium;

import io.github.haykam821.eylium.block.ModBlocks;
import io.github.haykam821.eylium.world.EyliumPatchFeature;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;

public class Main implements ModInitializer {
	public static final String MOD_ID = "eylium";

	// Features
	private static final Identifier EYLIUM_PATCH_ID = new Identifier(MOD_ID, "eylium_patch");
	public static final EyliumPatchFeature EYLIUM_PATCH = new EyliumPatchFeature(DefaultFeatureConfig.CODEC);

	private static final Identifier UNCOMMON_EYLIUM_PATCH_ID = new Identifier(MOD_ID, "uncommon_eylium_patch");
	public static final ConfiguredFeature<?, ?> UNCOMMON_EYLIUM_PATCH = EYLIUM_PATCH.configure(FeatureConfig.DEFAULT).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeatRandomly(1);

	private static final Identifier COMMON_EYLIUM_PATCH_ID = new Identifier(MOD_ID, "common_eylium_patch");
	public static final ConfiguredFeature<?, ?> COMMON_EYLIUM_PATCH = EYLIUM_PATCH.configure(FeatureConfig.DEFAULT).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeatRandomly(5);

	@Override
	public void onInitialize() {
		ModBlocks.register();

		// Features
		Registry.register(Registry.FEATURE, EYLIUM_PATCH_ID, EYLIUM_PATCH);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, UNCOMMON_EYLIUM_PATCH_ID, UNCOMMON_EYLIUM_PATCH);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, COMMON_EYLIUM_PATCH_ID, COMMON_EYLIUM_PATCH);
	}
}