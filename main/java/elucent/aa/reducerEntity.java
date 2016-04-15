package elucent.aa;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class reducerEntity extends TileEntity implements ITickable, tileElementContainer {
	aaElementValue fireValue, earthValue, waterValue, airValue, lightValue, voidValue;
	public double fireCapacity = 2560;
	public double earthCapacity = 2560;
	public double waterCapacity = 2560;
	public double airCapacity = 2560;
	public double lightCapacity = 2560;
	public double voidCapacity = 2560;
	int ticker = 0;
	boolean heated = false;
	int burntime = 0;
	Random random = new Random();
	public reducerEntity(){
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
		if (tag.hasKey("heated")){
			heated = tag.getBoolean("heated");
		}
		if (tag.hasKey("burntime")){
			burntime = tag.getInteger("burntime");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		tag.setBoolean("heated", heated);
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
		tag.setInteger("burntime",burntime);
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
	
	public void onActivated(){
		
	}

	@Override
	public void update() {
		ticker ++;
		boolean didDecr = false;
		if (burntime > 0){
			getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, getPos().getX()+0.25+0.5*random.nextFloat(), getPos().getY()+1, getPos().getZ()+0.25+0.5*random.nextFloat(), 0, random.nextFloat()*0.1+0.1, 0, 0);
			burntime --;
			if (burntime == 0){
				getWorld().setBlockState(getPos(), getWorld().getBlockState(pos).withProperty(reducerBlock.activated, reducerBlock.activatedState.enumOff),3);
			}
		}
		if (ticker % 5 == 0){
			if (fireValue.getValue() > 0){
				fireValue.setValue(fireValue.getValue()-0.01f);
				didDecr = true;
			}
			if (earthValue.getValue() > 0){
				earthValue.setValue(earthValue.getValue()-0.01f);
				didDecr = true;
			}
			if (waterValue.getValue() > 0){
				waterValue.setValue(waterValue.getValue()-0.01f);
				didDecr = true;
			}
			if (airValue.getValue() > 0){
				airValue.setValue(airValue.getValue()-0.01f);
				didDecr = true;
			}
			if (lightValue.getValue() > 0){
				lightValue.setValue(lightValue.getValue()-0.01f);
				didDecr = true;
			}
			if (voidValue.getValue() > 0){
				voidValue.setValue(voidValue.getValue()-0.01f);
				didDecr = true;
			}
			if (didDecr){
				getWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, getPos().getX()+0.25+0.5*random.nextFloat(), getPos().getY()+1, getPos().getZ()+0.25+0.5*random.nextFloat(), 0, random.nextFloat()*0.1+0.1, 0, 0);
			}
		}
		if (ticker % 20 == 0){
			ticker = 0;
			Block oneBelow = getWorld().getBlockState(this.getPos().add(0,-1,0)).getBlock();
			if (oneBelow == Blocks.lava){
				heated = true;
			}
			if (oneBelow == Blocks.air) {
				heated = false;
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
			//System.out.println("Added value to value list: " + fireValue.getElement().getName());
		}
		if (earthValue.getValue() > 0){
			values.add(earthValue);
			//System.out.println("Added value to value list: " + earthValue.getElement().getName());
		}
		if (waterValue.getValue() > 0){
			values.add(waterValue);
			//System.out.println("Added value to value list: " + waterValue.getElement().getName());
		}
		if (airValue.getValue() > 0){
			values.add(airValue);
			//System.out.println("Added value to value list: " + airValue.getElement().getName());
		}
		if (lightValue.getValue() > 0){
			values.add(lightValue);
			//System.out.println("Added value to value list: " + lightValue.getElement().getName());
		}
		if (voidValue.getValue() > 0){
			values.add(voidValue);
			//System.out.println("Added value to value list: " + voidValue.getElement().getName());
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
		boolean doIncinerate = true;
		if (player.getHeldItem() != null){
			if (player.getHeldItem().getItem() == aaItemManager.debugWand){
				player.addChatMessage(new ChatComponentText(Double.toString(fireValue.getValue())+" "+
						Double.toString(earthValue.getValue())+" "+
						Double.toString(waterValue.getValue())+" "+
						Double.toString(airValue.getValue())+" "+
						Double.toString(lightValue.getValue())+" "+
						Double.toString(voidValue.getValue())+" "));
				doIncinerate = false;
			}
			if (player.getHeldItem().getItem() == aaItemManager.itemAlchemyFlask){
				ItemStack stack = player.getHeldItem();
				if (stack.getMetadata() == 0){
					ArrayList<aaElementValue> values = getValueList();
					if (values.size() > 0){
						if (values.get(0).getValue() > 0){
							stack.setItemDamage(1);
							stack.setTagCompound(new NBTTagCompound());
							stack.getTagCompound().setString("elementName", values.get(0).getElement().getName());
							stack.getTagCompound().setDouble("elementValue", Math.min(values.get(0).getValue(),128.0f));
							setElementValueByString(values.get(0).element.getName(), Math.max(values.get(0).getValue()-128.0,0.0));
						}
					}
				}
				else {
					if (stack.hasTagCompound()){
						String elementName = "";
						double elementQuantity = 0;
						if (stack.getTagCompound().hasKey("elementName")){
							elementName = stack.getTagCompound().getString("elementName");
						}
						if (stack.getTagCompound().hasKey("elementValue")){
							elementQuantity = stack.getTagCompound().getDouble("elementValue");
						}
						if (elementQuantity < 128){
							double containerQuantity = 0;
							ArrayList<aaElementValue> values = getValueList();
							for (int i = 0; i < values.size(); i ++){
								if (aaElementManager.getElementFromString(elementName) == values.get(i).getElement()){
									containerQuantity = values.get(i).getValue();
								}
							}
							if (containerQuantity > 0){
								if (elementQuantity + containerQuantity <= 128.0){
									elementQuantity += containerQuantity;
									setElementValueByString(elementName,0);
									stack.getTagCompound().setDouble("elementValue", elementQuantity);
								}
								else {
									elementQuantity = 128;
									setElementValueByString(elementName,containerQuantity-(128.0-elementQuantity));
									stack.getTagCompound().setDouble("elementValue", elementQuantity);
								}
							}
						}
					}
				}
				doIncinerate = false;
			}
		}
		if (burntime <= 0 && doIncinerate){
			if (player.getHeldItem() != null && heated){
				ArrayList<aaElementValue> recipe = arcaneArts.instance.globalElementManager.getStackComposition(player.getHeldItem());
				fireValue.setValue(fireValue.getValue()+aaElementManager.getValue(recipe,"fire"));
				earthValue.setValue(earthValue.getValue()+aaElementManager.getValue(recipe,"earth"));
				waterValue.setValue(waterValue.getValue()+aaElementManager.getValue(recipe,"water"));
				airValue.setValue(airValue.getValue()+aaElementManager.getValue(recipe,"air"));
				lightValue.setValue(lightValue.getValue()+aaElementManager.getValue(recipe,"light"));
				voidValue.setValue(voidValue.getValue()+aaElementManager.getValue(recipe,"void"));
				aaElementManager.decompose(recipe, getWorld(), getPos().add(0.5,1.5,0.5));
				player.getHeldItem().stackSize --;
				NBTTagCompound tag = new NBTTagCompound();
				getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()).withProperty(reducerBlock.activated, reducerBlock.activatedState.enumOn));
				burntime = 10;
			}
		}
		this.markDirty();
	}

	public void breakBlock(World world, BlockPos pos, IBlockState state,EntityPlayer player) {
		invalidate();
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
