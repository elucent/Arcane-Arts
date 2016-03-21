package elucent.aa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class crucibleEntity extends TileEntity implements ITickable {
	ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	ArrayList<ItemStack> renderItems = new ArrayList<ItemStack>();
	int ticker = 0;
	Random random = new Random();
	float itemRotation = 0;
	public crucibleEntity(){
		super();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		items = new ArrayList<ItemStack>();
		renderItems = new ArrayList<ItemStack>();
		if (tag.hasKey("inventory")){
			NBTTagList list = tag.getTagList("inventory", Constants.NBT.TAG_COMPOUND);
			for (int i = 0; i < list.tagCount(); i ++){
				items.add(ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i)));
				renderItems.add(ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i)));
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		if (items.size() > 0){
			NBTTagList list = new NBTTagList();
			for (int i = 0; i < items.size(); i ++){;
				list.appendTag(items.get(i).writeToNBT(new NBTTagCompound()));
			}
			tag.setTag("inventory",list);
		}
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
		for (int i = 0; i < items.size(); i ++){
			world.spawnEntityInWorld(new EntityItem(world,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,items.get(i)));
		}
		invalidate();
	}

	@Override
	public void update() {
		ticker ++;
		itemRotation += 4.0;
		if (items.size() == 0){
			renderItems.clear();
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
		if (newState.getBlock() == Blocks.air){
			return true;
		}
		else return false;
	}

	public void activate(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (player.getHeldItem() == null){
			if (items.size() > 0){	
				if (world.isRemote == false){
					world.spawnEntityInWorld(new EntityItem(world, pos.getX()+0.5,pos.getY()+1,pos.getZ()+0.5, items.get(items.size()-1)));
					items.remove(items.size()-1);
				}
			}
			if (renderItems.size() > 0){
				renderItems.remove(renderItems.size()-1);
			}
		}
		else {
			boolean doCraft = false;
			for (int i = 0; i < aaElementManager.mixtures.size(); i ++){
				if (aaElementManager.mixtures.get(i).doesMatch(items)){
					doCraft = aaElementManager.mixtures.get(i).willWork(world, pos, player.getHeldItem());
					if (doCraft){
						aaElementManager.mixtures.get(i).doResult(world, pos, player.getHeldItem(), this);
						items = new ArrayList<ItemStack>();
						items.clear();
						renderItems = new ArrayList<ItemStack>();
						renderItems.clear();
						player.getHeldItem().stackSize --;
					}
				}
			}
			if (!doCraft){
				if (items.size() < 10){
					ItemStack newItem = new ItemStack(player.getHeldItem().getItem(),1,player.getHeldItem().getItemDamage());
					newItem.setTagCompound(player.getHeldItem().getTagCompound());
					items.add(newItem);
					renderItems.add(newItem);
					player.getHeldItem().stackSize --;
				}
			}
		}
	}
}
