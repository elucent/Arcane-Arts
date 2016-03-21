package elucent.aa;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class arcaneFocusEntity extends TileEntity implements ITickable {
	public ItemStack heldItem = null;
	Random random = new Random();
	public int itemRotation = 0;
	
	public arcaneFocusEntity(){
		super();
	}
	
	public boolean onActivated(World world, BlockPos pos, EntityPlayer player){
		if (heldItem != null && player.getHeldItem() == null){
			player.setCurrentItemOrArmor(0, heldItem);
			heldItem = null;
		}
		else if (heldItem == null){
			heldItem = player.getHeldItem();
			player.setCurrentItemOrArmor(0, null);
		}
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public double getMaxRenderDistanceSquared(){
		final int distance = 64;
		return distance*distance;
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
	
	public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		if (heldItem != null){
			world.spawnEntityInWorld(new EntityItem(world, pos.getX()+random.nextFloat(),pos.getY()+random.nextFloat(),pos.getZ()+random.nextFloat(), heldItem));
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		if (tag.hasKey("heldItem")){
			heldItem = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("heldItem"));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		if (heldItem != null){
			tag.setTag("heldItem", heldItem.writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public void update(){
		itemRotation += 4;
		if (itemRotation >= 360){
			itemRotation = 0;
		}
	}
}
