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

public class containerDrainEntity extends TileEntity implements ITickable {
	public int progress = 0;
	public ItemStack heldItem = null;
	int ticker = 0;
	public double itemRotation = 0;
	Random random = new Random();
	public boolean filled = false;
	public containerDrainEntity(){
		super();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		if (tag.hasKey("stack")){
			heldItem = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("stack"));
		}
		if (tag.hasKey("progress")){
			progress = tag.getInteger("progress");
		}
		if (tag.hasKey("filled")){
			filled = tag.getBoolean("filled");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		tag.setInteger("progress",progress);
		if (heldItem != null){
			tag.setTag("heldItem", heldItem.writeToNBT(new NBTTagCompound()));
		}
		tag.setBoolean("filled", filled);
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
	public void update() {
		itemRotation += 4.0;
		ticker ++;
		if (progress > 0){
			progress --;
			getWorld().spawnParticle(EnumParticleTypes.REDSTONE, getPos().getX()+0.25+0.5*random.nextFloat(), getPos().getY()+0.25+0.5*random.nextFloat(), getPos().getZ()+0.25+0.5*random.nextFloat(), 0, 1.0, 1.0, 0);
			if (progress == 0){
				for (int i = 0; i < 16; i ++){
					getWorld().spawnParticle(EnumParticleTypes.REDSTONE, getPos().getX()+random.nextFloat(), getPos().getY()+random.nextFloat(), getPos().getZ()+random.nextFloat(), 0, 1.0, 1.0, 0);
				}
				heldItem.stackSize = 1;
				if (getWorld().isRemote == false){
					EntityItem droppedItem = new EntityItem(getWorld(), getPos().getX()+random.nextFloat(),getPos().getY()+random.nextFloat(),getPos().getZ()+random.nextFloat(), heldItem);
					getWorld().spawnEntityInWorld(droppedItem);
				}
				heldItem = null;
			}
		}
		if (ticker % 20 == 0){
			ticker = 0;
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public double getMaxRenderDistanceSquared(){
		final int distance = 64;
		return distance*distance;
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
		if (newState.getBlock() == Blocks.air){
			return true;
		}
		else return false;
	}

	public void activate(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (player.getHeldItem() != null){
			ItemStack stack = player.getHeldItem();
			if (progress == 0){
				if (stack.getItem() instanceof itemElementContainer){
					((itemElementContainer)(stack.getItem())).setElementValue(stack, "fire", Math.max(((itemElementContainer)(stack.getItem())).getElementValue(stack,"fire")-4.0,0));
					((itemElementContainer)(stack.getItem())).setElementValue(stack, "earth", Math.max(((itemElementContainer)(stack.getItem())).getElementValue(stack,"earth")-4.0,0));
					((itemElementContainer)(stack.getItem())).setElementValue(stack, "water", Math.max(((itemElementContainer)(stack.getItem())).getElementValue(stack,"water")-4.0,0));
					((itemElementContainer)(stack.getItem())).setElementValue(stack, "air", Math.max(((itemElementContainer)(stack.getItem())).getElementValue(stack,"air")-4.0,0));
					((itemElementContainer)(stack.getItem())).setElementValue(stack, "light", Math.max(((itemElementContainer)(stack.getItem())).getElementValue(stack,"light")-4.0,0));
					((itemElementContainer)(stack.getItem())).setElementValue(stack, "void", Math.max(((itemElementContainer)(stack.getItem())).getElementValue(stack,"void")-4.0,0));
					heldItem = stack;
					stack.stackSize = 0;
					progress = 20;
				}
			}
		}
	}
}
