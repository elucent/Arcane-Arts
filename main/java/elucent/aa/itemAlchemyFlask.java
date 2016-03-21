package elucent.aa;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class itemAlchemyFlask extends Item {
	public itemAlchemyFlask(){
		super();
		setUnlocalizedName("itemAlchemyFlask");
		setRegistryName("itemAlchemyFlask");
		setCreativeTab(arcaneArts.tab);
		this.setMaxStackSize(1);
	}
	
	@Override
	public boolean getHasSubtypes(){
		return false;
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
		if (stack.getItemDamage() == 1){
			if (renderPass == 0){
				if (stack.hasTagCompound()){
					if (stack.getTagCompound().hasKey("elementName")){
						return aaElementManager.getElementFromString(stack.getTagCompound().getString("elementName")).color;
					}
				}
			}
		}
		return 0xFFFFFF;
    }
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
		if (world.getBlockState(pos).getBlock() == aaItemManager.vesselBlock){
			((vesselEntity)(world.getTileEntity(pos))).activate(stack, world, pos, world.getBlockState(pos), player);
		}
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound() && stack.getMetadata() == 1){
			if (stack.getTagCompound().hasKey("elementName") && stack.getTagCompound().hasKey("elementValue")){
				if (aaElementManager.getElementFromString(stack.getTagCompound().getString("elementName")).getIsItemClass()){
					tooltip.add("Contains " + Double.toString(Math.round(stack.getTagCompound().getDouble("elementValue") * 100.0)/100.0)+ " units of " + aaElementManager.getElementFromString(stack.getTagCompound().getString("elementName")).getAdjective() + " matter.");
				}
				else {
					tooltip.add("Contains " + Double.toString(Math.round(stack.getTagCompound().getDouble("elementValue") * 100.0)/100.0)+ " units of " + aaElementManager.getElementFromString(stack.getTagCompound().getString("elementName")).getAdjective() + " substance.");
				}
			}
		}
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player){
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean isSelected){
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().getDouble("elementValue") <= 0){
				stack.getItem().setDamage(stack, 0);
			}
			else {
				stack.getItem().setDamage(stack, 1);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelBakery.addVariantName(this, "arcanearts:itemAlchemyFlask_0","arcanearts:itemAlchemyFlask_1");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:itemAlchemyFlask_0","inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 1, new ModelResourceLocation("arcanearts:itemAlchemyFlask_1","inventory"));
	}
}
