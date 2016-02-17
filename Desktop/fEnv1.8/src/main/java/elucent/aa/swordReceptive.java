package elucent.aa;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class swordReceptive extends ItemSword implements itemElementContainer {

	public swordReceptive(){
		super(aaItemManager.materialReceptiveMetal.setRepairItem(new ItemStack(aaItemManager.itemMaterial,1,15)));
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
		if (renderPass == 0){
			if (stack.hasTagCompound()){
				if (stack.getTagCompound().hasKey("attunement")){
					int attunement = stack.getTagCompound().getInteger("attunement");
					if (attunement == 0){
						return 0xFFFFFF;
					}
					if (attunement == 1){
						return aaElementManager.elementFire.color;
					}
					if (attunement == 2){
						return aaElementManager.elementEarth.color;
					}
					if (attunement == 3){
						return aaElementManager.elementWater.color;
					}
					if (attunement == 4){
						return aaElementManager.elementAir.color;
					}
					if (attunement == 5){
						return aaElementManager.elementLight.color;
					}
					if (attunement == 6){
						return aaElementManager.elementVoid.color;
					}
				}
			}
		}
		return 0xFFFFFF;
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
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity){
		if (stack.hasTagCompound()){
			int attunement = stack.getTagCompound().getInteger("attunement");
			if (attunement == 1 && getElementValue(stack, "fire") > 0){
				setElementValue(stack,"fire",getElementValue(stack,"fire")-0.2);
				if (entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).setFire(6);
				}
			}
			if (attunement == 2 && getElementValue(stack, "earth") > 0){
				setElementValue(stack,"earth",getElementValue(stack,"earth")-0.2);
				if (entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(2,20,5));
				}
			}
			if (attunement == 3 && getElementValue(stack, "water") > 0){
				setElementValue(stack,"water",getElementValue(stack,"water")-0.2);
				if (entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(2,400,2));
					((EntityLivingBase)entity).extinguish();
				}
			}
			if (attunement == 4 && getElementValue(stack, "air") > 0){
				setElementValue(stack,"air",getElementValue(stack,"air")-0.2);
				if (entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).setFire(6);
				}
			}
			if (attunement == 5 && getElementValue(stack, "light") > 0){
				setElementValue(stack,"light",getElementValue(stack,"light")-0.2);
				if (entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(10,200,1));
				}
			}
			if (attunement == 6 && getElementValue(stack, "void") > 0){
				setElementValue(stack,"void",getElementValue(stack,"void")-0.2);
				if (entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(20,200,1));
					((EntityLivingBase)entity).extinguish();
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
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:swordReceptive","inventory"));
	}

}
