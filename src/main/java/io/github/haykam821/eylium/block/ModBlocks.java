package io.github.haykam821.eylium.block;

import java.util.function.Function;

import io.github.haykam821.eylium.Main;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum ModBlocks {
	END_SPROUTS("end_sprouts", new EyliumSproutsBlock(FabricBlockSettings.copyOf(Blocks.NETHER_SPROUTS).materialColor(MaterialColor.CYAN)), ItemGroup.DECORATIONS),
	TALL_END_SPROUTS("tall_end_sprouts", new TallEyliumSproutsBlock(FabricBlockSettings.copyOf(Blocks.TALL_GRASS).materialColor(MaterialColor.CYAN)), block -> new TallBlockItem(block, new Item.Settings().group(ItemGroup.DECORATIONS))),
	EYLIUM("eylium", new EyliumBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_NYLIUM).sounds(BlockSoundGroup.STONE).materialColor(MaterialColor.CYAN)), ItemGroup.BUILDING_BLOCKS);

	private final Block block;
	private final BlockItem item;

	private ModBlocks(String path, Block block, BlockItem item) {
		Identifier id = new Identifier(Main.MOD_ID, path);

		this.block = block;
		Registry.register(Registry.BLOCK, id, this.block);

		this.item = item;
		Registry.register(Registry.ITEM, id, this.item);
	}

	private ModBlocks(String path, Block block, Function<Block, BlockItem> itemFunction) {
		this(path, block, itemFunction.apply(block));
	}

	private ModBlocks(String path, Block block, ItemGroup group) {
		this(path, block, new BlockItem(block, new Item.Settings().group(group)));
	}

	public Block getBlock() {
		return this.block;
	}

	public BlockItem getItem() {
		return this.item;
	}

	public static void register() {
		return;
	}
}