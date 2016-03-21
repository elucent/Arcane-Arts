package elucent.aa;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class beamPistol extends Item implements itemElementContainer {
	Random random = new Random();
	public beamPistol(){
		super();
		setUnlocalizedName("beamPistol");
		setRegistryName("beamPistol");
		setCreativeTab(arcaneArts.tab);
		this.setMaxStackSize(1);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack old, ItemStack newStack, boolean slot){
		return false;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean isSelected){
		if (!stack.hasTagCompound()){
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
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		super.onItemRightClick(stack, world, player);
		if (!player.isSneaking()){
			if (stack.hasTagCompound()){
				if (getElement(stack) != null){
					if (getElementValue(stack,getElement(stack).name) >= 0.1){
						world.spawnEntityInWorld(new aaElementRay(world).init(player.posX,player.posY+1.4,player.posZ,player.getLookVec().xCoord,player.getLookVec().yCoord,player.getLookVec().zCoord,this.getElement(stack),6.0));
						setElementValue(stack,getElement(stack).name,getElementValue(stack,getElement(stack).name)-0.1);
					}
				}
			}
		}
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			if (getElement(stack) != null){
				tooltip.add("Contains: ");
				tooltip.add("   " + stack.getTagCompound().getDouble(getElement(stack).name) + " units of elemental " + getElement(stack).name + ".");
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:beamPistol","inventory"));
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
	
	public double getTotalElementValue(ItemStack stack){
		return getElementValue(stack,"fire")+getElementValue(stack,"earth")+getElementValue(stack,"water")+getElementValue(stack,"air")+getElementValue(stack,"light")+getElementValue(stack,"void");
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
