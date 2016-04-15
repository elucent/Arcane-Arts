package elucent.aa;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
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
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class rayLensEntity extends TileEntity implements ITickable, tileEnergyContainer {
	public double angle = 0;
	public double fireValue = 0;
	public double earthValue = 0;
	public double waterValue = 0;
	public double airValue = 0;
	public double lightValue = 0;
	public double voidValue = 0;
	public double fireCapacity = 8;
	public double earthCapacity = 8;
	public double waterCapacity = 8;
	public double airCapacity = 8;
	public double lightCapacity = 8;
	public double voidCapacity = 8;
	public String attunement = "fire";
	public int ticker = 0;
	public BlockPos target;
	public rayLensEntity(){
		super();
		target = getPos();
		fireCapacity = 8;
		earthCapacity = 8;
		waterCapacity = 8;
		airCapacity = 8;
		lightCapacity = 8;
		voidCapacity = 8;
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
		if (tag.hasKey("targetX")){
			target = new BlockPos(tag.getInteger("targetX"),tag.getInteger("targetY"),tag.getInteger("targetZ"));
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
		tag.setInteger("targetX", target.getX());
		tag.setInteger("targetY", target.getY());
		tag.setInteger("targetZ", target.getZ());
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
		angle += 1.0;
		ticker ++;
		if (ticker % 20 == 0){
			//if (getWorld().isBlockIndirectlyGettingPowered(getPos()) > 0){
				ticker = 0;
				double velX = (target.getX()-getPos().getX())/32.0;
				double velY = (target.getY()-getPos().getY())/32.0;
				double velZ = (target.getZ()-getPos().getZ())/32.0;
				if (getElementValue("fire") > 0){
					getWorld().spawnEntityInWorld(new aaEnergyRay(getWorld()).init(getPos().getX()+0.5+4.0*velX, getPos().getY()+0.5+4.0*velY, getPos().getZ()+0.5+4.0*velZ, velX, velY, velZ, aaElementManager.getElementFromString("fire"), getElementValue("fire")));
					setElementValue("fire",0);
					markDirty();
				}
				if (getElementValue("earth") > 0){
					getWorld().spawnEntityInWorld(new aaEnergyRay(getWorld()).init(getPos().getX()+0.5+4.0*velX, getPos().getY()+0.5+4.0*velY, getPos().getZ()+0.5+4.0*velZ, velX, velY, velZ, aaElementManager.getElementFromString("earth"), getElementValue("earth")));
					setElementValue("earth",0);
					markDirty();
				}
				if (getElementValue("water") > 0){
					getWorld().spawnEntityInWorld(new aaEnergyRay(getWorld()).init(getPos().getX()+0.5+4.0*velX, getPos().getY()+0.5+4.0*velY, getPos().getZ()+0.5+4.0*velZ, velX, velY, velZ, aaElementManager.getElementFromString(attunement), getElementValue(attunement)));
					setElementValue(attunement,0);
					markDirty();
				}
				if (getElementValue("air") > 0){
					getWorld().spawnEntityInWorld(new aaEnergyRay(getWorld()).init(getPos().getX()+0.5+4.0*velX, getPos().getY()+0.5+4.0*velY, getPos().getZ()+0.5+4.0*velZ, velX, velY, velZ, aaElementManager.getElementFromString("air"), getElementValue("air")));
					setElementValue("air",0);
					markDirty();
				}
				if (getElementValue("light") > 0){
					getWorld().spawnEntityInWorld(new aaEnergyRay(getWorld()).init(getPos().getX()+0.5+4.0*velX, getPos().getY()+0.5+4.0*velY, getPos().getZ()+0.5+4.0*velZ, velX, velY, velZ, aaElementManager.getElementFromString("light"), getElementValue("light")));
					setElementValue("light",0);
					markDirty();
				}
				if (getElementValue("void") > 0){
					getWorld().spawnEntityInWorld(new aaEnergyRay(getWorld()).init(getPos().getX()+0.5+4.0*velX, getPos().getY()+0.5+4.0*velY, getPos().getZ()+0.5+4.0*velZ, velX, velY, velZ, aaElementManager.getElementFromString("void"), getElementValue("void")));
					setElementValue("void",0);
					markDirty();
				}
			//}
		}
	}
	
	@Override
	public boolean isTargetable(){
		return true;
	}

	@Override
	public void setTarget(BlockPos target) {
		this.target = target;
		this.markDirty();
	}

	public void activate(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (player.getHeldItem() != null){
			if (player.getHeldItem().getItem() == aaItemManager.debugWand){
				player.addChatMessage(new ChatComponentText("Contains " + getElementValue(attunement) + " units of " + attunement + "."));
			}
		}
		markDirty();
	}
}
