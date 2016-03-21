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

public class laevateinn extends ItemSword implements itemVariableResult {
	Random random = new Random();
	public laevateinn(){
		super(aaItemManager.materialLaevateinn);
		setUnlocalizedName("laevateinn");
		setRegistryName("laevateinn");
		setCreativeTab(arcaneArts.tab);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity){
		entity.setFire(6);
		return super.onLeftClickEntity(stack, player, entity);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack){
		return EnumAction.BOW;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack old, ItemStack newStack, boolean slot){
		return false;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft){
		if (72000-timeLeft > 5){
			stack.damageItem(6, player);
			world.spawnEntityInWorld(new aaElementProjectile(world).init(player.posX+2.0*player.getLookVec().xCoord,player.posY+2.0*player.getLookVec().yCoord+player.getEyeHeight(),player.posZ+2.0*player.getLookVec().zCoord,player.getLookVec().xCoord*2.0,player.getLookVec().yCoord*2.0,player.getLookVec().zCoord*2.0,aaElementManager.elementFire,1.0,((72000-timeLeft)/20.0)*this.getAttackDamage(stack)*1.5,5,player));
		}
	}
	
	public double getAttackDamage(ItemStack stack){
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
		return 1.0+12.0*damageModifier;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean isSelected){
		if (!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setBoolean("inited", false);
			stack.getTagCompound().setDouble("correctness", 1.00);
			stack.getTagCompound().setInteger("ticker", 0);
		}
		else {
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
				NBTTagCompound damageModTag = new NBTTagCompound();
				stack.getTagCompound().setTag("AttributeModifiers", new NBTTagList());
				damageModTag.setString("AttributeName","generic.attackDamage");
				damageModTag.setString("Name","Damage");
				damageModTag.setString("Slot","mainhand");
				damageModTag.setInteger("Operation",0);
				damageModTag.setDouble("Amount",12.0*damageModifier);
				damageModTag.setLong("UUIDMost",1);
				damageModTag.setLong("UUIDLeast",2000000);
				stack.getTagCompound().getTagList("AttributeModifiers", Constants.NBT.TAG_COMPOUND).appendTag(damageModTag);
				stack.getTagCompound().setBoolean("inited",true);
			}
		}
		if (player instanceof EntityPlayer){
			if (((EntityPlayer)player).getItemInUse() != stack){
				if ((new Random()).nextInt(40) == 20){
					stack.setItemDamage(Math.max(0, stack.getItemDamage()-1));
				}
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			tooltip.add("Quality: " + delocalizedFunctions.getCorrectnessPhrase(getCorrectness(stack)));
			tooltip.add("");
			tooltip.add("Blazing sword of the flaming king.");
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:laevateinn","inventory"));
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
		ItemStack result = new ItemStack(aaItemManager.laevateinn,1);
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
