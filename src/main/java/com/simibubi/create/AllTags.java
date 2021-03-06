package com.simibubi.create;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.nullness.NonNullFunction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;

public class AllTags {
	private static final CreateRegistrate REGISTRATE = Create.registrate()
		.itemGroup(() -> Create.baseCreativeTab);
	
	public static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, ItemBuilder<BlockItem, BlockBuilder<T, P>>> tagBlockAndItem(
		String tagName) {
		return b -> b.tag(forgeBlockTag(tagName))
			.item()
			.tag(forgeItemTag(tagName));
	}

	public static Tag<Block> forgeBlockTag(String name) {
		return forgeTag(BlockTags.getCollection(), name);
	}

	public static Tag<Item> forgeItemTag(String name) {
		return forgeTag(ItemTags.getCollection(), name);
	}

	public static <T> Tag<T> forgeTag(TagCollection<T> collection, String name) {
		return tag(collection, "forge", name);
	}

	public static <T> Tag<T> tag(TagCollection<T> collection, String domain, String name) {
		return collection.getOrCreate(new ResourceLocation(domain, name));
	}

	public static enum AllItemTags {
		CRUSHED_ORES,
		CREATE_INGOTS,
		beacon_payment("", "forge"),
		ingots("", "forge"),
		nuggets("", "forge"),
		plates("", "forge");

		public Tag<Item> tag;

		private AllItemTags() {
			this("");
		}

		private AllItemTags(String path) {
			this(path, Create.ID);
		}

		private AllItemTags(String path, String modid) {
			tag = new ItemTags.Wrapper(
				new ResourceLocation(modid, (path.isEmpty() ? "" : path + "/") + Lang.asId(name())));
		}

		public boolean matches(ItemStack stack) {
			return tag.contains(stack.getItem());
		}
	}

	public static enum AllBlockTags {
		WINDMILL_SAILS, FAN_HEATERS, WINDOWABLE,;

		public Tag<Block> tag;

		private AllBlockTags() {
			this("");
		}

		private AllBlockTags(String path) {
			tag = new BlockTags.Wrapper(
				new ResourceLocation(Create.ID, (path.isEmpty() ? "" : path + "/") + Lang.asId(name())));
		}

		public boolean matches(BlockState block) {
			return tag.contains(block.getBlock());
		}
	}
	
	public static void register() {
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> prov.getBuilder(AllItemTags.beacon_payment.tag)
			.add(AllItemTags.CREATE_INGOTS.tag));
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> prov.getBuilder(AllItemTags.ingots.tag)
			.add(AllItemTags.CREATE_INGOTS.tag));
	}
}
