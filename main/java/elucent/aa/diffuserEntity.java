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

public class diffuserEntity extends TileEntity implements ITickable, tileElementContainer{
	aaElementValue fireValue, earthValue, waterValue, airValue, lightValue, voidValue;
	public double fireCapacity = 2560;
	public double earthCapacity = 2560;
	public double waterCapacity = 2560;
	public double airCapacity = 2560;
	public double lightCapacity = 2560;
	public double voidCapacity = 2560;
	int ticker = 0;
	Random random = new Random();
	public diffuserEntity(){
		super();
		fireValue = new aaElementValue(aaElementManager.elementFire,0);
		earthValue = new aaElementValue(aaElementManager.elementEarth,0);
		waterValue = new aaElementValue(aaElementManager.elementWater,0);
		airValue = new aaElementValue(aaElementManager.elementAir,0);
		lightValue = new aaElementValue(aaElementManager.elementEarth,0);
		voidValue = new aaElementValue(aaElementManager.elementVoid,0);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		if (tag.hasKey("fire")){
			fireValue.setValue(tag.getDouble("fire"));
		}
		if (tag.hasKey("earth")){
			earthValue.setValue(tag.getDouble("earth"));
		}
		if (tag.hasKey("water")){
			waterValue.setValue(tag.getDouble("water"));
		}
		if (tag.hasKey("air")){
			airValue.setValue(tag.getDouble("air"));
		}
		if (tag.hasKey("light")){
			lightValue.setValue(tag.getDouble("light"));
		}
		if (tag.hasKey("void")){
			voidValue.setValue(tag.getDouble("void"));
		}
		if (tag.hasKey("fireCapacity")){
			fireCapacity = (tag.getDouble("fireCapacity"));
		}
		if (tag.hasKey("earthCapacity")){
			earthCapacity = (tag.getDouble("earthCapacity"));
		}
		if (tag.hasKey("waterCapacity")){
			waterCapacity = (tag.getDouble("waterCapacity"));
		}
		if (tag.hasKey("airCapacity")){
			airCapacity = (tag.getDouble("airCapacity"));
		}
		if (tag.hasKey("lightCapacity")){
			lightCapacity = (tag.getDouble("lightCapacity"));
		}
		if (tag.hasKey("voidCapacity")){
			voidCapacity = (tag.getDouble("voidCapacity"));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		tag.setDouble("fire", fireValue.getValue());
		tag.setDouble("earth", earthValue.getValue());
		tag.setDouble("water", waterValue.getValue());
		tag.setDouble("air", airValue.getValue());
		tag.setDouble("light", lightValue.getValue());
		tag.setDouble("void", voidValue.getValue());
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
	public void update() {
		ticker ++;
		if (getWorld().isBlockIndirectlyGettingPowered(getPos()) > 0){
			if (ticker % 20 == 0){
				if (fireValue.getValue() > 0){
					for (int i = 0; i < 24; i ++){
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, (getPos().getX()-0.5)+2.0*random.nextFloat(), (getPos().getY()-0.5)+2.0*random.nextFloat(), (getPos().getZ()-0.5)+2.0*random.nextFloat(), aaElementManager.elementFire.vColor.getX()/255.0-1.0, aaElementManager.elementFire.vColor.getY()/255.0, aaElementManager.elementFire.vColor.getZ()/255.0, 0);
					}
					fireValue.setValue(fireValue.getValue()-0.05f);
					/*int xCoord = getPos().getX() + (random.nextInt(5)-2);
					int zCoord = getPos().getZ() + (random.nextInt(5)-2);
					if (getWorld().getBlockState(getWorld().getHeight(new BlockPos(xCoord,0,zCoord)).add(new Vec3i(0,1,0))).getBlock() == Blocks.air){
						getWorld().setBlockState(getWorld().getHeight(new BlockPos(xCoord,0,zCoord)).add(new Vec3i(0,1,0)),Blocks.fire.getDefaultState());
					}*/
					List targets = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(getPos().getX()-3, getPos().getY()-3, getPos().getZ()-3, getPos().getX()+4, getPos().getY()+4, getPos().getZ()+4));
					if (targets.size() > 0){
						EntityLivingBase target = (EntityLivingBase) targets.get(random.nextInt(targets.size()));
						if (target.isBurning() == false){
							target.setFire(10);
						}
					}
				}
				if (earthValue.getValue() > 0){
					for (int i = 0; i < 24; i ++){
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, (getPos().getX()-0.5)+2.0*random.nextFloat(), (getPos().getY()-0.5)+2.0*random.nextFloat(), (getPos().getZ()-0.5)+2.0*random.nextFloat(), aaElementManager.elementEarth.vColor.getX()/255.0-1.0, aaElementManager.elementEarth.vColor.getY()/255.0, aaElementManager.elementEarth.vColor.getZ()/255.0, 0);
					}
					earthValue.setValue(earthValue.getValue()-0.05f);
					for (int i = -2; i < 3; i ++){
						for (int j = -2; j < 3; j ++){
							for (int k = -2; k < 3; k ++){
								if (random.nextFloat() > 0.99){
									//getWorld().spawnParticle(EnumParticleTypes.REDSTONE, getPos().getX()+i+random.nextFloat(), getPos().getY()+j+random.nextFloat(),getPos().getZ()+k+random.nextFloat(), -1, 1, 0, 0);
									IBlockState state = getWorld().getBlockState(getPos().add(new Vec3i(i,j,k)));
									boolean doTransform = false;
									if (state == Blocks.sand.getDefaultState()){
										state = Blocks.sandstone.getDefaultState();
										doTransform = true;
									}
									else if (state == Blocks.sandstone.getDefaultState()){
										state = Blocks.sandstone.getStateFromMeta(2);
										doTransform = true;
									}
									else if (state == Blocks.cobblestone.getDefaultState()){
										state = Blocks.stone.getDefaultState();
										doTransform = true;
									}
									else if (state == Blocks.stone.getStateFromMeta(1)){
										state = Blocks.stone.getStateFromMeta(2);
										doTransform = true;
									}
									else if (state == Blocks.stone.getStateFromMeta(3)){
										state = Blocks.stone.getStateFromMeta(4);
										doTransform = true;
									}
									else if (state == Blocks.stone.getStateFromMeta(5)){
										state = Blocks.stone.getStateFromMeta(6);
										doTransform = true;
									}
									else if (state == Blocks.stonebrick.getStateFromMeta(2)){
										state = Blocks.stonebrick.getDefaultState();
										doTransform = true;
									}
									else if (state == Blocks.sand.getStateFromMeta(1)){
										state = Blocks.red_sandstone.getDefaultState();
										doTransform = true;
									}
									else if (state == Blocks.red_sandstone.getDefaultState()){
										state = Blocks.red_sandstone.getStateFromMeta(2);
										doTransform = true;
									}
									else if (state == Blocks.gravel.getDefaultState()){
										state = Blocks.cobblestone.getDefaultState();
										doTransform = true;
									}
									else if (state == Blocks.prismarine.getDefaultState()){
										state = Blocks.prismarine.getStateFromMeta(1);
										doTransform = true;
									}
									else if (state == Blocks.snow.getDefaultState()){
										state = Blocks.ice.getDefaultState();
										doTransform = true;
									}
									else if (state == Blocks.ice.getDefaultState()){
										state = Blocks.packed_ice.getDefaultState();
										doTransform = true;
									}
									if (doTransform){
										getWorld().setBlockState(getPos().add(new Vec3i(i,j,k)), state);
									}
								}
							}
						}
					}
				}
				if (waterValue.getValue() > 0){
					for (int i = 0; i < 24; i ++){
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, (getPos().getX()-0.5)+2.0*random.nextFloat(), (getPos().getY()-0.5)+2.0*random.nextFloat(), (getPos().getZ()-0.5)+2.0*random.nextFloat(), aaElementManager.elementWater.vColor.getX()/255.0-1.0, aaElementManager.elementWater.vColor.getY()/255.0, aaElementManager.elementWater.vColor.getZ()/255.0, 0);
					}
					waterValue.setValue(waterValue.getValue()-0.05f);
					List targets = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(getPos().getX()-3, getPos().getY()-3, getPos().getZ()-3, getPos().getX()+4, getPos().getY()+4, getPos().getZ()+4));
					if (targets.size() > 0){
						EntityLivingBase target = (EntityLivingBase)(targets.get(random.nextInt(targets.size())));
						target.extinguish();
						target.addPotionEffect(new PotionEffect(2, 200, 2));
					}
				}
				if (airValue.getValue() > 0){
					for (int i = 0; i < 24; i ++){
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, (getPos().getX()-0.5)+2.0*random.nextFloat(), (getPos().getY()-0.5)+2.0*random.nextFloat(), (getPos().getZ()-0.5)+2.0*random.nextFloat(), aaElementManager.elementAir.vColor.getX()/255.0-1.0, aaElementManager.elementAir.vColor.getY()/255.0, aaElementManager.elementAir.vColor.getZ()/255.0, 0);
					}
					airValue.setValue(airValue.getValue()-0.05f);
					List targets = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(getPos().getX()-3, getPos().getY()-3, getPos().getZ()-3, getPos().getX()+4, getPos().getY()+4, getPos().getZ()+4));
					for (int i = 0; i < targets.size(); i ++){
						EntityLivingBase el = (EntityLivingBase)(targets.get(i));
						el.knockBack(el, (float) ((float) 40.0f*(el.posX-((float)getPos().getX() + 0.5f))), 24.0, 80.0*(((double)getPos().getZ() + 0.5f - el.posZ)));
					}
				}
				if (lightValue.getValue() > 0){
					for (int i = 0; i < 24; i ++){
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, (getPos().getX()-0.5)+2.0*random.nextFloat(), (getPos().getY()-0.5)+2.0*random.nextFloat(), (getPos().getZ()-0.5)+2.0*random.nextFloat(), aaElementManager.elementLight.vColor.getX()/255.0-1.0, aaElementManager.elementLight.vColor.getY()/255.0, aaElementManager.elementLight.vColor.getZ()/255.0, 0);
					}
					lightValue.setValue(lightValue.getValue()-0.05f);
					for (int i = -3; i < 4; i ++){
						for (int j = -3; j < 4; j ++){
							for (int k = -3; k < 4; k ++){
								if (random.nextFloat() > 0.99){
									//getWorld().spawnParticle(EnumParticleTypes.REDSTONE, getPos().getX()+i+random.nextFloat(), getPos().getY()+j+random.nextFloat(),getPos().getZ()+k+random.nextFloat(), -1, 1, 0, 0);
									IBlockState state = getWorld().getBlockState(getPos().add(new Vec3i(i,j,k)));
									if (state.getBlock() instanceof IGrowable){
										((IGrowable)(state.getBlock())).grow(getWorld(), random, getPos().add(new Vec3i(i,j,k)), state);
									}
								}
							}
						}
					}
					List targets = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(getPos().getX()-3, getPos().getY()-3, getPos().getZ()-3, getPos().getX()+4, getPos().getY()+4, getPos().getZ()+4));
					for (int i = 0; i < targets.size(); i ++){
						EntityLivingBase el = (EntityLivingBase)(targets.get(i));
						el.addPotionEffect(new PotionEffect(10, 200, 1));
					}
				}
				if (voidValue.getValue() > 0){
					for (int i = 0; i < 24; i ++){
						getWorld().spawnParticle(EnumParticleTypes.REDSTONE, (getPos().getX()-0.5)+2.0*random.nextFloat(), (getPos().getY()-0.5)+2.0*random.nextFloat(), (getPos().getZ()-0.5)+2.0*random.nextFloat(), aaElementManager.elementVoid.vColor.getX()/255.0-1.0, aaElementManager.elementVoid.vColor.getY()/255.0, aaElementManager.elementVoid.vColor.getZ()/255.0, 0);
					}
					voidValue.setValue(voidValue.getValue()-0.05f);
					List targets = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(getPos().getX()-3, getPos().getY()-3, getPos().getZ()-3, getPos().getX()+4, getPos().getY()+4, getPos().getZ()+4));
					if (targets.size() > 0){
						EntityLivingBase el = (EntityLivingBase)(targets.get(random.nextInt(targets.size())));
						el.setHealth(el.getHealth()-1);
						el.addPotionEffect(new PotionEffect(20, 200, 0));
					}
				}
			}
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState){
		if (newState.getBlock() == Blocks.air){
			return true;
		}
		else return false;
	}
	
	public ArrayList<aaElementValue> getValueList(){
		ArrayList<aaElementValue> values = new ArrayList<aaElementValue>();
		if (fireValue.getValue() > 0){
			values.add(fireValue);
		}
		if (earthValue.getValue() > 0){
			values.add(earthValue);
		}
		if (waterValue.getValue() > 0){
			values.add(waterValue);
		}
		if (airValue.getValue() > 0){
			values.add(airValue);
		}
		if (lightValue.getValue() > 0){
			values.add(lightValue);
		}
		if (voidValue.getValue() > 0){
			values.add(voidValue);
		}
		return values;
	}
	
	public void setElementValueByString(String name, double amount){
		if (name == "fire"){
			fireValue.setValue(amount);
		}
		if (name == "earth"){
			earthValue.setValue(amount);
		}
		if (name == "water"){
			waterValue.setValue(amount);
		}
		if (name == "air"){
			airValue.setValue(amount);
		}
		if (name == "light"){
			lightValue.setValue(amount);
		}
		if (name == "void"){
			voidValue.setValue(amount);
		}
	}

	public void activate(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (player.getHeldItem() != null){
			if (player.getHeldItem().getItem() == aaItemManager.debugWand){
				player.addChatMessage(new ChatComponentText(Double.toString(fireValue.getValue())+" "+
						Double.toString(earthValue.getValue())+" "+
						Double.toString(waterValue.getValue())+" "+
						Double.toString(airValue.getValue())+" "+
						Double.toString(lightValue.getValue())+" "+
						Double.toString(voidValue.getValue())+" "));
			}
			if (player.getHeldItem().getItem() == aaItemManager.itemAlchemyFlask){
				ItemStack stack = player.getHeldItem();
				if (stack.getMetadata() == 1){
					if (stack.hasTagCompound()){
						if (stack.getTagCompound().hasKey("elementName") && stack.getTagCompound().hasKey("elementValue")){
							if (stack.getTagCompound().getString("elementName") == "fire"){
								fireValue.value += stack.getTagCompound().getDouble("elementValue");
								stack.getTagCompound().setDouble("elementValue", 0.0D);
								stack.setItemDamage(0);
							}
							if (stack.getTagCompound().getString("elementName") == "earth"){
								earthValue.value += stack.getTagCompound().getDouble("elementValue");
								stack.getTagCompound().setDouble("elementValue", 0.0D);
								stack.setItemDamage(0);
							}
							if (stack.getTagCompound().getString("elementName") == "water"){
								waterValue.value += stack.getTagCompound().getDouble("elementValue");
								stack.getTagCompound().setDouble("elementValue", 0.0D);
								stack.setItemDamage(0);
							}
							if (stack.getTagCompound().getString("elementName") == "air"){
								airValue.value += stack.getTagCompound().getDouble("elementValue");
								stack.getTagCompound().setDouble("elementValue", 0.0D);
								stack.setItemDamage(0);
							}
							if (stack.getTagCompound().getString("elementName") == "light"){
								lightValue.value += stack.getTagCompound().getDouble("elementValue");
								stack.getTagCompound().setDouble("elementValue", 0.0D);
								stack.setItemDamage(0);
							}
							if (stack.getTagCompound().getString("elementName") == "void"){
								voidValue.value += stack.getTagCompound().getDouble("elementValue");
								stack.getTagCompound().setDouble("elementValue", 0.0D);
								stack.setItemDamage(0);
							}
						}
					}
				}
			}
		}
		this.markDirty();
	}

	@Override
	public void setElementValue(String elementName, double value) {
		if (elementName == "fire"){
			fireValue.value = value;
		}
		if (elementName == "earth"){
			earthValue.value = value;
		}
		if (elementName == "water"){
			waterValue.value = value;
		}
		if (elementName == "air"){
			airValue.value = value;
		}
		if (elementName == "light"){
			lightValue.value = value;
		}
		if (elementName == "void"){
			voidValue.value = value;
		}
	}
	
	@Override
	public double getElementValue(String elementName) {
		if (elementName == "fire"){
			return fireValue.value;
		}
		if (elementName == "earth"){
			return earthValue.value;
		}
		if (elementName == "water"){
			return waterValue.value;
		}
		if (elementName == "air"){
			return airValue.value;
		}
		if (elementName == "light"){
			return lightValue.value;
		}
		if (elementName == "void"){
			return voidValue.value;
		}
		return 0;
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
}
