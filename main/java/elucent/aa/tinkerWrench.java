package elucent.aa;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class tinkerWrench extends Item {
	public tinkerWrench(){
		super();
		setUnlocalizedName("tinkerWrench");
		setRegistryName("tinkerWrench");
		setCreativeTab(arcaneArts.tab);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
		if (player.isSneaking()){
			TileEntity te = world.getTileEntity(pos);
			if (stack.hasTagCompound()){
				if (stack.getTagCompound().hasKey("storedX")){
					TileEntity ste = world.getTileEntity(new BlockPos(stack.getTagCompound().getInteger("storedX"),stack.getTagCompound().getInteger("storedY"),stack.getTagCompound().getInteger("storedZ")));
					if (ste != null){
						if (ste instanceof tileEnergyContainer){
							if (((tileEnergyContainer)ste).isTargetable()){
								((tileEnergyContainer)ste).setTarget(pos);
								stack.getTagCompound().removeTag("storedX");
								stack.getTagCompound().removeTag("storedY");
								stack.getTagCompound().removeTag("storedZ");
								return true;
							}
						}
					}
				}
				else {
					if (te != null){
						if (te instanceof tileEnergyContainer){
							tileEnergyContainer tec = (tileEnergyContainer)te;
							if (tec.isTargetable()){
								if (!stack.hasTagCompound()){
									stack.setTagCompound(new NBTTagCompound());
								}
								stack.getTagCompound().setInteger("storedX", pos.getX());
								stack.getTagCompound().setInteger("storedY", pos.getY());
								stack.getTagCompound().setInteger("storedZ", pos.getZ());
								return true;
							}
						}
					}
				}
			}
			else {
				if (te != null){
					if (te instanceof tileEnergyContainer){
						tileEnergyContainer tec = (tileEnergyContainer)te;
						if (tec.isTargetable()){
							if (!stack.hasTagCompound()){
								stack.setTagCompound(new NBTTagCompound());
							}
							stack.getTagCompound().setInteger("storedX", pos.getX());
							stack.getTagCompound().setInteger("storedY", pos.getY());
							stack.getTagCompound().setInteger("storedZ", pos.getZ());
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().hasKey("storedX")){
				tooltip.add("Connecting tile entity at");
				tooltip.add("  X:"+stack.getTagCompound().getInteger("storedX"));
				tooltip.add("  Y:"+stack.getTagCompound().getInteger("storedY"));
				tooltip.add("  Z:"+stack.getTagCompound().getInteger("storedZ"));
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:tinkerWrench","inventory"));
	}
}
