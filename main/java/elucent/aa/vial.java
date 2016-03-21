package elucent.aa;

import java.util.Collection;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class vial extends Item {
	public vial(){
		super();
		setUnlocalizedName("vial");
		setRegistryName("vial");
		setCreativeTab(arcaneArts.tab);
		this.setMaxStackSize(1);
	}
	
	@Override
	public boolean getHasSubtypes(){
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems){
		ItemStack stack = new ItemStack(item,1,0);
		//stack.setTagCompound(new NBTTagCompound());
		//stack.getTagCompound().setString("elementName", "null");
		//stack.getTagCompound().setDouble("elementValue", 0);
		subItems.add(stack);
	}
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass){
		if (stack.hasTagCompound()){
			if (renderPass == 0){
				if (stack.getTagCompound().getBoolean("filled")){
					if (stack.getTagCompound().hasKey("effect")){
						return GameData.getPotionRegistry().getObject(new ResourceLocation(stack.getTagCompound().getString("effect"))).getLiquidColor();
					}
				}
			}
		}
		return 0xFFFFFF;
    }
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player){
		ItemStack result = stack.copy();
		if (stack.getTagCompound().getBoolean("filled")){
			if (stack.getTagCompound().getInteger("function") == 0){
				Potion potion = Potion.getPotionFromResourceLocation(stack.getTagCompound().getString("effect"));
				if (player.getActivePotionEffect(potion) != null){
					if (player.getActivePotionEffect(potion).getAmplifier() <= stack.getTagCompound().getInteger("level")){
						player.removePotionEffect(potion.id);
					}
				}
			}
			if (stack.getTagCompound().getInteger("function") == 1){
				Potion potion = GameData.getPotionRegistry().getObject(new ResourceLocation(stack.getTagCompound().getString("effect")));
				PotionEffect effect = new PotionEffect(potion.id,stack.getTagCompound().getInteger("duration"),stack.getTagCompound().getInteger("level")-1);
				if (potion.isInstant()){
					effect = new PotionEffect(potion.id,1,stack.getTagCompound().getInteger("level")-1);
					effect.performEffect(player);
				}
				else {
					player.addPotionEffect(effect);
				}
			}
			if (result.stackSize > 1){
				result.stackSize --;
				ItemStack spawnStack = result.copy();
				spawnStack.setTagCompound(null);
				spawnStack.stackSize = 1;
				world.spawnEntityInWorld(new EntityItem(world,player.posX,player.posY,player.posZ,spawnStack));
			}
			else {
				result.setTagCompound(null);
			}
		}
		return result;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack){
		if (stack.getMetadata() == 1){
			return EnumAction.DRINK;
		}
		else {
			return EnumAction.NONE;
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack stack){
        return 32;
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().getBoolean("filled")){
				if (stack.getTagCompound().getInteger("function") == 0){
					tooltip.add("Cures " + delocalizedFunctions.potionName(stack.getTagCompound().getString("effect")) + " " + delocalizedFunctions.getRomanNumeralsFor(stack.getTagCompound().getInteger("level")));
				}
				if (stack.getTagCompound().getInteger("function") == 1){
					Potion potion = GameData.getPotionRegistry().getObject(new ResourceLocation(stack.getTagCompound().getString("effect")));
					if (potion.isInstant()){
						tooltip.add("Applies " + delocalizedFunctions.potionName(stack.getTagCompound().getString("effect")) + " " + delocalizedFunctions.getRomanNumeralsFor(stack.getTagCompound().getInteger("level")));
					}
					else {
						if (stack.getTagCompound().getInteger("duration") == Integer.MAX_VALUE){
							tooltip.add("Grants " + delocalizedFunctions.potionName(stack.getTagCompound().getString("effect")) + " " + delocalizedFunctions.getRomanNumeralsFor(stack.getTagCompound().getInteger("level")) + " indefinitely.");
						}
						else {	
							tooltip.add("Grants " + delocalizedFunctions.potionName(stack.getTagCompound().getString("effect")) + " " + delocalizedFunctions.getRomanNumeralsFor(stack.getTagCompound().getInteger("level")) + " for " + stack.getTagCompound().getInteger("duration")/20 + " seconds.");
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean hasEffect(ItemStack stack){
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().getBoolean("filled")){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player){
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean isSelected){
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().getBoolean("filled")){
				stack.setItemDamage(1);
			}
			else {
				stack.setItemDamage(0);
			}
		}
		else {
			stack.setItemDamage(0);
		}
	}
	
	public static ItemStack initVial(ItemStack stack, int type, String effect, int duration, int level){
		ItemStack result = stack.copy();
		result.setTagCompound(new NBTTagCompound());
		result.getTagCompound().setBoolean("filled", true);
		result.getTagCompound().setInteger("function", type);
		result.getTagCompound().setString("effect", effect);
		result.getTagCompound().setInteger("level", level);
		result.getTagCompound().setInteger("duration", duration);
		return result;
	}
	
	@Override
	public int getItemStackLimit(){
		return 16;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelBakery.addVariantName(this, "arcanearts:vial_0","arcanearts:vial_1");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:vial_0","inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 1, new ModelResourceLocation("arcanearts:vial_1","inventory"));
	}
}
