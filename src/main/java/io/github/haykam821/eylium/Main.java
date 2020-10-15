package io.github.haykam821.eylium;

import io.github.haykam821.eylium.block.ModBlocks;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
	public static final String MOD_ID = "eylium";

	@Override
	public void onInitialize() {
		ModBlocks.register();
	}
}