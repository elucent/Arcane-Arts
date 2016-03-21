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

public class fireInfuserEntity extends TileEntity implements ITickable {
	public int progress = 0;
	public ItemStack heldItem = null;
	int ticker = 0;
	Random random = new Random();
	public boolean filled = false;
	public fireInfuserEntity(){
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
		ticker ++;
		if (progress > 0){
			progress --;
			getWorld().spawnParticle(EnumParticleTypes.REDSTONE, getPos().getX()+0.25+0.5*random.nextFloat(), getPos().getY()+0.25+0.5*random.nextFloat(), getPos().getZ()+0.25+0.5*random.nextFloat(), 0, 0.25, 0, 0);
			if (progress == 0 && filled){
				for (int i = 0; i < 16; i ++){
					getWorld().spawnParticle(EnumParticleTypes.REDSTONE, getPos().getX()+random.nextFloat(), getPos().getY()+random.nextFloat(), getPos().getZ()+random.nextFloat(), 0, 0.25, 0, 0);
				}
				getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()).getBlock().getStateFromMeta(0));
				filled = false;
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
		if (player.getHeldItem() == null){
		}
		if (player.getHeldItem() != null){
			ItemStack stack = player.getHeldItem();
			if (player.getHeldItem().getItem() == aaItemManager.itemAlchemyFlask){
				if (world.getBlockState(getPos()).getBlock().getMetaFromState(state) == 1){
					if (stack.getMetadata() == 0){
						if (stack.hasTagCompound()){
							world.setBlockState(getPos(), world.getBlockState(getPos()).getBlock().getStateFromMeta(0));
							filled = false;
							stack.getTagCompound().setDouble("elementValue", 4.0);
							stack.getTagCompound().setString("elementName","fire");
							stack.setItemDamage(1);
						}
					}
					else if (stack.getMetadata() == 1){
						if (stack.hasTagCompound()){
							if (stack.getTagCompound().hasKey("elementName") && stack.getTagCompound().hasKey("elementValue")){
								if (stack.getTagCompound().getString("elementName") == "fire"){
									stack.getTagCompound().setDouble("elementValue", stack.getTagCompound().getDouble("elementValue")+4.0);
									filled = false;
									world.setBlockState(getPos(), world.getBlockState(getPos()).getBlock().getStateFromMeta(0));
								}
							}
						}
					}
				}
				else if (world.getBlockState(getPos()).getBlock().getMetaFromState(state) == 0){
					if (stack.getMetadata() == 1){
						if (stack.hasTagCompound()){
							if (stack.getTagCompound().hasKey("elementName") && stack.getTagCompound().hasKey("elementValue")){
								if (aaElementManager.getElementFromString(stack.getTagCompound().getString("elementName")).equals(aaElementManager.elementFire)){
									if (stack.getTagCompound().getDouble("elementValue") >= 4.0){
										filled = true;
										world.setBlockState(getPos(), world.getBlockState(getPos()).getBlock().getStateFromMeta(1));
										stack.getTagCompound().setDouble("elementValue", stack.getTagCompound().getDouble("elementValue")-4.0);

										if (stack.getTagCompound().getDouble("elementValue") == 0){
											stack.setItemDamage(0);
										}
									}
								}
							}
						}
					}
				}
			}
			else if (progress == 0 && filled){
				if (stack.getItem() instanceof itemElementContainer){
					if (((itemElementContainer)stack.getItem()).isElementValid(stack, "fire")){
						((itemElementContainer)(stack.getItem())).setElementValue(stack, "fire", ((itemElementContainer)(stack.getItem())).getElementValue(stack,"fire")+4.0);
						heldItem = stack;
						stack.stackSize = 0;
						progress = 20;
					}
				}
				else {
					ArrayList<aaSimpleInfusionRecipe> recipes = aaElementManager.infusions;
					boolean doBreak = false;
					for (int i = 0; i < recipes.size() && !doBreak; i ++){
						if (recipes.get(i).element == aaElementManager.elementFire){
							if (recipes.get(i).input.getItem() == stack.getItem() && recipes.get(i).input.getMetadata() == stack.getMetadata()){
								heldItem = recipes.get(i).result;
								stack.stackSize --;
								progress = 200;
								doBreak = true;
							}
						}
					}
				}
			}
		}
	}
}
