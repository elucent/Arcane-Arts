package elucent.aa;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class aaRecipeManager {
	public static void createRecipes(){
		//GameRegistry.addShapelessRecipe(new ItemStack(aaItemManager.itemMagicChalk,1), new ItemStack(Blocks.clay,1), new ItemStack(aaItemManager.itemMaterial,1,0), new ItemStack(Items.dye,1,15));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.arcaneFocus,1), true, new Object[]{" X ", "XYX", " X ", 'X', Blocks.hardened_clay, 'Y', new ItemStack(aaItemManager.itemMaterial,1,0)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.itemAlchemyFlask,1,0), true, new Object[]{"  W", "XX ", "XX ", 'X', "blockGlass", 'W', "logWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.reducerBlock,1), true, new Object[]{"Y Y", "YWY", "XZX", 'X', "cobblestone", 'Y', Blocks.stonebrick, 'Z', Items.coal, 'W', new ItemStack(aaItemManager.itemMaterial,1,0)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.itemMaterial,1,1), true, new Object[]{" X ", "XYX", " X ", 'X', new ItemStack(aaItemManager.itemMaterial,1,0), 'Y', "blockGlass"}));
		GameRegistry.addSmelting(new ItemStack(aaItemManager.itemMaterial,1,1), new ItemStack(aaItemManager.itemMaterial,1,2), 3.0f);
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(aaItemManager.itemMaterial,4,0), new Object[]{new ItemStack(Items.coal,1,1), Items.gunpowder}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.itemMaterial,1,4), true, new Object[]{" X ", "XYX", " X ", 'X', new ItemStack(aaItemManager.itemMaterial,1,3), 'Y', "blockGlass"}));
		GameRegistry.addSmelting(new ItemStack(aaItemManager.itemMaterial,1,4), new ItemStack(aaItemManager.itemMaterial,1,5), 3.0f);
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(aaItemManager.itemMaterial,4,3), new Object[]{new ItemStack(Items.dye,1,4), "dustRedstone"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.itemMaterial,1,7), true, new Object[]{" X ", "XYX", " X ", 'X', new ItemStack(aaItemManager.itemMaterial,1,6), 'Y', "blockQuartz"}));
		GameRegistry.addSmelting(new ItemStack(aaItemManager.itemMaterial,1,7), new ItemStack(aaItemManager.itemMaterial,1,8), 3.0f);
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(aaItemManager.itemMaterial,4,6), new Object[]{new ItemStack(Items.blaze_powder,1,0), new ItemStack(Items.glowstone_dust,1)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.fireInfuserBlock,1,0), true, new Object[]{"GLG", "WCW", " B ", 'G', "ingotGold", 'L', "blockGlass", 'W', "plankWood", 'C', new ItemStack(aaItemManager.itemMaterial,1,5), 'B', "blockIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.earthInfuserBlock,1,0), true, new Object[]{" L ", "GCG", "IBI", 'G', "ingotGold", 'L', "blockGlass", 'C', new ItemStack(aaItemManager.itemMaterial,1,5), 'B', "blockIron", 'I', "ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.waterInfuserBlock,1,0), true, new Object[]{"GLG", "G G", "BCB", 'G', "ingotGold", 'L', "blockGlass", 'C', new ItemStack(aaItemManager.itemMaterial,1,5), 'B', "blockIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.airInfuserBlock,1,0), true, new Object[]{"GIG", "GLG", "WCW", 'G', "ingotGold", 'L', "blockGlass", 'W', "plankWood", 'C', new ItemStack(aaItemManager.itemMaterial,1,5), 'I', "ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.lightInfuserBlock,1,0), true, new Object[]{"GGG", "LCB", "GGG", 'G', "ingotGold", 'L', "blockGlass", 'C', new ItemStack(aaItemManager.itemMaterial,1,5), 'B', "blockGold"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.voidInfuserBlock,1,0), true, new Object[]{"LII", "I I", "BCB", 'I', "ingotIron", 'L', "blockGlass", 'C', new ItemStack(aaItemManager.itemMaterial,1,5), 'B', "blockIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.diffuserBlock), true, new Object[]{"BGB", "BCB", "SGS", 'S', "cobblestone", 'G', "ingotGold", 'B', Blocks.stonebrick, 'C', new ItemStack(aaItemManager.itemMaterial,1,2)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.vesselBlock), true, new Object[]{"BCB", "C C", "BCB", 'B', Blocks.stonebrick, 'C', new ItemStack(aaItemManager.itemMaterial,1,2)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.containerDrainBlock), new Object[]{"I I", "IFI", "ICI", 'I', "ingotIron", 'F', Blocks.furnace, 'C', new ItemStack(aaItemManager.itemMaterial,1,5)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.arcaneForgeBlock), true, new Object[]{"GBG", "RCR", "SBS", 'S', "cobblestone", 'G', "ingotGold", 'B', "blockIron", 'C', new ItemStack(aaItemManager.itemMaterial,1,8), 'R', new ItemStack(aaItemManager.itemMaterial,1,15)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.beamPistol), true, new Object[]{"WI ", "IRI", " LR", 'I', "ingotIron", 'W', new ItemStack(aaItemManager.itemMaterial,1,11), 'R', new ItemStack(aaItemManager.itemMaterial,1,15), 'L', "logWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.invigoratingUrn), true, new Object[]{" B ", "HCH", "BHB", 'C', new ItemStack(aaItemManager.itemMaterial,1,3), 'B', Items.brick, 'H', Blocks.hardened_clay}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.vial,2,0), true, new Object[]{"P", "G", "G", 'G', "blockGlass", 'P', "plankWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.simpleFormatorBlock,1,0), true, new Object[]{"RPA", "RCA", "RCA", 'R', new ItemStack(aaItemManager.itemMaterial,1,13), 'A', new ItemStack(aaItemManager.itemMaterial,1,14), 'C', "cobblestone", 'P', Items.ender_pearl}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.crucibleBlock,1,0), true, new Object[]{"I I", "I I", "CBC", 'I', "ingotIron", 'C', "cobblestone", 'B', new ItemStack(aaItemManager.itemMaterial,1,2)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.forgeHammer,1), true, new Object[]{"ILI"," S "," S ", 'I', "ingotIron", 'L', Items.leather, 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.swordReceptive,1), true, new Object[]{" I "," I "," S ", 'I', new ItemStack(aaItemManager.itemMaterial,1,15), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.pickaxeReceptive,1), true, new Object[]{"III"," S "," S ", 'I', new ItemStack(aaItemManager.itemMaterial,1,15), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.axeReceptive,1), true, new Object[]{" II"," SI"," S ", 'I', new ItemStack(aaItemManager.itemMaterial,1,15), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.axeReceptive,1), true, new Object[]{"II ","IS "," S ", 'I', new ItemStack(aaItemManager.itemMaterial,1,15), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.shovelReceptive,1), true, new Object[]{" I "," S "," S ", 'I', new ItemStack(aaItemManager.itemMaterial,1,15), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.hoeReceptive,1), true, new Object[]{"II "," S "," S ", 'I', new ItemStack(aaItemManager.itemMaterial,1,15), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.hoeReceptive,1), true, new Object[]{" II"," S "," S ", 'I', new ItemStack(aaItemManager.itemMaterial,1,15), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.swordRegenerative,1), true, new Object[]{" I "," I "," S ", 'I', new ItemStack(aaItemManager.regenerativeMetal,1), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.pickaxeRegenerative,1), true, new Object[]{"III"," S "," S ", 'I', new ItemStack(aaItemManager.regenerativeMetal,1), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.axeRegenerative,1), true, new Object[]{" II"," SI"," S ", 'I', new ItemStack(aaItemManager.regenerativeMetal,1), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.axeRegenerative,1), true, new Object[]{"II ","IS "," S ", 'I', new ItemStack(aaItemManager.regenerativeMetal,1), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.shovelRegenerative,1), true, new Object[]{" I "," S "," S ", 'I', new ItemStack(aaItemManager.regenerativeMetal,1), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.hoeRegenerative,1), true, new Object[]{"II "," S "," S ", 'I', new ItemStack(aaItemManager.regenerativeMetal,1), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.hoeRegenerative,1), true, new Object[]{" II"," S "," S ", 'I', new ItemStack(aaItemManager.regenerativeMetal,1), 'S', "stickWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.advancedFormatorBlock,1), new Object[]{"BCB", "GSG", "RGR", 'G', "ingotGold", 'B', Blocks.gold_block, 'C', new ItemStack(aaItemManager.scarletStone,1), 'R', "cobblestone", 'S', new ItemStack(aaItemManager.simpleFormatorBlock,1)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aaItemManager.pickaxeReceptive,1), true, new Object[]{"III"," S "," S ", 'I', new ItemStack(aaItemManager.itemMaterial,1,15), 'S', "stickWood"}));
	}
}
