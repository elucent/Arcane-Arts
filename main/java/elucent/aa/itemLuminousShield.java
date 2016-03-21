package elucent.aa;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class itemLuminousShield extends Item implements itemVariableResult {
	Random random = new Random();
	public itemLuminousShield(){
		super();
		setUnlocalizedName("itemLuminousShield");
		setRegistryName("itemLuminousShield");
		setCreativeTab(arcaneArts.tab);
		this.setMaxStackSize(1);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged){
		return false;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean isSelected){
		if (stack.hasTagCompound() == false){
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("ticker", 0);
			stack.getTagCompound().setBoolean("activated", false);
			stack.getTagCompound().setDouble("correctness", 1.0);
			stack.getTagCompound().setDouble("depletion", 0);
		}
		if (stack.hasTagCompound()){
			stack.getTagCompound().setInteger("ticker", stack.getTagCompound().getInteger("ticker")+1);
			double colR = aaElementManager.elementLight.vColor.getX()/255.0;
			double colG = aaElementManager.elementLight.vColor.getY()/255.0;
			double colB = aaElementManager.elementLight.vColor.getZ()/255.0;
			if (stack.getTagCompound().getBoolean("activated")){
				for (double i = 0; i < 20; i ++){
					if (random.nextInt(4) == 2){
						double posX = player.posX+Math.sin((i/20.0)*2.0*Math.PI);
						double posY = player.posY+0.65;
						double posZ = player.posZ+Math.cos((i/20.0)*2.0*Math.PI);
						world.spawnParticle(EnumParticleTypes.REDSTONE, posX, posY, posZ, colR-1.0, colG, colB, 0);
					}
				}
			}
			if (stack.getTagCompound().getInteger("ticker") > 20){
				stack.getTagCompound().setInteger("ticker", 0);
				if (stack.getTagCompound().getBoolean("activated")){
					stack.getTagCompound().setDouble("depletion", stack.getTagCompound().getDouble("depletion")+(1.0/getCorrectness(stack)));
					EntityPlayer ep = (EntityPlayer)player;
					int level = 0;
					if (ep.getEquipmentInSlot(1) == null){
						level ++;
					}
					if (ep.getEquipmentInSlot(2) == null){
						level ++;
					}
					if (ep.getEquipmentInSlot(3) == null){
						level ++;
					}
					if (ep.getEquipmentInSlot(4) == null){
						level ++;
					}
					if (level > 0){
						ep.addPotionEffect(new PotionEffect(Potion.resistance.id,40,level-1));
					}
					if (stack.getTagCompound().getDouble("depletion") > 1024){
						stack.getTagCompound().setDouble("depletion", 1024);
						stack.getTagCompound().setBoolean("activated", false);
					}
				}
				else {
					if (random.nextInt(40) == 20){
						stack.getTagCompound().setDouble("depletion", Math.max(0,stack.getTagCompound().getDouble("depletion")-getCorrectness(stack)));
					}
				}
			}
		}
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
			return ((stack.getTagCompound().getDouble("depletion"))/1024.0);
		}
		else return 0;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getBoolean("activated");
		}
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			boolean isActive = stack.getTagCompound().getBoolean("activated");
			tooltip.add("Quality: " +delocalizedFunctions.getCorrectnessPhrase(getCorrectness(stack)));
			if (isActive){
				tooltip.add("Activated");
			}
			else {
				tooltip.add("Deactivated");
			}
			tooltip.add("Charge Level: " + Math.round((1024.0-stack.getTagCompound().getDouble("depletion"))*100.0)/100.0);
			tooltip.add("");
			tooltip.add("Radiates a barrier of hardened light.");
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (player.isSneaking()){
			if (stack.hasTagCompound()){
				stack.getTagCompound().setBoolean("activated", !(stack.getTagCompound().getBoolean("activated")));
			}
		}
		return stack;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:itemLuminousShield","inventory"));
	}

	@Override
	public double getCorrectness(ItemStack stack) {
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getDouble("correctness");
		}
		return 0;
	}

	@Override
	public ItemStack initFromCorrectness(double correctness) {
		ItemStack result = new ItemStack(aaItemManager.itemLuminousShield,1);
		if (correctness < 0.7){
			result.stackSize = 0;
		}
		result.setTagCompound(new NBTTagCompound());
		result.getTagCompound().setDouble("correctness", correctness);
		return result;
	}
}
