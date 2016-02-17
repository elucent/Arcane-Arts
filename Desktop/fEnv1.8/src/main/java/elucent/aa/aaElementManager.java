package elucent.aa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import com.sun.javafx.geom.Vec3f;

public class aaElementManager {
	public static aaElement elementFire, elementLight, elementEarth, elementWater, elementAir, elementVoid;
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
	public static ArrayList<aaSimpleInfusionRecipe> infusions = new ArrayList<aaSimpleInfusionRecipe>();
	
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
	}
	
	public static ArrayList<aaElementValue> getStackComposition(ItemStack stack){
		ArrayList<aaElementValue> compos = new ArrayList<aaElementValue>();
		boolean doBreak = false;
		for (int i = 0; i < recipes.size() && !doBreak; i ++){
			if (recipes.get(i).item.getItem() == stack.getItem() && recipes.get(i).item.getMetadata() == stack.getMetadata()){
				compos.add(recipes.get(i).evFire);
				compos.add(recipes.get(i).evEarth);
				compos.add(recipes.get(i).evWater);
				compos.add(recipes.get(i).evAir);
				compos.add(recipes.get(i).evLight);
				compos.add(recipes.get(i).evVoid);
				if (recipes.get(i).itemClass1 != null){
					compos.add(recipes.get(i).itemClass1);
				}
				if (recipes.get(i).itemClass2 != null){
					compos.add(recipes.get(i).itemClass2);
				}
				doBreak = true;
			}
		}
		return compos;
	}
	
	public static void registerRecipe(ItemStack item, float fireValue, float earthValue, float waterValue, float airValue, float lightValue, float voidValue, aaElementValue class1, aaElementValue class2){
		aaElementRecipe recipe = new aaElementRecipe(item,fireValue,earthValue,waterValue,airValue,lightValue,voidValue);
		recipe.setClasses(class1, class2);
		recipes.add(recipe);
	}
	
	public void initRecipes(){
		registerRecipe(new ItemStack(Items.coal,1,0),0,2,0,0,0,0,new aaElementValue(this.itemReactant,1),null);
		registerRecipe(new ItemStack(Blocks.coal_block,1,0),0,18,0,0,0,0,new aaElementValue(this.itemReactant,9),null);
		registerRecipe(new ItemStack(Items.coal,1,1),2,0,0,0,0,0,new aaElementValue(this.itemReactant,1),null);
		registerRecipe(new ItemStack(Items.redstone,1,0),0,0,2,0,0,0,new aaElementValue(this.itemReactant,1),null);
		registerRecipe(new ItemStack(Blocks.redstone_block,1,0),0,0,18,0,0,0,new aaElementValue(this.itemReactant,9),null);
		registerRecipe(new ItemStack(Items.glowstone_dust,1,0),0,0,0,0,2,0,new aaElementValue(this.itemReactant,4),null);
		registerRecipe(new ItemStack(Blocks.glowstone,1,0),0,0,0,0,8,0,new aaElementValue(this.itemReactant,16),null);
		registerRecipe(new ItemStack(Items.blaze_powder,1,0),4,0,0,0,4,0,new aaElementValue(this.itemReactant,16),null);
		registerRecipe(new ItemStack(Items.gunpowder,1,0),4,0,0,0,0,0,new aaElementValue(this.itemReactant,8),null);
		registerRecipe(new ItemStack(Items.sugar,1,0),0,0,0,2,0,0,new aaElementValue(this.itemReactant,1),null);
		
		registerRecipe(new ItemStack(Items.iron_ingot,1,0),0,0,0,0,0,1,new aaElementValue(this.itemMetal,1),null);
		registerRecipe(new ItemStack(Blocks.iron_door,1,0),0,0,0,0,0,6,new aaElementValue(this.itemMetal,6),null);
		registerRecipe(new ItemStack(Blocks.iron_trapdoor,1,0),0,0,0,0,0,3,new aaElementValue(this.itemMetal,3),null);
		registerRecipe(new ItemStack(Blocks.heavy_weighted_pressure_plate,1,0),0,0,0,0,0,2,new aaElementValue(this.itemMetal,2),null);
		registerRecipe(new ItemStack(Blocks.iron_block,1,0),0,0,0,0,0,9,new aaElementValue(this.itemMetal,9),null);
		registerRecipe(new ItemStack(Blocks.light_weighted_pressure_plate,1,0),0,0,0,0,2,0,new aaElementValue(this.itemMetal,4),null);
		registerRecipe(new ItemStack(Items.gold_ingot,1,0),0,0,0,0,1,0,new aaElementValue(this.itemMetal,2),null);
		registerRecipe(new ItemStack(Blocks.gold_block,1,0),0,0,0,0,9,0,new aaElementValue(this.itemMetal,18),null);
		
		registerRecipe(new ItemStack(Items.feather,1,0),0,0,0,4,0,0,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Items.blaze_rod,1,0),8,0,0,0,8,0,new aaElementValue(this.itemDry,8),null);
		registerRecipe(new ItemStack(Items.bone,1,0),0,0,0,0,0,2,new aaElementValue(this.itemDry,2),null);
		registerRecipe(new ItemStack(Blocks.skull,1,2),0,0,0,0,0,4,new aaElementValue(this.itemDry,4),null);
		registerRecipe(new ItemStack(Blocks.skull,1,0),0,8,0,0,0,8,new aaElementValue(this.itemDry,16),null);
		registerRecipe(new ItemStack(Items.string,1,0),0,0,0,2,0,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,0),0,2,0,0,0,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,1),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,2),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,3),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,4),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,5),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,6),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,7),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,8),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,9),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,10),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,11),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,12),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,13),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,14),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		registerRecipe(new ItemStack(Blocks.wool,1,15),0,2,0,0,2,0,new aaElementValue(this.itemDry,1),null);
		
		registerRecipe(new ItemStack(Items.dye,1,1),0,0,0,0,2,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.dye,1,2),0,0,2,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.dye,1,11),0,0,0,0,2,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.potato,1,0),0,2,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.baked_potato,1,0),2,0,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.melon_seeds,1,0),0,0,0,0,1,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Items.pumpkin_seeds,1,0),0,0,0,0,1,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Items.wheat_seeds,1,0),0,0,0,0,1,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Items.carrot,1,0),0,0,2,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.melon,1,0),0,0,1,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.pumpkin,1,0),4,0,0,0,0,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Items.wheat,1,0),0,0,0,2,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Items.bread,1,0),2,0,0,0,0,0,new aaElementValue(this.itemPlant,12),null);
		registerRecipe(new ItemStack(Items.stick,1,0),0,1,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Items.dye,1,3),0,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Items.apple,1,0),0,2,0,0,0,0,new aaElementValue(this.itemPlant,8),null);
		registerRecipe(new ItemStack(Items.reeds,1,0),0,0,2,0,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.sapling,1,0),0,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.sapling,1,1),0,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.sapling,1,2),0,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.sapling,1,3),0,0,2,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.sapling,1,4),2,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.sapling,1,5),0,0,0,2,0,0,new aaElementValue(this.itemPlant,2),null);
		registerRecipe(new ItemStack(Blocks.log,1,0),0,4,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.log,1,1),0,4,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.log,1,2),0,4,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.log,1,3),0,4,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.log2,1,1),0,4,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.log2,1,1),0,4,0,0,0,0,new aaElementValue(this.itemPlant,4),null);
		registerRecipe(new ItemStack(Blocks.planks,1,0),0,1,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.planks,1,1),0,1,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.planks,1,2),0,1,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.planks,1,3),0,1,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.planks,1,4),0,1,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		registerRecipe(new ItemStack(Blocks.planks,1,5),0,1,0,0,0,0,new aaElementValue(this.itemPlant,1),null);
		
		registerRecipe(new ItemStack(Items.dye,1,4),0,0,0,0,2,0,new aaElementValue(this.itemStone,2),null);
		registerRecipe(new ItemStack(Blocks.lapis_block,1,0),0,0,0,0,18,0,new aaElementValue(this.itemStone,18),null);
		registerRecipe(new ItemStack(Items.clay_ball,1,0),0,0,1,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Items.flint,1,0),0,2,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.stone,1,0),0,1,0,0,0,0,new aaElementValue(this.itemStone,1),null);
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
		registerRecipe(new ItemStack(Items.quartz,1,0),1,0,0,0,0,0,new aaElementValue(this.itemStone,2),null);
		registerRecipe(new ItemStack(Blocks.quartz_block,1,0),4,0,0,0,0,0,new aaElementValue(this.itemStone,8),null);
		registerRecipe(new ItemStack(Blocks.quartz_block,1,1),4,0,0,0,0,0,new aaElementValue(this.itemStone,8),null);
		registerRecipe(new ItemStack(Blocks.quartz_block,1,2),4,0,0,0,0,0,new aaElementValue(this.itemStone,8),null);
		registerRecipe(new ItemStack(Blocks.sand,1,0),0,0,1,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.sand,1,1),0,0,1,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.clay,1,0),0,0,2,0,0,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.sandstone,1,0),4,4,0,0,0,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.sandstone,1,1),4,4,0,0,0,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.sandstone,1,2),4,4,0,0,0,0,new aaElementValue(this.itemStone,4),null);
		registerRecipe(new ItemStack(Blocks.gravel,1,0),0,0,0,2,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.dirt,1,0),2,2,0,0,0,0,new aaElementValue(this.itemStone,1),null);
		registerRecipe(new ItemStack(Blocks.iron_ore,1,0),0,8,0,0,0,4,new aaElementValue(this.itemStone,8),new aaElementValue(this.itemMetal,4));
		registerRecipe(new ItemStack(Blocks.gold_ore,1,0),0,8,0,0,4,0,new aaElementValue(this.itemStone,8),new aaElementValue(this.itemMetal,8));
		registerRecipe(new ItemStack(Blocks.diamond_ore,1,0),8,32,0,0,0,0,new aaElementValue(this.itemStone,8),new aaElementValue(this.itemGem,4));
		registerRecipe(new ItemStack(Blocks.emerald_ore,1,0),8,32,0,0,0,0,new aaElementValue(this.itemStone,8),new aaElementValue(this.itemGem,8));
		
		registerRecipe(new ItemStack(Items.dye,1,0),0,0,0,0,0,2,new aaElementValue(this.itemFlesh,1),null);
		registerRecipe(new ItemStack(Items.rotten_flesh,1,0),0,0,0,0,0,4,new aaElementValue(this.itemFlesh,1),null);
		registerRecipe(new ItemStack(Items.cooked_beef,1,0),2,0,0,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Items.cooked_porkchop,1,0),2,0,0,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Items.cooked_chicken,1,0),2,0,0,0,0,0,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.porkchop,1,0),0,2,0,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Items.chicken,1,0),0,2,0,0,0,0,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.leather,1,0),0,4,0,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		registerRecipe(new ItemStack(Items.fish,1,0),0,0,2,0,0,0,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.cooked_fish,1,0),2,0,0,0,0,0,new aaElementValue(this.itemFlesh,2),null);
		registerRecipe(new ItemStack(Items.beef,1,0),0,2,0,0,0,0,new aaElementValue(this.itemFlesh,4),null);
		
		registerRecipe(new ItemStack(Items.diamond,1,0),8,8,0,0,0,0,new aaElementValue(this.itemGem,1),null);
		registerRecipe(new ItemStack(Blocks.diamond_block,1,0),72,72,0,0,0,0,new aaElementValue(this.itemGem,9),null);
		registerRecipe(new ItemStack(Blocks.emerald_block,1,0),72,72,0,0,0,0,new aaElementValue(this.itemGem,18),null);
		registerRecipe(new ItemStack(Items.nether_star,1,0),128,128,0,0,128,128,new aaElementValue(this.itemGem,64),null);
		registerRecipe(new ItemStack(Items.emerald,1,0),8,8,0,0,0,0,new aaElementValue(this.itemGem,2),null);
		
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.gold_ingot,1,0),aaElementManager.getElementFromString("fire"),new ItemStack(aaItemManager.itemMaterial,1,9)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.gold_ingot,1,0),aaElementManager.getElementFromString("earth"),new ItemStack(aaItemManager.itemMaterial,1,10)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.gold_ingot,1,0),aaElementManager.getElementFromString("water"),new ItemStack(aaItemManager.itemMaterial,1,11)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.gold_ingot,1,0),aaElementManager.getElementFromString("air"),new ItemStack(aaItemManager.itemMaterial,1,12)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.gold_ingot,1,0),aaElementManager.getElementFromString("light"),new ItemStack(aaItemManager.itemMaterial,1,13)));
		infusions.add(new aaSimpleInfusionRecipe(new ItemStack(Items.gold_ingot,1,0),aaElementManager.getElementFromString("void"),new ItemStack(aaItemManager.itemMaterial,1,14)));
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
}
