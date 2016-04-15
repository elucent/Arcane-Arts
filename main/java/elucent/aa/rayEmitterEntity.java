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

public class rayEmitterEntity extends TileEntity implements ITickable, tileEnergyContainer {
	public double angle = 0;
	public double fireValue = 0;
	public double earthValue = 0;
	public double waterValue = 0;
	public double airValue = 0;
	public double lightValue = 0;
	public double voidValue = 0;
	public double fireCapacity = 1;
	public double earthCapacity = 1;
	public double waterCapacity = 1;
	public double airCapacity = 1;
	public double lightCapacity = 1;
	public double voidCapacity = 1;
	public String attunement = "fire";
	public int ticker = 0;
	public BlockPos target;
	public rayEmitterEntity(){
		super();
		target = getPos();
		fireCapacity = 1;
		earthCapacity = 1;
		waterCapacity = 1;
		airCapacity = 1;
		lightCapacity = 1;
		voidCapacity = 1;
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
		if (tag.hasKey("attunement")){
			int attuneInt = tag.getInteger("attunement");
			if (attuneInt == 0){
				attunement = "fire";
			}
			if (attuneInt == 1){
				attunement = "earth";
			}
			if (attuneInt == 2){
				attunement = "water";
			}
			if (attuneInt == 3){
				attunement = "air";
			}
			if (attuneInt == 4){
				attunement = "light";
			}
			if (attuneInt == 5){
				attunement = "void";
			}
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
		int attuneInt = 0;
		if (attunement == "earth"){
			attuneInt = 1;
		}
		if (attunement == "water"){
			attuneInt = 2;
		}
		if (attunement == "air"){
			attuneInt = 3;
		}
		if (attunement == "light"){
			attuneInt = 4;
		}
		if (attunement == "void"){
			attuneInt = 5;
		}
		tag.setInteger("attunement", attuneInt);
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
				boolean foundSource = false;
				if (getWorld().getTileEntity(getPos().north()) instanceof tileEnergyContainer){
					tileEnergyContainer tec = (tileEnergyContainer)getWorld().getTileEntity(getPos().north());
					if (tec.getElementValue(attunement) > 0 && getElementValue(attunement) < getMaxCapacity(attunement)){
						double dQuantity = Math.min(tec.getElementValue(attunement), getMaxCapacity(attunement)-getElementValue(attunement));
						tec.setElementValue(attunement,tec.getElementValue(attunement)-dQuantity);
						setElementValue(attunement,getElementValue(attunement)+dQuantity);
						getWorld().getTileEntity(getPos().north()).markDirty();
						this.markDirty();
					}
				}
				if (getWorld().getTileEntity(getPos().north()) instanceof tileEnergyContainer){
					tileEnergyContainer tec = (tileEnergyContainer)getWorld().getTileEntity(getPos().north());
					if (tec.getElementValue(attunement) > 0 && getElementValue(attunement) < getMaxCapacity(attunement)){
						double dQuantity = Math.min(tec.getElementValue(attunement), getMaxCapacity(attunement)-getElementValue(attunement));
						tec.setElementValue(attunement,tec.getElementValue(attunement)-dQuantity);
						setElementValue(attunement,getElementValue(attunement)+dQuantity);
						getWorld().getTileEntity(getPos().north()).markDirty();
						this.markDirty();
					}
				}
				if (getWorld().getTileEntity(getPos().south()) instanceof tileEnergyContainer){
					tileEnergyContainer tec = (tileEnergyContainer)getWorld().getTileEntity(getPos().south());
					if (tec.getElementValue(attunement) > 0 && getElementValue(attunement) < getMaxCapacity(attunement)){
						double dQuantity = Math.min(tec.getElementValue(attunement), getMaxCapacity(attunement)-getElementValue(attunement));
						tec.setElementValue(attunement,tec.getElementValue(attunement)-dQuantity);
						setElementValue(attunement,getElementValue(attunement)+dQuantity);
						getWorld().getTileEntity(getPos().south()).markDirty();
						this.markDirty();
					}
				}
				if (getWorld().getTileEntity(getPos().east()) instanceof tileEnergyContainer){
					tileEnergyContainer tec = (tileEnergyContainer)getWorld().getTileEntity(getPos().east());
					if (tec.getElementValue(attunement) > 0 && getElementValue(attunement) < getMaxCapacity(attunement)){
						double dQuantity = Math.min(tec.getElementValue(attunement), getMaxCapacity(attunement)-getElementValue(attunement));
						tec.setElementValue(attunement,tec.getElementValue(attunement)-dQuantity);
						setElementValue(attunement,getElementValue(attunement)+dQuantity);
						getWorld().getTileEntity(getPos().east()).markDirty();
						this.markDirty();
					}
				}
				if (getWorld().getTileEntity(getPos().west()) instanceof tileEnergyContainer){
					tileEnergyContainer tec = (tileEnergyContainer)getWorld().getTileEntity(getPos().west());
					if (tec.getElementValue(attunement) > 0 && getElementValue(attunement) < getMaxCapacity(attunement)){
						double dQuantity = Math.min(tec.getElementValue(attunement), getMaxCapacity(attunement)-getElementValue(attunement));
						tec.setElementValue(attunement,tec.getElementValue(attunement)-dQuantity);
						setElementValue(attunement,getElementValue(attunement)+dQuantity);
						getWorld().getTileEntity(getPos().west()).markDirty();
						this.markDirty();
					}
				}
				if (getWorld().isBlockIndirectlyGettingPowered(getPos()) > 0 && getElementValue(attunement) > 0){
					double velX = (target.getX()-getPos().getX())/32.0;
					double velY = (target.getY()-getPos().getY())/32.0;
					double velZ = (target.getZ()-getPos().getZ())/32.0;
					if (getWorld().isRemote == false){
						getWorld().spawnEntityInWorld(new aaEnergyRay(getWorld()).init(getPos().getX()+0.5+1.0*velX, getPos().getY()+0.5+1.0*velY, getPos().getZ()+0.5+1.0*velZ, velX, velY, velZ, aaElementManager.getElementFromString(attunement), getElementValue(attunement), this));
					}
					setElementValue(attunement,0);
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
			if (player.getHeldItem().getItem() == aaItemManager.tinkerWrench){
				if (attunement == "fire"){
					attunement = "earth";
				}
				else if (attunement == "earth"){
					attunement = "water";
				}
				else if (attunement == "water"){
					attunement = "air";
				}
				else if (attunement == "air"){
					attunement = "light";
				}
				else if (attunement == "light"){
					attunement = "void";
				}
				else if (attunement == "void"){
					attunement = "fire";
				}
			}
		}
		markDirty();
	}
}
