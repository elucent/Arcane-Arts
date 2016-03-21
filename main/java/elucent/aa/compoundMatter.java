package elucent.aa;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class compoundMatter extends Item implements itemElementContainer {

	public compoundMatter(){
		super();
		setUnlocalizedName("compoundMatter");
		setRegistryName("compoundMatter");
		setCreativeTab(arcaneArts.tab);
	}
	
	@Override
	public int getItemStackLimit(){
		return 1;
	}
	
	public void initFromRecipe(ItemStack stack, aaComplexInfusionRecipe recipe){
		if (stack.hasTagCompound()){
			stack.getTagCompound().setString("recipeName", recipe.name);
		}
	}
	
	public double getCorrectness(ItemStack stack, aaComplexInfusionRecipe recipe, World world){
		if (stack.hasTagCompound()){
			return recipe.getCorrectness(getElementValue(stack, "fire"), getElementValue(stack, "earth"), getElementValue(stack, "water"), getElementValue(stack, "air"), getElementValue(stack, "light"), getElementValue(stack, "void"), world);
		}
		return 0;
	}
	
	@Override
	public double getElementValue(ItemStack stack, String elementName) {
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().hasKey(elementName)){
				return stack.getTagCompound().getDouble(elementName);
			}
		}
		return 0;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean isSelected){
		if (stack.hasTagCompound() == false){
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setDouble("fire", 0);
			stack.getTagCompound().setDouble("earth", 0);
			stack.getTagCompound().setDouble("water", 0);
			stack.getTagCompound().setDouble("air", 0);
			stack.getTagCompound().setDouble("light", 0);
			stack.getTagCompound().setDouble("void", 0);
			stack.getTagCompound().setDouble("fireCapacity", 128);
			stack.getTagCompound().setDouble("earthCapacity", 128);
			stack.getTagCompound().setDouble("waterCapacity", 128);
			stack.getTagCompound().setDouble("airCapacity", 128);
			stack.getTagCompound().setDouble("lightCapacity", 128);
			stack.getTagCompound().setDouble("voidCapacity", 128);
		}
	}
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass){
		double totalContents = getElementValue(stack, "fire")+getElementValue(stack,"earth")+getElementValue(stack,"water")+getElementValue(stack,"air")+getElementValue(stack,"light")+getElementValue(stack,"void");
		if (totalContents > 0){
			return delocalizedFunctions.mixElements(getElementValue(stack,"fire")/totalContents,
											 getElementValue(stack,"earth")/totalContents,
											 getElementValue(stack,"water")/totalContents,
											 getElementValue(stack,"air")/totalContents,
											 getElementValue(stack,"light")/totalContents,
											 getElementValue(stack,"void")/totalContents);
		}
		else {
			return 0xFFFFFF;
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			String attuneStr = "";
			tooltip.add("Contains: ");
			tooltip.add("   " + stack.getTagCompound().getDouble("fire") + " units of elemental fire.");
			tooltip.add("   " + stack.getTagCompound().getDouble("earth") + " units of elemental earth.");
			tooltip.add("   " + stack.getTagCompound().getDouble("water") + " units of elemental water.");
			tooltip.add("   " + stack.getTagCompound().getDouble("air") + " units of elemental air.");
			tooltip.add("   " + stack.getTagCompound().getDouble("light") + " units of elemental light.");
			tooltip.add("   " + stack.getTagCompound().getDouble("void") + " units of elemental void.");
		}
	}

	@Override
	public void setElementValue(ItemStack stack, String elementName, double value) {
		if (stack.hasTagCompound()){
			stack.getTagCompound().setDouble(elementName, Math.round(value * 100.0)/100.0);
			if (stack.getTagCompound().getDouble(elementName) > getMaxCapacity(stack,elementName)){
				stack.getTagCompound().setDouble(elementName,getMaxCapacity(stack,elementName));
			}
		}
	}

	@Override
	public double getMaxCapacity(ItemStack stack, String elementName) {
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().hasKey(elementName+"Capacity")){
				return stack.getTagCompound().getDouble(elementName+"Capacity");
			}
		}
		return 0;
	}

	@Override
	public void setMaxCapacity(ItemStack stack, String elementName, double value) {
		if (stack.hasTagCompound()){
			stack.getTagCompound().setDouble(elementName+"Capacity",value);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:compoundMatter","inventory"));
	}

	@Override
	public boolean isElementValid(ItemStack stack, String elementName) {
		return true;
	}

}
