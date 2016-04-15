package elucent.aa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
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

public class vesselEntity extends TileEntity implements tileElementContainer{
	aaElementValue contents;
	double capacity = 640;
	int ticker = 0;
	Random random = new Random();
	public vesselEntity(){
		super();
		contents = new aaElementValue(null, 0);
	}
	
	public void setContents(aaElementValue par){
		contents = par;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		if (tag.hasKey("contentsQuantity")){
			contents.setValue(tag.getDouble("contentsQuantity"));
		}
		if (tag.hasKey("contentsName")){
			contents.setElement(aaElementManager.getElementFromString(tag.getString("contentsName")));
		}
		else {
			contents.element = null;
		}
		if (tag.hasKey("capacity")){
			capacity = (tag.getDouble("capacity"));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		tag.setDouble("contentsQuantity", contents.value);
		if (contents.element != null){
			tag.setString("contentsName", contents.element.name);
		}
		tag.setDouble("capacity", capacity);
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

	public void activate(ItemStack stack, World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		vesselEntity te = (vesselEntity)(world.getTileEntity(pos));
		if (stack.getItemDamage() == 1){
			if (stack.hasTagCompound()){
				if (stack.getTagCompound().hasKey("elementValue") && stack.getTagCompound().hasKey("elementName")){
					if (te.contents.element == null){
						te.contents.element = aaElementManager.getElementFromString(stack.getTagCompound().getString("elementName"));
					}
					if (aaElementManager.getElementFromString(te.contents.element.name) == aaElementManager.getElementFromString(stack.getTagCompound().getString("elementName"))){
						double shiftCoeff = 1.0;
						if (player.isSneaking()){
							shiftCoeff = 0.5;
						}
						te.contents.value += Math.min(128.0,shiftCoeff*stack.getTagCompound().getDouble("elementValue"));
						double remainder = 0;
						if (te.contents.value > te.capacity){
							remainder = te.contents.value-te.capacity;
							te.contents.value = te.capacity;
						}
						stack.getTagCompound().setDouble("elementValue", (stack.getTagCompound().getDouble("elementValue")-stack.getTagCompound().getDouble("elementValue")*shiftCoeff)+remainder);
						if (stack.getTagCompound().getDouble("elementValue") <= 0){
							stack.setItemDamage(0);
							stack.setTagCompound(new NBTTagCompound());
						}
					}
				}
			}
		}
		else if (stack.getItemDamage() == 0){
			if (te.contents.element != null){
				stack.setItemDamage(1);
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setString("elementName", te.contents.element.name);
				stack.getTagCompound().setDouble("elementValue", Math.min(128.0,te.contents.value));
				te.contents.value -= Math.min(128.0, te.contents.value);
				if (te.contents.value == 0){
					te.contents.element = null;
				}
			}
		}
		this.markDirty();
	}

	@Override
	public void setElementValue(String elementName, double value) {
		if (contents.element != null){
			if (elementName == contents.element.name){
				contents.value = value;
			}
		}
	}
	
	@Override
	public double getElementValue(String elementName) {
		if (contents.element != null){
			if (elementName == contents.element.name){
				return contents.value;
			}
		}
		return 0;
	}

	@Override
	public double getMaxCapacity(String elementName) {
		if (contents.element != null){
			if (elementName == contents.element.name){
				return capacity;
			}
		}
		return 0;
	}

	@Override
	public void setMaxCapacity(String elementName, double value) {
		if (contents.element != null){
			if (contents.element.name == elementName){
				capacity = value;
			}
		}
	}
}
