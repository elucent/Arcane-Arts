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

public class arcaneForgeEntity extends TileEntity implements ITickable {
	public ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	public ArrayList<ItemStack> renderItems = new ArrayList<ItemStack>();
	public aaComplexInfusionRecipe recipe = null;
	public int progress = 0;
	int ticker = 0;
	Random random = new Random();
	float itemRotation = 0;
	public arcaneForgeEntity(){
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
		if (tag.hasKey("recipeName")){
			recipe = aaElementManager.findComplexInfusionFromName(tag.getString("recipeName"));
		}
		if (tag.hasKey("progress")){
			progress = tag.getInteger("progress");
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
		if (recipe != null){
			tag.setString("recipeName", recipe.name);
		}
		tag.setInteger("progress",progress);
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
		boolean updateRecipe = false;
		if (player.getHeldItem() == null){
			updateRecipe = true;
			if (items.size() > 0){
				if (world.isRemote == false){
					world.spawnEntityInWorld(new EntityItem(world, pos.getX()+0.5,pos.getY()+1,pos.getZ()+0.5, items.get(items.size()-1)));
				}	
				items.remove(items.size()-1);
			}
			if (renderItems.size() > 0){
				renderItems.remove(renderItems.size()-1);
			}
		}
		else if (player.getHeldItem() != null){
			if (player.getHeldItem().getItem() == aaItemManager.forgeHammer){
				if (recipe != null){
					progress --;
					for (int i = 0; i < 4; i ++){
						getWorld().spawnParticle(EnumParticleTypes.CRIT, getPos().getX()+random.nextFloat()*0.5+0.25, getPos().getY()+1.0, getPos().getZ()+random.nextFloat()*0.5+0.25, random.nextFloat()*0.1, random.nextFloat()*0.1, random.nextFloat()*0.1, 0);
					}
					if (progress == 0){
						double correctness = 0.0;
						for (int i = 0; i < items.size(); i ++){
							if (items.get(i).getItem() == aaItemManager.compoundMatter){
								compoundMatter comp = (compoundMatter)items.get(i).getItem();
								correctness = recipe.getCorrectness(comp.getElementValue(items.get(i), "fire"),
													  				comp.getElementValue(items.get(i), "earth"),
													  				comp.getElementValue(items.get(i), "water"),
													  				comp.getElementValue(items.get(i), "air"),
													  				comp.getElementValue(items.get(i), "light"),
													  				comp.getElementValue(items.get(i), "void"),
													  				getWorld());
							}
						}
						if (getWorld().isRemote == false){
							world.spawnEntityInWorld(new EntityItem(world, pos.getX()+0.5,pos.getY()+1,pos.getZ()+0.5, ((itemVariableResult)recipe.output.getItem()).initFromCorrectness(correctness)));
						}
						world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, getPos().getX()+0.5, getPos().getY()+0.5, getPos().getZ()+0.5, 0, 0, 0, 0);
						items.clear();
						updateRecipe = true;
						renderItems.clear();
					}
				}
			}
			else {
				updateRecipe = true;
				player.getHeldItem().stackSize --;
				boolean doCraft = false;
				/*
				items = new ArrayList<ItemStack>();
				renderItems = new ArrayList<ItemStack>();*/
				if (!doCraft){
					if (items.size() < 14){
						ItemStack newItem = new ItemStack(player.getHeldItem().getItem(),1,player.getHeldItem().getItemDamage());
						newItem.setTagCompound(player.getHeldItem().getTagCompound());
						items.add(newItem);
						renderItems.add(newItem);
					}
				}
			}
		}
		if (updateRecipe){
			recipe = null;
			for (int i = 0; i < aaElementManager.complexInfusions.size(); i ++){
				if (aaElementManager.complexInfusions.get(i).doesMatch(items, world)){
					recipe = aaElementManager.complexInfusions.get(i);
					progress = (int)Math.floor((recipe.fireReq + recipe.earthReq + recipe.waterReq + recipe.airReq + recipe.lightReq + recipe.voidReq)*0.15);
				}
			}
		}
	}
}
