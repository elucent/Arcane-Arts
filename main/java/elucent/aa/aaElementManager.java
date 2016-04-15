package elucent.aa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.oredict.OreDictionary;

public class aaElementManager {
	public static aaElement elementFire, elementLight, elementEarth, elementWater, elementAir, elementVoid, elementRaw;
	public static aaElement itemStone;
	public static aaElement itemMetal;
	public static aaElement itemGem;
	public static aaElement itemGel;
	public static aaElement itemReactant;
	public static aaElement itemPlant;
	public static aaElement itemFlesh;
	public static aaElement itemDry;
	
	static Random random = new Random();
	
	public static ArrayList<aaElementRecipe> recipes = new ArrayList<aaElementRecipe>();
	public static ArrayList<aaOreElementRecipe> oreRecipes = new ArrayList<aaOreElementRecipe>();
	public static ArrayList<aaSimpleInfusionRecipe> infusions = new ArrayList<aaSimpleInfusionRecipe>();
	public static ArrayList<aaCrucibleRecipe> mixtures = new ArrayList<aaCrucibleRecipe>();
	public static ArrayList<aaComplexInfusionRecipe> complexInfusions = new ArrayList<aaComplexInfusionRecipe>();
	
	public static aaElementRecipe getRecipeForItem(ItemStack stack){
		for (int i = 0; i < oreRecipes.size(); i ++){
			if (oreRecipes.get(i).doesMatch(stack)){
				return oreRecipes.get(i);
			}
		}
		for (int i = 0; i < recipes.size(); i ++){
			if (recipes.get(i).item.getItem() == stack.getItem() && stack.getMetadata() == recipes.get(i).item.getMetadata()){
				return recipes.get(i);
			}
		}
		return null;
	}
	
	public static aaElement getElementFromString(String s){
		if (s.equals("fire")){
			return elementFire;
		}
		else if (s.equals("earth")){
			return elementEarth;
		}
		else if (s.equals("water")){
			return elementWater;
		}
		else if (s.equals("air")){
			return elementAir;
		}
		else if (s.equals("light")){
			return elementLight;
		}
		else if (s.equals("void")){
			return elementVoid;
		}
		else if (s.equals("stone")){
			return itemStone;
		}
		else if (s.equals("metal")){
			return itemMetal;
		}
		else if (s.equals("gem")){
			return itemGem;
		}
		else if (s.equals("reactant")){
			return itemReactant;
		}
		else if (s.equals("dry")){
			return itemDry;
		}
		else if (s.equals("flesh")){
			return itemFlesh;
		}
		else if (s.equals("plant")){
			return itemPlant;
		}
		else if (s.equals("gel")){
			return itemGel;
		}
		else if (s.equals("raw")){
			return elementRaw;
		}
		else return elementFire;
	}
	
	public static double getValue(ArrayList<aaElementValue> values, String s){
		for (int i = 0; i < values.size(); i ++){
			if (values.get(i).getElement().getName() == s){
				return values.get(i).getValue();
			}
		}
		return 0;
	}
	
	public aaElementManager(){
		initElements();
	}
	
	public void initElements(){
		elementFire = new aaElement("fire",new Vec3i(255,64,0));
		elementLight = new aaElement("light",new Vec3i(255,255,192));
		elementEarth = new aaElement("earth",new Vec3i(0,192,0));
		elementWater = new aaElement("water",new Vec3i(32,0,192));
		elementAir = new aaElement("air",new Vec3i(192,255,255));
		elementVoid = new aaElement("void",new Vec3i(32,0,32));
		itemStone = new aaElement("stone",new Vec3i(96,96,96));
		itemMetal = new aaElement("metal",new Vec3i(192,192,192));
		itemGem = new aaElement("gem",new Vec3i(128,255,128));
		itemReactant = new aaElement("reactant",new Vec3i(255,192,0));
		itemPlant = new aaElement("plant",new Vec3i(16,128,64));
		itemFlesh = new aaElement("flesh",new Vec3i(192,128,160));
		itemDry = new aaElement("dry",new Vec3i(192,192,128));
		itemGel = new aaElement("gel",new Vec3i(255,255,224));
		elementRaw = new aaElement("raw", new Vec3i(255,255,255));
	}
	
	public static boolean intArrayCompare(int[] a1, int[] a2){
		if (a1.length <= a2.length){
			for (int i = 0; i < a1.length; i ++){
				if (a1[i] == a2[i]){
					return true;
				}
			}
		}
		else {
			for (int i = 0; i < a2.length; i ++){
				if (a1[i] == a2[i]){
					return true;
				}
			}
		}
		return false;
	}
	
	public static ArrayList<aaElementValue> getStackComposition(ItemStack stack){
		ArrayList<aaElementValue> compos = new ArrayList<aaElementValue>();
		aaElementRecipe recipe = null;
		boolean doBreak = false;
		recipe = getRecipeForItem(stack);
		if (recipe != null){
			compos.add(recipe.evFire);
			compos.add(recipe.evEarth);
			compos.add(recipe.evWater);
			compos.add(recipe.evAir);
			compos.add(recipe.evLight);
			compos.add(recipe.evVoid);
			if (recipe.itemClass1 != null){
				compos.add(recipe.itemClass1);
			}
			if (recipe.itemClass2 != null){
				compos.add(recipe.itemClass2);
			}
		}
		return compos;
	}
	
	public static void registerRecipe(ItemStack item, float fireValue, float earthValue, float waterValue, float airValue, float lightValue, float voidValue, aaElementValue class1, aaElementValue class2){
		aaElementRecipe recipe = new aaElementRecipe(item,fireValue,earthValue,waterValue,airValue,lightValue,voidValue);
		recipe.setClasses(class1, class2);
		recipes.add(recipe);
	}
	
	public static void registerOreRecipe(String name, float fireValue, float earthValue, float waterValue, float airValue, float lightValue, float voidValue, aaElementValue class1, aaElementValue class2){
		aaOreElementRecipe recipe = new aaOreElementRecipe(name,fireValue,earthValue,waterValue,airValue,lightValue,voidValue);
		recipe.setClasses(class1, class2);
		oreRecipes.add(recipe);
	}
	
	public void initRecipes(){
		registerOreRecipe("coal",2,0,0,0,0,2,new aaElementValue(this.itemReactant,1),null);
		registerRecipe(new ItemStack(Items.fire_charge,1,0),8,0,0,0,8,0,new aaElementValue(this.itemReactant,4),null);
		registerOreRecipe("blockCoal",18,0,0,0,0,18,new aaElementValue(this.itemReactant,9),null);
		registerOreRecipe("dustRedstone",0,0,2,0,0,0,new aaElementValue(this.itemReactant,1),null);
		registerOreRecipe("blockRedstone",0,0,18,0,0,0,new aaElementValue(this.itemReactant,9),null);
		registerOreRecipe("dustGlowstone",0,0,0,0,2,0,new aaElementValue(this.itemReactant,4),null);
		registerOreRecipe("glowstone",0,0,0,0,8,0,new aaElementValue(this.itemReactant,16),null);
		registerRecipe(new ItemStack(Items.blaze_powder,1,0),4,0,0,0,4,0,new aaElementValue(this.itemReactant,16),null);
		registerOreRecipe("gunpowder",4,0,0,0,0,0,new aaElementValue(this.itemReactant,8),null);
		registerOreRecipe("dustSugar",0,0,0,2,0,0,new aaElementValue(this.itemReactant,1),null);
		
		registerOreRecipe("ingotIron",0,0,0,0,0,2,new aaElementValue(this.itemMetal,1),null);
		registerRecipe(new ItemStack(Items.cauldron,1,0),0,0,0,8,0,2,new aaElementValue(this.itemMetal,7),null);
		registerRecipe(new ItemStack(Items.minecart,1,0),0,0,4,0,0,10,new aaElementValue(this.itemMetal,5),null);
		registerRecipe(new ItemStack(Blocks.anvil,1,0),0,0,0,0,0,62,new aaElementValue(this.itemMetal,31),null);
		registerRecipe(new ItemStack(Blocks.anvil,1,1),0,0,0,0,0,42,new aaElementValue(this.itemMetal,21),null);
		registerRecipe(new ItemStack(Blocks.anvil,1,2),0,0,0,0,0,22,new aaElementValue(this.itemMetal,11),null);
		registerRecipe(new ItemStack(Blocks.iron_door,1,0),0,0,0,0,0,12,new aaElementValue(this.itemMetal,6),null);
		registerRecipe(new ItemStack(Blocks.iron_trapdoor,1,0),0,0,0,0,0,6,new aaElementValue(this.itemMetal,3),null);
		registerRecipe(new ItemStack(Blocks.heavy_weighted_pressure_plate,1,0),0,0,0,0,0,4,new aaElementValue(this.itemMetal,2),null);
		registerOreRecipe("blockIron",0,0,0,0,0,18,new aaElementValue(this.itemMetal,9),null);
		registerRecipe(new ItemStack(Blocks.light_weighted_pressure_plate,1,0),0,0,0,0,8,0,new aaElementValue(this.itemMetal,4),null);
		registerOreRecipe("ingotGold",0,0,0,0,4,0,new aaElementValue(this.itemMetal,2),null);
		registerOreRecipe("blockGold",0,0,0,0,36,0,new aaElementValue(this.itemMetal,18),null);
		
		registerRecipe(new ItemStack(Items.feather,1,0),0,0,0,8,0,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Items.blaze_rod,1,0),8,0,0,0,8,0,new aaElementValue(this.itemDry,8),null);
		registerRecipe(new ItemStack(Items.bone,1,0),0,0,0,0,0,4,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.skull,1,2),0,0,0,0,0,16,new aaElementValue(this.itemDry,4),null);
		registerRecipe(new ItemStack(Blocks.skull,1,0),0,64,0,0,0,64,new aaElementValue(this.itemDry,16),new aaElementValue(this.itemGem,4));
		registerRecipe(new ItemStack(Blocks.web,1,0),0,0,8,8,0,64,new aaElementValue(this.itemDry,4),null);
		registerRecipe(new ItemStack(Items.string,1,0),0,0,0,4,0,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.soul_sand,1,0),0,1,0,0,0,1,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,0),0,0,0,4,0,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,1),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,2),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,3),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,4),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,5),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,6),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,7),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,8),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,9),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,10),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,11),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,12),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,13),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,14),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.wool,1,15),0,0,0,4,2,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Items.painting,1,0),0,4,0,4,0,0,new aaElementValue(this.itemDry,1),new aaElementValue(this.itemPlant,4));
		registerRecipe(new ItemStack(Items.item_frame,1,0),0,8,0,0,0,0,new aaElementValue(this.itemFlesh,4),new aaElementValue(this.itemPlant,4));

		registerRecipe(new ItemStack(Blocks.yellow_flower,1,0),0,0,0,4,4,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.red_flower,1,0),0,0,0,4,4,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.red_flower,1,1),0,0,0,4,4,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.red_flower,1,2),0,0,0,4,4,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.red_flower,1,3),0,0,0,4,4,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.red_flower,1,4),0,0,0,4,4,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.red_flower,1,5),0,0,0,4,4,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.red_flower,1,6),0,0,0,4,4,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.red_flower,1,7),0,0,0,4,4,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.red_flower,1,8),0,0,0,4,4,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.double_plant,1,0),0,0,0,8,8,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Blocks.double_plant,1,1),0,0,0,8,8,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Blocks.double_plant,1,2),0,0,0,8,0,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Blocks.double_plant,1,3),0,0,0,8,0,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Blocks.double_plant,1,4),0,0,0,8,8,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Blocks.double_plant,1,5),0,0,0,8,8,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Items.dye,1,1),0,0,0,0,2,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.dye,1,2),2,0,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.dye,1,11),0,0,0,0,2,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.dye,1,3),0,0,0,0,2,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.potato,1,0),0,2,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.poisonous_potato,1,0),0,2,0,0,0,4,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.baked_potato,1,0),2,0,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.red_mushroom,1,0),0,0,0,0,4,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.brown_mushroom,1,0),0,0,0,0,0,4,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.melon_seeds,1,0),0,0,0,0,2,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Items.pumpkin_seeds,1,0),0,0,0,0,2,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Items.wheat_seeds,1,0),0,0,0,0,2,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Items.carrot,1,0),0,0,2,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.golden_carrot,1,0),0,0,2,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.melon,1,0),0,0,2,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Items.speckled_melon,1,0),0,0,2,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.pumpkin,1,0),4,0,0,0,0,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Blocks.lit_pumpkin,1,0),4,0,0,0,4,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Blocks.melon_block,1,0),0,4,8,0,0,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Items.wheat,1,0),0,0,0,4,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.bread,1,0),12,0,0,0,0,0,new aaElementValue(this.itemPlant,12),null);
		registerRecipe(new ItemStack(Items.stick,1,0),0,1,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Items.dye,1,3),0,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Items.apple,1,0),0,2,0,0,0,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Items.reeds,1,0),0,0,2,0,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Items.paper,1,0),0,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.sapling,1,0),0,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.sapling,1,1),0,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.sapling,1,2),0,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.sapling,1,3),0,0,2,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.sapling,1,4),2,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.sapling,1,5),0,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.log,1,0),0,8,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.log,1,1),0,8,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.log,1,2),0,8,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.log,1,3),0,8,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.log2,1,1),0,8,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.log2,1,1),0,8,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.waterlily,1,0),0,0,8,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.vine,1,0),0,0,2,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.leaves,1,0),0,0,0,2,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.leaves,1,1),0,0,0,2,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.leaves,1,2),0,0,0,2,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.leaves,1,3),0,0,0,2,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.leaves2,1,1),0,0,0,2,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.leaves2,1,1),0,0,0,2,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.planks,1,0),0,2,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.planks,1,1),0,2,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.planks,1,2),0,2,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.planks,1,3),0,2,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.planks,1,4),0,2,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.planks,1,5),0,2,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.chest,1,0),0,16,0,4,0,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Items.boat,1,0),0,10,4,0,0,0,new aaElementValue(this.itemPlant,5),null);
		registerRecipe(new ItemStack(Blocks.acacia_door,1,0),0,4,0,0,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.oak_door,1,0),0,4,0,0,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.dark_oak_door,1,0),0,4,0,0,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.spruce_door,1,0),0,4,0,0,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.birch_door,1,0),0,4,0,0,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.jungle_door,1,0),0,4,0,0,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.acacia_fence,1,0),0,6,0,0,0,0,new aaElementValue(this.itemPlant,3),null);
		registerRecipe(new ItemStack(Blocks.oak_fence,1,0),0,6,0,0,0,0,new aaElementValue(this.itemPlant,3),null);
		registerRecipe(new ItemStack(Blocks.dark_oak_fence,1,0),0,6,0,0,0,0,new aaElementValue(this.itemPlant,3),null);
		registerRecipe(new ItemStack(Blocks.spruce_fence,1,0),0,6,0,0,0,0,new aaElementValue(this.itemPlant,3),null);
		registerRecipe(new ItemStack(Blocks.birch_fence,1,0),0,6,0,0,0,0,new aaElementValue(this.itemPlant,3),null);
		registerRecipe(new ItemStack(Blocks.jungle_fence,1,0),0,6,0,0,0,0,new aaElementValue(this.itemPlant,3),null);
		registerRecipe(new ItemStack(Blocks.bookshelf,1,0),0,24,0,12,0,0,new aaElementValue(this.itemPlant,24),new aaElementValue(this.itemFlesh,12));
		registerRecipe(new ItemStack(Items.bowl,1,0),0,0,0,1,0,0,null,null);
		
		registerRecipe(new ItemStack(Items.dye,1,4),0,0,0,0,2,0,new aaElementValue(this.itemStone,2),null);
		registerRecipe(new ItemStack(Blocks.lapis_block,1,0),0,0,0,0,18,0,new aaElementValue(this.itemStone,18),null);
		registerRecipe(new ItemStack(Items.clay_ball,1,0),0,0,1,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Items.brick,1,0),2,0,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.brick_block,1,0),8,0,0,0,0,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Items.flower_pot,1,0),6,0,0,4,0,0,new aaElementValue(this.itemStone,3),null);
		registerRecipe(new ItemStack(Items.flint,1,0),0,2,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.stone,1,0),0,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.dirt,1,0),0,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.furnace,1,0),4,8,0,0,0,0,new aaElementValue(this.itemStone,8),null);
		registerRecipe(new ItemStack(Blocks.stone,1,1),0,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.netherrack,1,0),1,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Items.netherbrick,1,0),1,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.nether_brick,1,0),4,4,0,0,0,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.end_stone,1,0),0,0,0,0,0,4,new aaElementValue(this.itemStone,8),null);
		registerRecipe(new ItemStack(Blocks.stone,1,2),0,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.stone,1,3),0,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.stone,1,4),0,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.stone,1,5),0,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.stone,1,6),0,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.cobblestone,1,0),0,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.obsidian,1,0),2,8,0,0,0,0,new aaElementValue(this.itemStone,64),null);
		registerRecipe(new ItemStack(Blocks.stonebrick,1,0),0,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.stonebrick,1,1),0,1,1,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.stonebrick,1,2),0,1,0,0,0,1,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.stonebrick,1,3),0,2,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.quartz_ore,1,0),24,8,0,0,0,0,new aaElementValue(this.itemStone,16),null);
		registerRecipe(new ItemStack(Items.quartz,1,0),2,0,0,0,0,0,new aaElementValue(this.itemStone,2),null);
		registerRecipe(new ItemStack(Blocks.quartz_block,1,0),8,0,0,0,0,0,new aaElementValue(this.itemStone,8),null);
		registerRecipe(new ItemStack(Blocks.quartz_block,1,1),8,0,0,0,0,0,new aaElementValue(this.itemStone,8),null);
		registerRecipe(new ItemStack(Blocks.quartz_block,1,2),8,0,0,0,0,0,new aaElementValue(this.itemStone,8),null);
		registerRecipe(new ItemStack(Blocks.sand,1,0),0,0,2,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.sand,1,1),1,0,2,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.clay,1,0),0,0,4,0,0,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,0),4,0,0,0,0,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,1),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,2),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,3),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,4),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,5),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,6),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,7),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,8),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,9),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,10),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,11),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,12),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,13),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,14),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.hardened_clay,1,15),4,0,0,0,1,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.sandstone,1,0),4,4,0,0,0,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.sandstone,1,1),4,4,0,0,0,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.sandstone,1,2),4,4,0,0,0,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.prismarine,1),0,0,8,0,0,0,new aaElementValue(this.itemStone,8),null);
		registerRecipe(new ItemStack(Blocks.prismarine,1,1),0,0,8,0,0,0,new aaElementValue(this.itemStone,8),null);
		registerRecipe(new ItemStack(Blocks.prismarine,1,2),0,0,8,0,0,2,new aaElementValue(this.itemStone,8),null);
		registerRecipe(new ItemStack(Blocks.sea_lantern,1),0,0,0,0,12,0,new aaElementValue(this.itemStone,8),null);
		registerRecipe(new ItemStack(Items.prismarine_shard,1),0,0,2,0,0,0,new aaElementValue(this.itemStone,2),null);
		registerRecipe(new ItemStack(Items.prismarine_crystals,1),0,0,0,0,4,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.gravel,1,0),0,0,0,4,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.dirt,1,0),2,2,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.iron_ore,1,0),0,8,0,0,0,8,new aaElementValue(this.itemStone,8),new aaElementValue(this.itemMetal,4));
		registerRecipe(new ItemStack(Blocks.gold_ore,1,0),0,8,0,0,8,0,new aaElementValue(this.itemStone,8),new aaElementValue(this.itemMetal,8));
		registerRecipe(new ItemStack(Blocks.diamond_ore,1,0),32,40,0,0,0,0,new aaElementValue(this.itemStone,8),new aaElementValue(this.itemGem,4));
		registerRecipe(new ItemStack(Blocks.emerald_ore,1,0),32,40,0,0,0,0,new aaElementValue(this.itemStone,8),new aaElementValue(this.itemGem,8));
		registerRecipe(new ItemStack(Blocks.redstone_ore,1,0),0,8,32,0,0,0,new aaElementValue(this.itemStone,8),new aaElementValue(this.itemReactant,16));
		registerRecipe(new ItemStack(Blocks.coal_ore,1,0),8,8,0,0,0,8,new aaElementValue(this.itemStone,8),new aaElementValue(this.itemReactant,4));
		registerRecipe(new ItemStack(Blocks.lapis_ore,1,0),0,8,0,0,64,0,new aaElementValue(this.itemStone,40),null);
		registerRecipe(new ItemStack(Blocks.glass,1,0),0,0,2,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Items.glass_bottle,1,0),0,0,0,2,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Items.potionitem,1,0),0,0,4,2,0,0,new aaElementValue(this.itemStone,1),null);
		
		registerRecipe(new ItemStack(Items.dye,1,0),0,0,0,0,0,2,new aaElementValue(this.itemFlesh,1),null);
		registerRecipe(new ItemStack(Items.rotten_flesh,1,0),0,0,0,0,0,2,new aaElementValue(this.itemFlesh,1),null);
		registerRecipe(new ItemStack(Items.spider_eye,1,0),0,0,0,0,0,4,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.fermented_spider_eye,1,0),4,0,0,0,0,4,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Items.rabbit_hide,1,0),0,2,0,0,0,0,new aaElementValue(this.itemFlesh,1),null);
		registerRecipe(new ItemStack(Items.rabbit_foot,1,0),0,0,0,8,0,0,new aaElementValue(this.itemFlesh,1),null);
		registerRecipe(new ItemStack(Items.cooked_beef,1,0),2,0,0,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Items.cooked_rabbit,1,0),2,0,0,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Items.cooked_porkchop,1,0),2,0,0,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Items.cooked_chicken,1,0),2,0,0,0,0,0,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.porkchop,1,0),0,2,0,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Blocks.sponge,1,0),0,0,0,8,0,0,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Blocks.sponge,1,1),0,0,8,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Items.rabbit,1,0),0,2,0,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Items.chicken,1,0),0,2,0,0,0,0,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.leather,1,0),0,4,0,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Items.fish,1,0),0,0,2,0,0,0,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.fish,1,1),0,0,2,0,0,0,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.fish,1,2),0,0,2,0,0,0,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.fish,1,3),0,0,2,0,0,2,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.skull,1,2),0,8,0,0,0,16,new aaElementValue(this.itemFlesh,8),null);
		registerRecipe(new ItemStack(Items.skull,1,3),0,8,0,0,16,0,new aaElementValue(this.itemFlesh,8),null);
		registerRecipe(new ItemStack(Items.skull,1,4),16,8,0,0,0,0,new aaElementValue(this.itemFlesh,8),null);
		registerRecipe(new ItemStack(Items.cooked_fish,1,0),2,0,0,0,0,0,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.cooked_fish,1,1),2,0,0,0,0,0,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.egg,1,0),0,0,0,0,8,0,new aaElementValue(this.itemFlesh,8),null);
		registerRecipe(new ItemStack(Items.beef,1,0),0,2,0,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		
		registerRecipe(new ItemStack(Items.diamond,1,0),8,8,0,0,0,0,new aaElementValue(this.itemGem,1),null);
		registerRecipe(new ItemStack(Blocks.diamond_block,1,0),72,72,0,0,0,0,new aaElementValue(this.itemGem,9),null);
		registerRecipe(new ItemStack(Blocks.emerald_block,1,0),72,72,0,0,0,0,new aaElementValue(this.itemGem,18),null);
		registerRecipe(new ItemStack(Items.nether_star,1,0),128,128,0,0,128,128,new aaElementValue(this.itemGem,64),null);
		registerRecipe(new ItemStack(Items.emerald,1,0),8,8,0,0,0,0,new aaElementValue(this.itemGem,2),null);
		
		registerRecipe(new ItemStack(Items.slime_ball,1,0),0,0,4,0,0,0,new aaElementValue(this.itemGel,1),null);
		registerRecipe(new ItemStack(Items.magma_cream,1,0),8,0,8,0,0,0,new aaElementValue(this.itemGel,8),null);
		registerRecipe(new ItemStack(Items.snowball,1,0),0,0,1,0,0,0,new aaElementValue(this.itemGel,1),null);
		registerRecipe(new ItemStack(Blocks.snow,1,0),0,0,4,0,0,0,new aaElementValue(this.itemGel,4),null);
		registerRecipe(new ItemStack(Blocks.ice,1,0),0,0,8,0,0,0,new aaElementValue(this.itemGel,4),null);
		registerRecipe(new ItemStack(Blocks.packed_ice,1,0),0,4,0,0,0,0,new aaElementValue(this.itemGel,4),null);
		registerRecipe(new ItemStack(Blocks.slime_block,1,0),0,0,36,0,0,0,new aaElementValue(this.itemGel,9),null);
		registerRecipe(new ItemStack(Items.ender_pearl,1,0),0,0,8,8,0,0,new aaElementValue(this.itemGel,8),new aaElementValue(this.itemDry,8));
		registerRecipe(new ItemStack(Items.ender_eye,1,0),8,0,0,8,0,0,new aaElementValue(this.itemReactant,8),new aaElementValue(this.itemDry,8));
		registerRecipe(new ItemStack(Items.experience_bottle,1,0),0,0,0,0,4,0,new aaElementValue(this.itemGel,2),null);
		registerRecipe(new ItemStack(Items.ghast_tear,1,0),0,0,16,16,0,0,new aaElementValue(this.itemGel,16),null);
		
		registerRecipe(new ItemStack(Items.dye,1,5),0,0,0,0,2,0,null,null);
		registerRecipe(new ItemStack(Items.dye,1,6),0,0,0,0,2,0,null,null);
		registerRecipe(new ItemStack(Items.dye,1,7),0,0,0,0,2,0,null,null);
		registerRecipe(new ItemStack(Items.dye,1,8),0,0,0,0,2,0,null,null);
		registerRecipe(new ItemStack(Items.dye,1,9),0,0,0,0,2,0,null,null);
		registerRecipe(new ItemStack(Items.dye,1,10),0,0,0,0,2,0,null,null);
		registerRecipe(new ItemStack(Items.dye,1,12),0,0,0,0,2,0,null,null);
		registerRecipe(new ItemStack(Items.dye,1,13),0,0,0,0,2,0,null,null);
		registerRecipe(new ItemStack(Items.dye,1,14),0,0,0,0,2,0,null,null);
		
		registerRecipe(new ItemStack(Blocks.brewing_stand,1,0),8,3,0,0,8,0,new aaElementValue(this.itemStone,3),new aaElementValue(this.itemDry,8));
		registerRecipe(new ItemStack(Items.bow,1,0),0,2,0,4,0,0,new aaElementValue(this.itemPlant,1),new aaElementValue(this.itemDry,1));
		registerRecipe(new ItemStack(Items.wooden_sword,1,0),0,2,0,0,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Items.stone_sword,1,0),0,2,0,0,0,0,new aaElementValue(this.itemStone,2),null);
		registerRecipe(new ItemStack(Items.iron_sword,1,0),0,0,0,0,0,4,new aaElementValue(this.itemMetal,1),null);
		registerRecipe(new ItemStack(Items.diamond_sword,1,0),16,16,0,0,0,0,new aaElementValue(this.itemGem,1),null);
		registerRecipe(new ItemStack(Items.golden_sword,1,0),0,0,0,0,8,0,new aaElementValue(this.itemMetal,2),null);
		registerRecipe(new ItemStack(Items.wooden_axe,1,0),0,4,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.wooden_pickaxe,1,0),0,4,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.wooden_hoe,1,0),0,3,0,0,0,0,new aaElementValue(this.itemPlant,3),null);
		registerRecipe(new ItemStack(Items.wooden_shovel,1,0),0,2,0,0,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Items.stone_axe,1,0),0,4,0,0,0,0,new aaElementValue(this.itemStone,3),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.stone_pickaxe,1,0),0,4,0,0,0,0,new aaElementValue(this.itemStone,3),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.stone_hoe,1,0),0,3,0,0,0,0,new aaElementValue(this.itemStone,2),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.stone_shovel,1,0),0,2,0,0,0,0,new aaElementValue(this.itemStone,1),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.iron_axe,1,0),0,1,0,0,0,6,new aaElementValue(this.itemMetal,3),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.iron_pickaxe,1,0),0,1,0,0,0,6,new aaElementValue(this.itemMetal,3),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.iron_hoe,1,0),0,1,0,0,0,4,new aaElementValue(this.itemMetal,2),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.iron_shovel,1,0),0,1,0,0,0,2,new aaElementValue(this.itemMetal,1),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.golden_axe,1,0),0,1,0,0,12,0,new aaElementValue(this.itemMetal,6),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.golden_pickaxe,1,0),0,1,0,0,12,0,new aaElementValue(this.itemMetal,6),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.golden_hoe,1,0),0,1,0,0,8,0,new aaElementValue(this.itemMetal,6),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.golden_shovel,1,0),0,1,0,0,4,0,new aaElementValue(this.itemMetal,6),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.diamond_axe,1,0),48,49,0,0,0,0,new aaElementValue(this.itemGem,3),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.diamond_pickaxe,1,0),48,49,0,0,0,0,new aaElementValue(this.itemGem,3),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.diamond_hoe,1,0),32,33,0,0,0,0,new aaElementValue(this.itemGem,2),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.diamond_shovel,1,0),16,17,0,0,0,0,new aaElementValue(this.itemGem,1),new aaElementValue(this.itemPlant,1));
		registerRecipe(new ItemStack(Items.compass,1,0),0,0,5,0,0,8,new aaElementValue(this.itemMetal,4),new aaElementValue(this.itemReactant,1));
		registerRecipe(new ItemStack(Items.clock,1,0),0,0,9,0,16,0,new aaElementValue(this.itemMetal,8),new aaElementValue(this.itemReactant,1));
		registerRecipe(new ItemStack(Items.shears,1,0),0,0,0,0,0,4,new aaElementValue(this.itemMetal,2),null);
		registerRecipe(new ItemStack(Items.fishing_rod,1,0),0,1,0,8,0,0,new aaElementValue(this.itemPlant,1),new aaElementValue(this.itemDry,2));
		registerRecipe(new ItemStack(Items.lead,1,0),0,0,4,8,0,0,new aaElementValue(this.itemGel,1),new aaElementValue(this.itemDry,3));
		registerRecipe(new ItemStack(Items.name_tag,1,0),0,0,0,12,0,0,new aaElementValue(this.itemPlant,4),new aaElementValue(this.itemDry,1));
		registerRecipe(new ItemStack(Items.saddle,1,0),0,20,0,32,0,0,new aaElementValue(this.itemFlesh,20),new aaElementValue(this.itemDry,16));
		registerRecipe(new ItemStack(Items.flint_and_steel,1,0),2,2,0,0,0,2,new aaElementValue(this.itemStone,1),new aaElementValue(this.itemMetal,1));
		registerRecipe(new ItemStack(Items.leather_helmet,1,0),0,20,0,0,0,0,new aaElementValue(this.itemFlesh,20),null);
		registerRecipe(new ItemStack(Items.leather_chestplate,1,0),0,32,0,0,0,0,new aaElementValue(this.itemFlesh,32),null);
		registerRecipe(new ItemStack(Items.leather_leggings,1,0),0,28,0,0,0,0,new aaElementValue(this.itemFlesh,28),null);
		registerRecipe(new ItemStack(Items.leather_boots,1,0),0,16,0,0,0,0,new aaElementValue(this.itemFlesh,16),null);
		registerRecipe(new ItemStack(Items.iron_helmet,1,0),0,0,0,0,0,10,new aaElementValue(this.itemMetal,5),null);
		registerRecipe(new ItemStack(Items.iron_chestplate,1,0),0,0,0,0,0,16,new aaElementValue(this.itemMetal,8),null);
		registerRecipe(new ItemStack(Items.iron_leggings,1,0),0,0,0,0,0,14,new aaElementValue(this.itemMetal,7),null);
		registerRecipe(new ItemStack(Items.iron_boots,1,0),0,0,0,0,0,8,new aaElementValue(this.itemMetal,4),null);
		registerRecipe(new ItemStack(Items.golden_helmet,1,0),0,0,0,0,20,0,new aaElementValue(this.itemMetal,10),null);
		registerRecipe(new ItemStack(Items.golden_chestplate,1,0),0,0,0,0,32,0,new aaElementValue(this.itemMetal,16),null);
		registerRecipe(new ItemStack(Items.golden_leggings,1,0),0,0,0,0,28,0,new aaElementValue(this.itemMetal,14),null);
		registerRecipe(new ItemStack(Items.golden_boots,1,0),0,0,0,0,16,0,new aaElementValue(this.itemMetal,8),null);
		registerRecipe(new ItemStack(Items.diamond_helmet,1,0),40,40,0,0,0,0,new aaElementValue(this.itemGem,5),null);
		registerRecipe(new ItemStack(Items.diamond_chestplate,1,0),64,64,0,0,0,0,new aaElementValue(this.itemGem,8),null);
		registerRecipe(new ItemStack(Items.diamond_leggings,1,0),56,56,0,0,0,0,new aaElementValue(this.itemGem,7),null);
		registerRecipe(new ItemStack(Items.diamond_boots,1,0),32,32,0,0,0,0,new aaElementValue(this.itemGem,4),null);
		registerRecipe(new ItemStack(Items.chainmail_helmet,1,0),0,0,0,10,0,0,new aaElementValue(this.itemMetal,3),null);
		registerRecipe(new ItemStack(Items.chainmail_chestplate,1,0),0,0,0,16,0,0,new aaElementValue(this.itemMetal,6),null);
		registerRecipe(new ItemStack(Items.chainmail_leggings,1,0),0,0,0,14,0,0,new aaElementValue(this.itemMetal,5),null);
		registerRecipe(new ItemStack(Items.chainmail_boots,1,0),0,0,0,8,0,0,new aaElementValue(this.itemMetal,2),null);
		registerRecipe(new ItemStack(Items.arrow,1,0),0,0,0,2,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.torch,1,0),0,0,0,0,1,0,null,null);
		registerRecipe(new ItemStack(Blocks.redstone_torch,1,0),0,0,1,0,0,0,new aaElementValue(this.itemReactant,1),null);
		registerRecipe(new ItemStack(Blocks.redstone_lamp,1,0),0,0,4,0,8,0,new aaElementValue(this.itemReactant,20),null);
		registerRecipe(new ItemStack(Items.book,1,0),0,2,0,6,0,0,new aaElementValue(this.itemPlant,3),new aaElementValue(this.itemFlesh,1));
		registerRecipe(new ItemStack(Blocks.bed,1,0),0,3,0,12,0,0,new aaElementValue(this.itemDry,12),new aaElementValue(this.itemPlant,3));
		registerRecipe(new ItemStack(Blocks.noteblock,1,0),0,8,1,0,0,0,new aaElementValue(this.itemReactant,1),new aaElementValue(this.itemPlant,8));
		registerRecipe(new ItemStack(Blocks.jukebox,1,0),8,16,0,0,0,0,new aaElementValue(this.itemGem,1),new aaElementValue(this.itemPlant,8));
		registerRecipe(new ItemStack(Blocks.tnt,1,0),20,0,4,0,0,0,new aaElementValue(this.itemReactant,40),new aaElementValue(this.itemStone,4));
		registerRecipe(new ItemStack(Items.cake,1,0),16,16,16,16,0,0,new aaElementValue(this.itemPlant,24),new aaElementValue(this.itemFlesh,8));
		
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.gold_ingot,1,0),aaElementManager.getElementFromString("fire"),new ItemStack(aaItemManager.itemMaterial,1,9)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.gold_ingot,1,0),aaElementManager.getElementFromString("earth"),new ItemStack(aaItemManager.itemMaterial,1,10)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.gold_ingot,1,0),aaElementManager.getElementFromString("water"),new ItemStack(aaItemManager.itemMaterial,1,11)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.gold_ingot,1,0),aaElementManager.getElementFromString("air"),new ItemStack(aaItemManager.itemMaterial,1,12)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.gold_ingot,1,0),aaElementManager.getElementFromString("light"),new ItemStack(aaItemManager.itemMaterial,1,13)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.gold_ingot,1,0),aaElementManager.getElementFromString("void"),new ItemStack(aaItemManager.itemMaterial,1,14)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Blocks.clay,1,0),aaElementManager.getElementFromString("water"),new ItemStack(Blocks.dirt,1)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Blocks.sand,1,0),aaElementManager.getElementFromString("air"),new ItemStack(Blocks.dirt,1)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Blocks.gravel,1,0),aaElementManager.getElementFromString("air"),new ItemStack(Blocks.cobblestone,1)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Blocks.hardened_clay,1,0),aaElementManager.getElementFromString("fire"),new ItemStack(Blocks.stone,1)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Blocks.web,1,0),aaElementManager.getElementFromString("void"),new ItemStack(Blocks.wool,1)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.coal,1,0),aaElementManager.getElementFromString("earth"),new ItemStack(Items.coal,1,1)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.coal,1,1),aaElementManager.getElementFromString("fire"),new ItemStack(Items.coal,1,0)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Blocks.glass,1,0),aaElementManager.getElementFromString("void"),new ItemStack(Blocks.quartz_block,1,0)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.redstone,1,0),aaElementManager.getElementFromString("water"),new ItemStack(Items.gunpowder,1,0)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.redstone,1,0),aaElementManager.getElementFromString("void"),new ItemStack(Items.glowstone_dust,1,0)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Blocks.obsidian,1,0),aaElementManager.getElementFromString("earth"),new ItemStack(Blocks.glowstone,1,0)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Blocks.stone,1,0),aaElementManager.getElementFromString("earth"),new ItemStack(Blocks.netherrack,1,0)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Blocks.netherrack,1,0),aaElementManager.getElementFromString("fire"),new ItemStack(Blocks.stone,1,0)));

		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.moveSlowdown).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.string,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.moveSlowdown).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.string,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.damageBoost).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.blaze_powder,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.damageBoost).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.blaze_powder,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.fireResistance).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.magma_cream,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.fireResistance).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.magma_cream,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.regeneration).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.ghast_tear,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.regeneration).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.ghast_tear,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.jump).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.rabbit_foot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.jump).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.rabbit_foot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.weakness).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.feather,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.weakness).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.feather,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.poison).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.spider_eye,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.poison).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.spider_eye,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.digSpeed).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.dye,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.digSpeed).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.dye,1,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.digSlowdown).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.bone,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.digSlowdown).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.bone,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.resistance).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.prismarine_shard,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.resistance).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.prismarine_shard,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.waterBreathing).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.fish,1,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.waterBreathing).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.fish,1,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.nightVision).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.golden_carrot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.nightVision).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.golden_carrot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.blindness).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.dye,1,0)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.blindness).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.dye,1,0)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.confusion).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Blocks.red_mushroom,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.confusion).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Blocks.red_mushroom,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.invisibility).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.glowstone_dust,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.invisibility).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.glowstone_dust,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.absorption).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.golden_apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.absorption).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.golden_apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.healthBoost).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.healthBoost).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.hunger).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.rotten_flesh,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.hunger).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.rotten_flesh,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.wither).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.coal,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.wither).toString(),900,1)).addIngredient(new ItemStack(Items.dye,1,1)).addIngredient(new ItemStack(Items.coal,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.harm).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.fermented_spider_eye,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.heal).toString(),900,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,0)).addIngredient(new ItemStack(Items.speckled_melon,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.moveSlowdown).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.string,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.moveSlowdown).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.string,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.damageBoost).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.blaze_powder,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.damageBoost).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.blaze_powder,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.fireResistance).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.magma_cream,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.fireResistance).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.magma_cream,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.regeneration).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.ghast_tear,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.regeneration).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.ghast_tear,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.jump).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.rabbit_foot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.jump).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.rabbit_foot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.weakness).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.feather,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.weakness).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.feather,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.poison).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.spider_eye,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.poison).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.spider_eye,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.digSpeed).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.dye,1,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.digSpeed).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.dye,1,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.digSlowdown).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.bone,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.digSlowdown).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.bone,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.resistance).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.prismarine_shard,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.resistance).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.prismarine_shard,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.waterBreathing).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.fish,1,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.waterBreathing).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.fish,1,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.nightVision).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.golden_carrot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.nightVision).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.golden_carrot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.blindness).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.dye,1,0)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.blindness).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.dye,1,0)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.confusion).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Blocks.red_mushroom,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.confusion).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Blocks.red_mushroom,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.invisibility).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.glowstone_dust,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.invisibility).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.glowstone_dust,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.absorption).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.golden_apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.absorption).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.golden_apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.healthBoost).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.healthBoost).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.hunger).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.rotten_flesh,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.hunger).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.rotten_flesh,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.wither).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.coal,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.wither).toString(),1800,2)).addIngredient(new ItemStack(Items.redstone,1)).addIngredient(new ItemStack(Items.coal,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.harm).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.fermented_spider_eye,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.heal).toString(),1800,2)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,3)).addIngredient(new ItemStack(Items.speckled_melon,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));

		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.moveSlowdown).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.string,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.moveSlowdown).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.string,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.damageBoost).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.blaze_powder,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.damageBoost).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.blaze_powder,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.fireResistance).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.magma_cream,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.fireResistance).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.magma_cream,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.regeneration).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.ghast_tear,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.regeneration).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.ghast_tear,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.jump).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.rabbit_foot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.jump).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.rabbit_foot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.weakness).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.feather,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.weakness).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.feather,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.poison).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.spider_eye,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.poison).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.spider_eye,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.digSpeed).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.dye,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.digSpeed).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.dye,1,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.digSlowdown).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.bone,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.digSlowdown).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.bone,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.resistance).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.prismarine_shard,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.resistance).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.prismarine_shard,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.waterBreathing).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.fish,1,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.waterBreathing).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.fish,1,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.nightVision).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.golden_carrot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.nightVision).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.golden_carrot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.blindness).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.dye,1,0)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.blindness).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.dye,1,0)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.confusion).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Blocks.red_mushroom,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.confusion).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Blocks.red_mushroom,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.invisibility).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.glowstone_dust,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.invisibility).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.glowstone_dust,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.absorption).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.golden_apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.absorption).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.golden_apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.healthBoost).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.healthBoost).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.hunger).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.rotten_flesh,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.hunger).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.rotten_flesh,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.wither).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.coal,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),0,GameData.getPotionRegistry().getNameForObject(Potion.wither).toString(),3600,3)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.coal,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.harm).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.fermented_spider_eye,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.heal).toString(),3600,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(Items.speckled_melon,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.moveSlowdown).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.string,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.damageBoost).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.blaze_powder,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.fireResistance).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.magma_cream,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.regeneration).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.ghast_tear,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.jump).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.rabbit_foot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.weakness).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.feather,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.poison).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.spider_eye,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.digSpeed).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.dye,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.digSlowdown).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.bone,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.resistance).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.prismarine_shard,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,16)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.waterBreathing).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.fish,1,3)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.nightVision).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.golden_carrot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.blindness).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.dye,1,0)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.confusion).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Blocks.red_mushroom,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.invisibility).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.glowstone_dust,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,19)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.absorption).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.golden_apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.healthBoost).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.apple,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,23)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.hunger).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.rotten_flesh,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,21)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.moveSpeed).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.sugar,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,22)));
		mixtures.add(new aaCrucibleRecipe(1, new ItemStack(aaItemManager.vial,1,0), vial.initVial(new ItemStack(aaItemManager.vial,1,1),1,GameData.getPotionRegistry().getNameForObject(Potion.wither).toString(),Integer.MAX_VALUE,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,6)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.coal,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,20)));
		mixtures.add(new aaCrucibleRecipe(2, new ItemStack(aaItemManager.itemMaterial,1,3), new ItemStack(aaItemManager.itemMaterial,1,15)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,13)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,14)));
		mixtures.add(new aaCrucibleRecipe(3, new ItemStack(aaItemManager.itemMaterial,1,6), new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,18)).addIngredient(new ItemStack(Items.ghast_tear,1)).addIngredient(new ItemStack(Items.ghast_tear,1)).addIngredient(new ItemStack(Blocks.soul_sand,1)).addIngredient(new ItemStack(Blocks.soul_sand)));
		mixtures.add(new aaCrucibleRecipe(3, new ItemStack(aaItemManager.itemMaterial,1,6), new ItemStack(aaItemManager.compoundMatter,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,8)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,5)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,2)));
		
		complexInfusions.add(new aaComplexInfusionRecipe("luminousShieldInfusion", 0, 0, 20, 20, 40, 0, 0.5f, new ItemStack(aaItemManager.itemLuminousShield,1)).addIngredient(new ItemStack(aaItemManager.compoundMatter,1)).addIngredient(new ItemStack(Items.gold_ingot,1)).addIngredient(new ItemStack(Items.gold_ingot,1)).addIngredient(new ItemStack(Items.gold_ingot,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,8)).addIngredient(new ItemStack(Items.ender_pearl,1)).addIngredient(new ItemStack(Items.ender_pearl,1)).addIngredient(new ItemStack(Items.ender_pearl,1)));	
		complexInfusions.add(new aaComplexInfusionRecipe("ichorInfusion", 128, 0, 0, 0, 128, 0, 0.5f, new ItemStack(aaItemManager.ichor,1)).addIngredient(new ItemStack(aaItemManager.compoundMatter,1)).addIngredient(new ItemStack(Blocks.gold_block,1)).addIngredient(new ItemStack(Items.skull,1,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,24)).addIngredient(new ItemStack(Items.ender_pearl,1,0)).addIngredient(new ItemStack(Items.ender_pearl,1,0)));
		complexInfusions.add(new aaComplexInfusionRecipe("mjolnirInfusion", 72, 0, 0, 96, 0, 128, 0.5f, new ItemStack(aaItemManager.mjolnir,1)).addIngredient(new ItemStack(aaItemManager.compoundMatter,1)).addIngredient(new ItemStack(Items.golden_axe,1)).addIngredient(new ItemStack(Items.golden_axe,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,12)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,12)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,14)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,14)).addIngredient(new ItemStack(aaItemManager.ichor,1)));
		complexInfusions.add(new aaComplexInfusionRecipe("crissaegrimInfusion", 0, 0, 160, 96, 0, 0, 0.5f, new ItemStack(aaItemManager.crissaegrim,1)).addIngredient(new ItemStack(aaItemManager.compoundMatter,1)).addIngredient(new ItemStack(Items.golden_sword,1)).addIngredient(new ItemStack(Blocks.redstone_block,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,12)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,12)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,11)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,11)).addIngredient(new ItemStack(aaItemManager.ichor,1)));
		complexInfusions.add(new aaComplexInfusionRecipe("invigoratingUrnInfusion", 24, 0, 60, 0, 0, 0, 0.5f, new ItemStack(aaItemManager.invigoratingUrn2,1)).addIngredient(new ItemStack(aaItemManager.compoundMatter,1)).addIngredient(new ItemStack(aaItemManager.invigoratingUrn,1)).addIngredient(new ItemStack(Items.prismarine_shard,1)).addIngredient(new ItemStack(Items.prismarine_shard,1)).addIngredient(new ItemStack(Items.prismarine_crystals,1)).addIngredient(new ItemStack(Items.prismarine_crystals,1)));
		complexInfusions.add(new aaComplexInfusionRecipe("starfireGunInfusion", 0, 0, 0, 0, 48, 48, 0.5f, new ItemStack(aaItemManager.starfireGun,1)).addIngredient(new ItemStack(aaItemManager.compoundMatter,1)).addIngredient(new ItemStack(aaItemManager.beamPistol,1)).addIngredient(new ItemStack(Blocks.glowstone,1)).addIngredient(new ItemStack(Blocks.glowstone,1)).addIngredient(new ItemStack(Blocks.redstone_block,1)).addIngredient(new ItemStack(Blocks.obsidian,1)));
		complexInfusions.add(new aaComplexInfusionRecipe("scarletStoneInfusion", 64, 0, 0, 0, 16, 0, 0.5f, new ItemStack(aaItemManager.scarletStone,1)).addIngredient(new ItemStack(aaItemManager.compoundMatter,1)).addIngredient(new ItemStack(Blocks.redstone_block,1)).addIngredient(new ItemStack(Blocks.redstone_block,1)).addIngredient(new ItemStack(Blocks.redstone_block,1)).addIngredient(new ItemStack(Blocks.glass,1)));
		complexInfusions.add(new aaComplexInfusionRecipe("laevateinnInfusion", 144, 0, 0, 0, 0, 72, 0.5f, new ItemStack(aaItemManager.laevateinn,1)).addIngredient(new ItemStack(aaItemManager.compoundMatter,1)).addIngredient(new ItemStack(Items.golden_sword,1)).addIngredient(new ItemStack(Items.blaze_rod,1)).addIngredient(new ItemStack(Items.blaze_rod,1,13)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,9)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,9)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,14)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,14)).addIngredient(new ItemStack(aaItemManager.ichor,1)));
		complexInfusions.add(new aaComplexInfusionRecipe("regenerativeMetalInfusion", 0, 20, 20, 0, 0, 0, 0.5f, new ItemStack(aaItemManager.regenerativeMetal,1)).addIngredient(new ItemStack(aaItemManager.compoundMatter,1)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,11)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,10)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,18)).addIngredient(new ItemStack(aaItemManager.itemMaterial,1,18)).addIngredient(new ItemStack(Items.nether_wart,1)).addIngredient(new ItemStack(Items.nether_wart,1)));
	}
	
	public static ItemStack matterItemFromString(String s, int count){
		if (s == "stone"){
			return new ItemStack(aaItemManager.itemMaterial,count,16);
		}
		else if (s == "metal"){
			return new ItemStack(aaItemManager.itemMaterial,count,17);
		}
		else if (s == "gem"){
			return new ItemStack(aaItemManager.itemMaterial,count,18);
		}
		else if (s == "reactant"){
			return new ItemStack(aaItemManager.itemMaterial,count,19);
		}
		else if (s == "dry"){
			return new ItemStack(aaItemManager.itemMaterial,count,20);
		}
		else if (s == "flesh"){
			return new ItemStack(aaItemManager.itemMaterial,count,21);
		}
		else if (s == "plant"){
			return new ItemStack(aaItemManager.itemMaterial,count,22);
		}
		else if (s == "gel"){
			return new ItemStack(aaItemManager.itemMaterial,count,23);
		}
		else {
			return null;
		}
	}
	
	public static ItemStack matterItemFromString(String s){
		if (s == "stone"){
			return new ItemStack(aaItemManager.itemMaterial,1,16);
		}
		else if (s == "metal"){
			return new ItemStack(aaItemManager.itemMaterial,1,17);
		}
		else if (s == "gem"){
			return new ItemStack(aaItemManager.itemMaterial,1,18);
		}
		else if (s == "reactant"){
			return new ItemStack(aaItemManager.itemMaterial,1,19);
		}
		else if (s == "dry"){
			return new ItemStack(aaItemManager.itemMaterial,1,20);
		}
		else if (s == "flesh"){
			return new ItemStack(aaItemManager.itemMaterial,1,21);
		}
		else if (s == "plant"){
			return new ItemStack(aaItemManager.itemMaterial,1,22);
		}
		else if (s == "gel"){
			return new ItemStack(aaItemManager.itemMaterial,1,23);
		}
		else {
			return null;
		}
	}

	public static void decompose(ArrayList<aaElementValue> recipe, World world, BlockPos pos) {
		for (int i = 0; i < recipe.size(); i ++){
			if (recipe.get(i).element.getIsItemClass() && !world.isRemote){
				world.spawnEntityInWorld(new EntityItem(world,pos.getX()+random.nextFloat(),pos.getY()+random.nextFloat(),pos.getZ()+random.nextFloat(), matterItemFromString(recipe.get(i).element.name,(int) recipe.get(i).value)));
			}
		}
	}

	public static aaComplexInfusionRecipe findComplexInfusionFromName(String name) {
		for (int i = 0; i < complexInfusions.size(); i ++){
			if (complexInfusions.get(i).name == name){
				return complexInfusions.get(i);
			}
		}
		return null;
	}
}
