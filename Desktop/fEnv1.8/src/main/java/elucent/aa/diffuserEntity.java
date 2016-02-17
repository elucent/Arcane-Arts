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

public class diffuserEntity extends TileEntity implements ITickable {
	aaElementValue fireValue, earthValue, waterValue, airValue, lightValue, voidValue;
	int ticker = 0;
	Random random = new Random();
	public diffuserEntity(){
		super();
		fireValue = new aaElementValue(aaElementManager.elementFire,0);
		earthValue = new aaElementValue(aaElementManager.elementEarth,0);
		waterValue = new aaElementValue(aaElementManager.elementWater,0);
		airValue = new aaElementValue(aaElementManager.elementAir,0);
		lightValue = new aaElementValue(aaElementManager.elementLight,0);
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
		if (ticker % 20 == 0){
			if (fireValue.getValue() > 0){
				fireValue.setValue(fireValue.getValue()-0.01f);
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
				earthValue.setValue(earthValue.getValue()-0.01f);
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
				waterValue.setValue(waterValue.getValue()-0.01f);
				List targets = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(getPos().getX()-3, getPos().getY()-3, getPos().getZ()-3, getPos().getX()+4, getPos().getY()+4, getPos().getZ()+4));
				if (targets.size() > 0){
					EntityLivingBase target = (EntityLivingBase)(targets.get(random.nextInt(targets.size())));
					target.extinguish();
					target.addPotionEffect(new PotionEffect(2, 200, 1));
				}
			}
			if (airValue.getValue() > 0){
				airValue.setValue(airValue.getValue()-0.01f);
				List targets = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(getPos().getX()-3, getPos().getY()-3, getPos().getZ()-3, getPos().getX()+4, getPos().getY()+4, getPos().getZ()+4));
				for (int i = 0; i < targets.size(); i ++){
					EntityLivingBase el = (EntityLivingBase)(targets.get(i));
					el.knockBack(el, (float) ((float) 8.0f*(el.posX-((float)getPos().getX() + 0.5f))), 6.0, 8.0*(((double)getPos().getZ() + 0.5f - el.posZ)));
				}
			}
			if (lightValue.getValue() > 0){
				lightValue.setValue(lightValue.getValue()-0.01f);
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
				voidValue.setValue(voidValue.getValue()-0.01f);
				List targets = getWorld().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(getPos().getX()-3, getPos().getY()-3, getPos().getZ()-3, getPos().getX()+4, getPos().getY()+4, getPos().getZ()+4));
				if (targets.size() > 0){
					EntityLivingBase el = (EntityLivingBase)(targets.get(random.nextInt(targets.size())));
					el.setHealth(el.getHealth()-1);
					el.addPotionEffect(new PotionEffect(20, 200, 1));
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
			System.out.println("Added value to value list: " + fireValue.getElement().getName());
		}
		if (earthValue.getValue() > 0){
			values.add(earthValue);
			System.out.println("Added value to value list: " + earthValue.getElement().getName());
		}
		if (waterValue.getValue() > 0){
			values.add(waterValue);
			System.out.println("Added value to value list: " + waterValue.getElement().getName());
		}
		if (airValue.getValue() > 0){
			values.add(airValue);
			System.out.println("Added value to value list: " + airValue.getElement().getName());
		}
		if (lightValue.getValue() > 0){
			values.add(lightValue);
			System.out.println("Added value to value list: " + lightValue.getElement().getName());
		}
		if (voidValue.getValue() > 0){
			values.add(voidValue);
			System.out.println("Added value to value list: " + voidValue.getElement().getName());
		}
		for (int i = 0; i < values.size(); i ++){
			System.out.println("Value " + Integer.toString(i) + " is " + values.get(i).getElement().getName());
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
	}
}
