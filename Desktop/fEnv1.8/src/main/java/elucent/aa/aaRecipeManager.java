package elucent.aa;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class aaRecipeManager {
	public static void createRecipes(){
		GameRegistry.addShapelessRecipe(new ItemStack(aaItemManager.itemMagicChalk,1), new ItemStack(Blocks.clay,1), new ItemStack(aaItemManager.itemMaterial,1,0), new ItemStack(Items.dye,1,15));
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.arcaneFocus,1), " X ", "XYX", " X ", 'X', Blocks.hardened_clay, 'Y', new ItemStack(aaItemManager.itemMaterial,1,0));
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.itemAlchemyFlask,1,0), "  W", "XX ", "XX ", 'X', Blocks.glass, 'W', Blocks.log);
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.itemAlchemyFlask,1,0), "  W", "XX ", "XX ", 'X', Blocks.glass, 'W', Blocks.log2);
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.itemAlchemyFlask,1,0), "W  ", " XX", " XX", 'X', Blocks.glass, 'W', Blocks.log);
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.itemAlchemyFlask,1,0), "W  ", " XX", " XX", 'X', Blocks.glass, 'W', Blocks.log2);
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.reducerBlock,1), "Y Y", "YWY", "XZX", 'X', Blocks.stone, 'Y', Blocks.stonebrick, 'Z', new ItemStack(Items.coal,1,OreDictionary.WILDCARD_VALUE), 'W', new ItemStack(aaItemManager.itemMaterial,1,0));
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.itemMaterial,1,1), " X ", "XYX", " X ", 'X', new ItemStack(aaItemManager.itemMaterial,1,0), 'Y', Blocks.glass);
		GameRegistry.addSmelting(new ItemStack(aaItemManager.itemMaterial,1,1), new ItemStack(aaItemManager.itemMaterial,1,2), 3.0f);
		GameRegistry.addShapelessRecipe(new ItemStack(aaItemManager.itemMaterial,4,0), new ItemStack(Items.coal,1,0), new ItemStack(Items.gunpowder,1));
		GameRegistry.addShapelessRecipe(new ItemStack(aaItemManager.itemMaterial,4,0), new ItemStack(Items.coal,1,1), new ItemStack(Items.gunpowder,1));
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.itemMaterial,1,4), " X ", "XYX", " X ", 'X', new ItemStack(aaItemManager.itemMaterial,1,3), 'Y', Blocks.glass);
		GameRegistry.addSmelting(new ItemStack(aaItemManager.itemMaterial,1,4), new ItemStack(aaItemManager.itemMaterial,1,5), 3.0f);
		GameRegistry.addShapelessRecipe(new ItemStack(aaItemManager.itemMaterial,4,3), new ItemStack(Items.sugar,1,0), new ItemStack(Items.redstone,1));
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.itemMaterial,1,7), " X ", "XYX", " X ", 'X', new ItemStack(aaItemManager.itemMaterial,1,8), 'Y', Blocks.glass);
		GameRegistry.addSmelting(new ItemStack(aaItemManager.itemMaterial,1,7), new ItemStack(aaItemManager.itemMaterial,1,6), 3.0f);
		GameRegistry.addShapelessRecipe(new ItemStack(aaItemManager.itemMaterial,4,6), new ItemStack(Items.blaze_powder,1,0), new ItemStack(Items.glowstone_dust,1));
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.fireInfuserBlock,1,0), "GLG", "WCW", " B ", 'G', Items.gold_ingot, 'L', Blocks.glass, 'W', Blocks.planks, 'C', new ItemStack(aaItemManager.itemMaterial,1,5), 'B', Blocks.iron_block);
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.earthInfuserBlock,1,0), " L ", "GCG", "IBI", 'G', Items.gold_ingot, 'L', Blocks.glass, 'C', new ItemStack(aaItemManager.itemMaterial,1,5), 'B', Blocks.iron_block, 'I', Items.iron_ingot);
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.waterInfuserBlock,1,0), "GLG", "G G", "BCB", 'G', Items.gold_ingot, 'L', Blocks.glass, 'C', new ItemStack(aaItemManager.itemMaterial,1,5), 'B', Blocks.iron_block);
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.airInfuserBlock,1,0), "GIG", "GLG", "WCW", 'G', Items.gold_ingot, 'L', Blocks.glass, 'W', Blocks.planks, 'C', new ItemStack(aaItemManager.itemMaterial,1,5), 'I', Items.iron_ingot);
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.lightInfuserBlock,1,0), "GGG", "LCB", "GGG", 'G', Items.gold_ingot, 'L', Blocks.glass, 'C', new ItemStack(aaItemManager.itemMaterial,1,5), 'B', Blocks.gold_block);
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.voidInfuserBlock,1,0), "LII", "I I", "BCB", 'I', Items.iron_ingot, 'L', Blocks.glass, 'C', new ItemStack(aaItemManager.itemMaterial,1,5), 'B', Blocks.iron_block);
		GameRegistry.addShapedRecipe(new ItemStack(aaItemManager.diffuserBlock), "SGS", "SCS", "BGB", 'S', Blocks.stone, 'G', Items.gold_ingot, 'B', Blocks.stonebrick, 'C', new ItemStack(aaItemManager.itemMaterial,1,2));
	}
}
