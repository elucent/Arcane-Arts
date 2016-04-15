package elucent.aa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class elementActivatorEntity extends TileEntity implements ITickable {
	public int ticker = 0;
	public elementActivatorEntity(){
		super();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet){
		readFromNBT(packet.getNbtCompound());
	}
	
	@Override
	public Packet getDescriptionPacket(){
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		int meta = getBlockMetadata();
		return new S35PacketUpdateTileEntity(pos,meta,nbt);
	}

	public void breakBlock(World world, BlockPos pos, IBlockState state,EntityPlayer player) {
		invalidate();
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
		if (newState.getBlock() == Blocks.air){
			return true;
		}
		else return false;
	}

	@Override
	public void update() {
		ticker ++;
		if (ticker % 20 == 0){
			ticker = 0;
			if (getWorld().getBlockState(getPos().down()).getBlock() == aaItemManager.vesselBlock && getWorld().getBlockState(getPos().up()).getBlock() == aaItemManager.energyCellBlock){
				vesselEntity ve = (vesselEntity)getWorld().getTileEntity(getPos().down());
				energyCellEntity ece = (energyCellEntity)getWorld().getTileEntity(getPos().up());
				if (ve.contents.element != null){
					String elementName = ve.contents.element.name;
					if (ve.contents.value >= 1.0 && ece.getElementValue(elementName) < ece.getMaxCapacity(elementName)-16.0){
						ve.contents.value -= 1.0;
						ece.setElementValue(elementName, ece.getElementValue(elementName)+16.0);
						ve.markDirty();
						ece.markDirty();
					}
				}
			}
		}
	}
}
