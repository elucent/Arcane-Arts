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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
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
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class simpleFormatorEntity extends TileEntity implements ITickable{
	int ticker = 0;
	ItemStack target = null;
	double fireValue, earthValue, waterValue, airValue, lightValue, voidValue = 0;
	boolean valid = false;
	boolean spawnParticles = false;
	aaElementRecipe recipe;
	public ArrayList<IInventory> particleInventories = null;
	public ArrayList<vesselEntity> particleVessels = null;
	public simpleFormatorEntity(){
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
		if (spawnParticles){
			spawnParticles = false;
			Random random = new Random();
			for (int i = 0; i < 16; i ++){
				getWorld().spawnParticle(EnumParticleTypes.SPELL_INSTANT, getPos().getX()+0.25+random.nextFloat()*0.5, getPos().getY()+1.25+random.nextFloat()*0.5, getPos().getZ()+0.25+random.nextFloat()*0.5, 0, random.nextFloat()*0.1+0.1, 0, 0);
			}
			for (int i = 0; i < particleInventories.size(); i ++){
				BlockPos bp = ((TileEntity)particleInventories.get(i)).getPos();
				Vec3 pos = new Vec3(bp.getX()+0.5,bp.getY()+1.0,bp.getZ()+0.5);
				for (int j = 0; j < 10; j ++){
					double dx, dy, dz = 0;
					double xDir = random.nextFloat()*2.0*Math.PI;
					double yDir = random.nextFloat()*2.0*Math.PI;
					dx = (getPos().getX()+0.5)-pos.xCoord;
					dy = (getPos().getY()+1.5)-pos.yCoord;
					dz = (getPos().getZ()+0.5)-pos.zCoord;
					
					for (double l = 0; l < random.nextInt(10); l ++){
						double coeff = 2.0*((l/9.0)-0.5);
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, getPos().getX()+0.5+coeff*dx, getPos().getY()+1.5+coeff*dy, getPos().getZ()+0.5+coeff*dz, 0, 1, 1, 0);
					}
				}
			}
			for (int i = 0; i < particleVessels.size(); i ++){
				if (particleVessels.get(i).contents.element != null){
					BlockPos bp = (particleVessels.get(i)).getPos();
					Vec3 pos = new Vec3(bp.getX()+0.5,bp.getY()+1.0,bp.getZ()+0.5);
					for (int j = 0; j < 10; j ++){
						double dx, dy, dz = 0;
						double xDir = random.nextFloat()*2.0*Math.PI;
						double yDir = random.nextFloat()*2.0*Math.PI;
						dx = (getPos().getX()+0.5)-pos.xCoord;
						dy = (getPos().getY()+1.5)-pos.yCoord;
						dz = (getPos().getZ()+0.5)-pos.zCoord;
						
						for (double l = 0; l < random.nextInt(10); l ++){
							double coeff = 2.0*((l/9.0)-0.5);
							getWorld().spawnParticle(EnumParticleTypes.REDSTONE, getPos().getX()+0.5+coeff*dx, getPos().getY()+1.5+coeff*dy, getPos().getZ()+0.5+coeff*dz, particleVessels.get(i).contents.element.vColor.getX()/255.0-1.0, particleVessels.get(i).contents.element.vColor.getY()/255.0, particleVessels.get(i).contents.element.vColor.getZ()/255.0, 0);
						}
					}
				}
			}
			particleVessels = null;
			particleInventories = null;
		}
		ticker ++;
		if (ticker >= 100){
			ticker = 0;
			aaElementRecipe recipe = null;
			World world = getWorld();
			if (world.getBlockState(getPos().up()).getBlock() == aaItemManager.arcaneFocus){
				arcaneFocusEntity afe = (arcaneFocusEntity)world.getTileEntity(getPos().up());
				if (afe.heldItem != null){
					recipe = aaElementManager.getRecipeForItem(afe.heldItem);
				}
			}
			if (recipe != null){
				ArrayList<IInventory> nearbyInventories = new ArrayList<IInventory>();
				ArrayList<vesselEntity> nearbyVessels = new ArrayList<vesselEntity>();
				ArrayList<NBTTagCompound> backupVesselData = new ArrayList<NBTTagCompound>();
				ArrayList<NBTTagCompound> backupInventoryData = new ArrayList<NBTTagCompound>();
				for (int i = -1; i < 2; i ++){
					for (int j = -1; j < 2; j ++){
						BlockPos tempPos = getPos().add(i,0,j);
						if (world.getTileEntity(tempPos) != null){
							if (world.getTileEntity(tempPos) instanceof IInventory){
								nearbyInventories.add((IInventory)world.getTileEntity(tempPos));
								NBTTagCompound tag = new NBTTagCompound();
								(world.getTileEntity(tempPos)).writeToNBT(tag);
								backupInventoryData.add(tag);
							}
							if (world.getTileEntity(tempPos) instanceof vesselEntity){
								nearbyVessels.add((vesselEntity)world.getTileEntity(tempPos));
								NBTTagCompound tag = new NBTTagCompound();
								((vesselEntity)world.getTileEntity(tempPos)).writeToNBT(tag);
								backupVesselData.add(tag);
							}
						}
					}
				}
				double fireReq = recipe.evFire.value*2.0;
				double earthReq = recipe.evEarth.value*2.0;
				double waterReq = recipe.evWater.value*2.0;
				double airReq = recipe.evAir.value*2.0;
				double lightReq = recipe.evLight.value*2.0;
				double voidReq = recipe.evVoid.value*2.0;
				double class1Req = 0;
				double class2Req = 0;
				if (recipe.itemClass1 != null){
					class1Req = recipe.itemClass1.value*2.0;
				}
				if (recipe.itemClass2 != null){
					class2Req = recipe.itemClass2.value*2.0;
				}
				int k = 0;
				while (k < nearbyVessels.size()){
					if (nearbyVessels.get(k).contents.element != null){
						if (nearbyVessels.get(k).contents.element.name == "fire" && nearbyVessels.get(k).contents.value >= 1.0 && fireReq > 0){
							nearbyVessels.get(k).contents.value -= 1.0;
							fireReq -= 1.0;
							k = -1;
						}
						else if (nearbyVessels.get(k).contents.element.name == "earth" && nearbyVessels.get(k).contents.value >= 1.0 && earthReq > 0){
							nearbyVessels.get(k).contents.value -= 1.0;
							earthReq -= 1.0;
							k = -1;
						}
						else if (nearbyVessels.get(k).contents.element.name == "water" && nearbyVessels.get(k).contents.value >= 1.0 && waterReq > 0){
							nearbyVessels.get(k).contents.value -= 1.0;
							waterReq -= 1.0;
							k = -1;
						}
						else if (nearbyVessels.get(k).contents.element.name == "air" && nearbyVessels.get(k).contents.value >= 1.0 && airReq > 0){
							nearbyVessels.get(k).contents.value -= 1.0;
							airReq -= 1.0;
							k = -1;
						}
						else if (nearbyVessels.get(k).contents.element.name == "light" && nearbyVessels.get(k).contents.value >= 1.0 && lightReq > 0){
							nearbyVessels.get(k).contents.value -= 1.0;
							lightReq -= 1.0;
							k = -1;
						}
						else if (nearbyVessels.get(k).contents.element.name == "void" && nearbyVessels.get(k).contents.value >= 1.0 && voidReq > 0){
							nearbyVessels.get(k).contents.value -= 1.0;
							voidReq -= 1.0;
							k = -1;
						}
					}
					k ++;
				}
				k = 0;
				if (fireReq <= 0 && earthReq <= 0 && waterReq <= 0 && airReq <= 0 && lightReq <= 0 && voidReq <= 0){
					while (k < nearbyInventories.size()){
						int l = 0;
						while (l < nearbyInventories.get(k).getSizeInventory()){
							if (recipe.itemClass1 != null && class1Req > 0 && nearbyInventories.get(k).getStackInSlot(l) != null){
								if (nearbyInventories.get(k).getStackInSlot(l).getItem() == aaElementManager.matterItemFromString(recipe.itemClass1.element.name).getItem() && nearbyInventories.get(k).getStackInSlot(l).getMetadata() == aaElementManager.matterItemFromString(recipe.itemClass1.element.name).getMetadata()){
									nearbyInventories.get(k).decrStackSize(l, 1);
									l = -1;
									class1Req -= 1.0;
								}
							}
							if (recipe.itemClass2 != null && class2Req > 0 && nearbyInventories.get(k).getStackInSlot(l) != null){
								if (nearbyInventories.get(k).getStackInSlot(l).getItem() == aaElementManager.matterItemFromString(recipe.itemClass2.element.name).getItem() && nearbyInventories.get(k).getStackInSlot(l).getMetadata() == aaElementManager.matterItemFromString(recipe.itemClass2.element.name).getMetadata()){
									nearbyInventories.get(k).decrStackSize(l, 1);
									l = -1;
									class2Req -= 1.0;
								}
							}
							l ++;
						}
						k ++;
					}
				}
				Random random = new Random();
				if (class1Req <= 0 && class2Req <= 0 && fireReq <= 0 && earthReq <= 0 && waterReq <= 0 && airReq <= 0 && lightReq <= 0 && voidReq <= 0){
					if (world.isRemote == false){
						ItemStack result = new ItemStack(recipe.item.getItem(),1,recipe.item.getMetadata());
						result.setTagCompound(recipe.item.getTagCompound());
						world.spawnEntityInWorld(new EntityItem(world,getPos().getX()+0.5,getPos().getY()+1.5,getPos().getZ()+0.5,result));
					}
					spawnParticles = true;
					particleInventories = nearbyInventories;
					particleVessels = nearbyVessels;
				}
				else {
					for (int i = 0; i < nearbyInventories.size(); i ++){
						((TileEntity)nearbyInventories.get(i)).readFromNBT(backupInventoryData.get(i));
					}
					for (int i = 0; i < nearbyVessels.size(); i ++){
						nearbyVessels.get(i).readFromNBT(backupVesselData.get(i));
					}
				}
			}
		}
	}
}
