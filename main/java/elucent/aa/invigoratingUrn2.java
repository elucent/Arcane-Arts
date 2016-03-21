package elucent.aa;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.vecmath.Vector3d;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class invigoratingUrn2 extends Item implements itemElementContainer, itemVariableResult {
	Random random = new Random();
	public invigoratingUrn2(){
		super();
		setUnlocalizedName("invigoratingUrn2");
		setRegistryName("invigoratingUrn2");
		setCreativeTab(arcaneArts.tab);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack){
		if (stack.hasTagCompound()){
			if (getElementValue(stack,"water") >= 2.0){
				return EnumAction.DRINK;
			}
			else return EnumAction.NONE;
		}
		else return EnumAction.NONE;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack){
		return (int)(32*(1.0/getCorrectness(stack)));
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack old, ItemStack newStack, boolean slot){
		return false;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player){
		if (stack.hasTagCompound()){
			if (getElementValue(stack,"water") >= 2.0/getCorrectness(stack)){
				setElementValue(stack,"water",getElementValue(stack,"water")-2.0/getCorrectness(stack));
				player.getFoodStats().setFoodSaturationLevel(Math.min(20, player.getFoodStats().getSaturationLevel()+2));
				player.getFoodStats().setFoodLevel(Math.min(20, player.getFoodStats().getFoodLevel()+2));
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id,(int)(300*getCorrectness(stack)),0));
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id,(int)(300*getCorrectness(stack)),0));
				player.addPotionEffect(new PotionEffect(Potion.jump.id,(int)(300*getCorrectness(stack)),0));
			}
		}
		return stack;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean isSelected){
		if (!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setBoolean("inited", false);
			stack.getTagCompound().setDouble("correctness", 1.00);
			stack.getTagCompound().setDouble("fire", 0);
			stack.getTagCompound().setDouble("earth", 0);
			stack.getTagCompound().setDouble("water", 0);
			stack.getTagCompound().setDouble("air", 0);
			stack.getTagCompound().setDouble("light", 0);
			stack.getTagCompound().setDouble("void", 0);
			stack.getTagCompound().setDouble("fireCapacity", 0);
			stack.getTagCompound().setDouble("earthCapacity", 0);
			stack.getTagCompound().setDouble("waterCapacity", 64);
			stack.getTagCompound().setDouble("airCapacity", 0);
			stack.getTagCompound().setDouble("lightCapacity", 0);
			stack.getTagCompound().setDouble("voidCapacity", 0);
		}
		else {
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			tooltip.add("Quality: " + delocalizedFunctions.getCorrectnessPhrase(getCorrectness(stack)));
			tooltip.add("Contains: ");
			tooltip.add("   " + stack.getTagCompound().getDouble("water") + " units of elemental water.");
			tooltip.add("");
			tooltip.add("Press \"Q\" to quaff.");
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:invigoratingUrn2","inventory"));
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

	@Override
	public boolean isElementValid(ItemStack stack, String name){
		if (name == "water"){
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public double getCorrectness(ItemStack stack) {
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getDouble("correctness");
		}
		else return 0;
	}

	@Override
	public ItemStack initFromCorrectness(double correctness) {
		ItemStack result = new ItemStack(aaItemManager.invigoratingUrn2,1);
		if (correctness < 0.7){
			result.stackSize = 0;
		}
		result.setTagCompound(new NBTTagCompound());
		result.getTagCompound().setBoolean("inited", false);
		result.getTagCompound().setDouble("correctness", correctness);
		result.getTagCompound().setInteger("ticker", 0);
		return result;
	}
}
