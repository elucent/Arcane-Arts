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

public class crystalSmelteryEntity extends TileEntity implements ITickable, tileEnergyContainer {
	public int ticker = 0;
	public double fireValue = 0;
	public double earthValue = 0;
	public double waterValue = 0;
	public double airValue = 0;
	public double lightValue = 0;
	public double voidValue = 0;
	public double fireCapacity = 256;
	public double earthCapacity = 0;
	public double waterCapacity = 0;
	public double airCapacity = 0;
	public double lightCapacity = 0;
	public double voidCapacity = 0;
	public crystalSmelteryEntity(){
		super();
		fireCapacity = 256;
		earthCapacity = 0;
		waterCapacity = 0;
		airCapacity = 0;
		lightCapacity = 0;
		voidCapacity = 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		if (tag.hasKey("fire")){
			fireValue = (tag.getDouble("fire"));
		}
		if (tag.hasKey("earth")){
			earthValue = (tag.getDouble("earth"));
		}
		if (tag.hasKey("water")){
			waterValue = (tag.getDouble("water"));
		}
		if (tag.hasKey("air")){
			airValue = (tag.getDouble("air"));
		}
		if (tag.hasKey("light")){
			lightValue = (tag.getDouble("light"));
		}
		if (tag.hasKey("void")){
			voidValue = (tag.getDouble("void"));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		tag.setDouble("fire", fireValue);
		tag.setDouble("earth", earthValue);
		tag.setDouble("water", waterValue);
		tag.setDouble("air", airValue);
		tag.setDouble("light", lightValue);
		tag.setDouble("void", voidValue);
		tag.setDouble("fireCapacity", fireCapacity);
		tag.setDouble("earthCapacity", earthCapacity);
		tag.setDouble("waterCapacity", waterCapacity);
		tag.setDouble("airCapacity", airCapacity);
		tag.setDouble("lightCapacity", lightCapacity);
		tag.setDouble("voidCapacity", voidCapacity);
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
	public double getElementValue(String elementName) {
		if (elementName == "fire"){
			return fireValue;
		}
		if (elementName == "earth"){
			return earthValue;
		}
		if (elementName == "water"){
			return waterValue;
		}
		if (elementName == "air"){
			return airValue;
		}
		if (elementName == "light"){
			return lightValue;
		}
		if (elementName == "void"){
			return voidValue;
		}
		return 0;
	}

	@Override
	public void setElementValue(String elementName, double value) {
		if (elementName == "fire"){
			fireValue = value;
		}
		if (elementName == "earth"){
			earthValue = value;
		}
		if (elementName == "water"){
			waterValue = value;
		}
		if (elementName == "air"){
			airValue = value;
		}
		if (elementName == "light"){
			lightValue = value;
		}
		if (elementName == "void"){
			voidValue = value;
		}
	}

	@Override
	public double getMaxCapacity(String elementName) {
		if (elementName == "fire"){
			return fireCapacity;
		}
		if (elementName == "earth"){
			return earthCapacity;
		}
		if (elementName == "water"){
			return waterCapacity;
		}
		if (elementName == "air"){
			return airCapacity;
		}
		if (elementName == "light"){
			return lightCapacity;
		}
		if (elementName == "void"){
			return voidCapacity;
		}
		return 0;
	}

	@Override
	public void setMaxCapacity(String elementName, double value) {
		if (elementName == "fire"){
			fireCapacity = value;
		}
		if (elementName == "earth"){
			earthCapacity = value;
		}
		if (elementName == "water"){
			waterCapacity = value;
		}
		if (elementName == "air"){
			airCapacity = value;
		}
		if (elementName == "light"){
			lightCapacity = value;
		}
		if (elementName == "void"){
			voidCapacity = value;
		}
	}

	@Override
	public void update() {
		ticker ++;
		if (ticker % 40 == 0){
			ticker = 0;
			if (getElementValue("fire") > 4.0){
				if (getWorld().getBlockState(getPos().up()).getBlock() == aaItemManager.arcaneFocus){
					arcaneFocusEntity afe = (arcaneFocusEntity)getWorld().getTileEntity(getPos().up());
					if (afe.heldItem != null){
						if (FurnaceRecipes.instance().getSmeltingResult(afe.heldItem) != null){
							ItemStack temp = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(afe.heldItem.getItem(),1,afe.heldItem.getItemDamage()));
							ItemStack result = new ItemStack(temp.getItem(),temp.stackSize,temp.getItemDamage());
							if (result.stackSize == 0){
								result.stackSize = 1;
							}
							afe.heldItem.stackSize --;
							setElementValue("fire",getElementValue("fire")-4.0);
							if (getWorld().isRemote == false){
								getWorld().spawnEntityInWorld(new EntityItem(getWorld(),getPos().getX()+0.5,getPos().getY()+1.5,getPos().getZ()+0.5,result));
							}
							if (afe.heldItem.stackSize == 0){
								afe.heldItem = null;
							}
							afe.markDirty();
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean isTargetable(){
		return false;
	}

	@Override
	public void setTarget(BlockPos target) {
		// TODO Auto-generated method stub
		
	}

	public void activate(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (player.getHeldItem() != null){
			if (player.getHeldItem().getItem() == aaItemManager.debugWand){
				Random random = new Random();
				setElementValue("fire",random.nextInt(2560));
				setElementValue("earth",random.nextInt(2560));
				setElementValue("water",random.nextInt(2560));
				setElementValue("air",random.nextInt(2560));
				setElementValue("light",random.nextInt(2560));
				setElementValue("void",random.nextInt(2560));
			}
		}
	}
}
