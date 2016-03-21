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

public class swordReceptive extends ItemSword implements itemElementContainer {

	public swordReceptive(){
		super(aaItemManager.materialReceptiveMetal);
		setUnlocalizedName("swordReceptive");
		setRegistryName("swordReceptive");
		setCreativeTab(arcaneArts.tab);
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
			stack.getTagCompound().setInteger("attunement", 0);
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
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().getInteger("attunement") == 2 && isSelected){
				if (stack.getItemDamage() > 0 && getElementValue(stack, "earth") > 0){
					setElementValue(stack,"earth",getElementValue(stack,"earth")-0.05);
					stack.setItemDamage(stack.getItemDamage()-1);
				}
			}
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		super.onItemRightClick(stack, world, player);
		if (player.isSneaking()){
			if (stack.hasTagCompound()){
				if (stack.getTagCompound().hasKey("attunement")){
					stack.getTagCompound().setInteger("attunement", stack.getTagCompound().getInteger("attunement")+1);
					if (stack.getTagCompound().getInteger("attunement") == 7){
						stack.getTagCompound().setInteger("attunement", 0);
					}
				}
			}
		}
		return stack;
	}
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass){
		return delocalizedFunctions.receptiveToolColor(stack, renderPass);
    }
	
	public aaElement getElement(ItemStack stack){
		if (getElementValue(stack, "fire") > 0){
			return aaElementManager.elementFire;
		}
		if (getElementValue(stack, "earth") > 0){
			return aaElementManager.elementEarth;
		}
		if (getElementValue(stack, "water") > 0){
			return aaElementManager.elementWater;
		}
		if (getElementValue(stack, "air") > 0){
			return aaElementManager.elementAir;
		}
		if (getElementValue(stack, "light") > 0){
			return aaElementManager.elementLight;
		}
		if (getElementValue(stack, "void") > 0){
			return aaElementManager.elementVoid;
		}
		return null;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			String attuneStr = "";
			int attunement = stack.getTagCompound().getInteger("attunement");
			if (attunement == 0) attuneStr = "none";
			if (attunement == 1) attuneStr = "fire";
			if (attunement == 2) attuneStr = "earth";
			if (attunement == 3) attuneStr = "water";
			if (attunement == 4) attuneStr = "air";
			if (attunement == 5) attuneStr = "light";
			if (attunement == 6) attuneStr = "void";
			tooltip.add("Attuned to: " + attuneStr + ".");
			if (getElement(stack) != null){
				tooltip.add("Contains: ");
				tooltip.add("   " + stack.getTagCompound().getDouble(getElement(stack).name) + " units of elemental " + getElement(stack).name + ".");
			}
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity){
		if (stack.hasTagCompound()){
			int attunement = stack.getTagCompound().getInteger("attunement");
			if (attunement == 1 && getElementValue(stack, "fire") > 0){
				setElementValue(stack,"fire",getElementValue(stack,"fire")-0.2);
				if (entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).attackEntityFrom(DamageSource.inFire, getDamageVsEntity());
				}
			}
			if (attunement == 2 && getElementValue(stack, "earth") > 0){
				setElementValue(stack,"earth",getElementValue(stack,"earth")-0.2);
				if (entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).attackEntityFrom(DamageSource.generic, getDamageVsEntity());
				}
			}
			if (attunement == 3 && getElementValue(stack, "water") > 0){
				setElementValue(stack,"water",getElementValue(stack,"water")-0.2);
				if (entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).attackEntityFrom(DamageSource.drown, getDamageVsEntity());
				}
			}
			if (attunement == 4 && getElementValue(stack, "air") > 0){
				setElementValue(stack,"air",getElementValue(stack,"air")-0.2);
				if (entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).attackEntityFrom(DamageSource.fall, getDamageVsEntity());
				}
			}
			if (attunement == 5 && getElementValue(stack, "light") > 0){
				setElementValue(stack,"light",getElementValue(stack,"light")-0.2);
				if (entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).attackEntityFrom(DamageSource.lightningBolt, getDamageVsEntity());
				}
			}
			if (attunement == 6 && getElementValue(stack, "void") > 0){
				setElementValue(stack,"void",getElementValue(stack,"void")-0.2);
				if (entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).attackEntityFrom(DamageSource.wither, getDamageVsEntity());
				}
			}
		}
		return super.onLeftClickEntity(stack, player, entity);
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
	
	public double getTotalElementValue(ItemStack stack){
		return getElementValue(stack,"fire")+getElementValue(stack,"earth")+getElementValue(stack,"water")+getElementValue(stack,"air")+getElementValue(stack,"light")+getElementValue(stack,"void");
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:swordReceptive","inventory"));
	}

	@Override
	public boolean isElementValid(ItemStack stack, String name){
		if (getElementValue(stack,name) > 0){
			return true;
		}
		else if (this.getTotalElementValue(stack) == 0){
			return true;
		}
		else {
			return false;
		}
	}
}
