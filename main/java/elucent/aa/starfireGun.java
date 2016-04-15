package elucent.aa;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class starfireGun extends Item implements itemVariableResult {

	public starfireGun(){
		super();
		setUnlocalizedName("starfireGun");
		setRegistryName("starfireGun");
		setCreativeTab(arcaneArts.tab);
		this.setMaxStackSize(1);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack old, ItemStack newStack, boolean slot){
		return false;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack){
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().getDouble("depletion") == 0){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack){
		if (stack.hasTagCompound()){
			return ((stack.getTagCompound().getDouble("depletion"))/455.0);
		}
		else return 0;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean isSelected){
		if (!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setBoolean("inited", false);
			stack.getTagCompound().setDouble("correctness", 1.00);
			stack.getTagCompound().setDouble("depletion", 0);
			stack.getTagCompound().setDouble("cooldown", 0);
			stack.getTagCompound().setInteger("ticker", 0);
		}
		else {
			if (stack.getTagCompound().getDouble("cooldown") > 0){
				stack.getTagCompound().setDouble("cooldown",stack.getTagCompound().getDouble("cooldown")-1.0);
			}
			if (stack.getTagCompound().getDouble("depletion") >= 455){
				stack.stackSize = 0;
			}
			if (stack.getTagCompound().getBoolean("inited") == false){
				double damageModifier = 0;
				if (getCorrectness(stack) > 0.95){
					damageModifier = 1.0;
				}
				else if (getCorrectness(stack) > 0.9){
					damageModifier = 0.95;
				}
				else if (getCorrectness(stack) > 0.85){
					damageModifier = 0.9;
				}
				else if (getCorrectness(stack) > 0.8){
					damageModifier = 0.85;
				}
				else if (getCorrectness(stack) > 0.75){
					damageModifier = 0.8;
				}
				else if (getCorrectness(stack) > 0.7){
					damageModifier = 0.75;
				}
				stack.getTagCompound().setBoolean("inited",true);
			}
		}
		if (player instanceof EntityPlayer){
			if (((EntityPlayer)player).getItemInUse() != stack){
				if ((new Random()).nextInt(40) == 20){
					stack.getTagCompound().setDouble("depletion", Math.max(0,stack.getTagCompound().getDouble("depletion")-getCorrectness(stack)));
				}
			}
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		super.onItemRightClick(stack, world, player);
		if (!player.isSneaking()){
			if (stack.hasTagCompound()){
				if (stack.getTagCompound().getDouble("cooldown") <= 0){
					stack.getTagCompound().setDouble("cooldown", 10.0/this.getCorrectness(stack));
					world.spawnEntityInWorld(new aaElementProjectile(world).init(player.posX+player.width/2.0+player.getLookVec().xCoord*2.0,player.posY+1.4+player.getLookVec().yCoord*2.0,player.posZ+player.width/2.0+player.getLookVec().zCoord*2.0,player.getLookVec().xCoord*3.0,player.getLookVec().yCoord*3.0,player.getLookVec().zCoord*3.0,aaElementManager.elementRaw,0.5,6.0*getCorrectness(stack)*getCorrectness(stack),3,player));
					stack.getTagCompound().setDouble("depletion",stack.getTagCompound().getDouble("depletion")+1.0/getCorrectness(stack));
				}
			}
		}
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			tooltip.add("Quality: " + delocalizedFunctions.getCorrectnessPhrase(getCorrectness(stack)));
			tooltip.add("Charge Level: " + Math.round((455.0-stack.getTagCompound().getDouble("depletion"))*100.0)/100.0);
			tooltip.add("");
			tooltip.add("It bears the mark of the north star.");
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:starfireGun","inventory"));
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
		ItemStack result = new ItemStack(aaItemManager.starfireGun,1);
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
