package elucent.aa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class itemMagicChalk extends Item {

	public itemMagicChalk() {
		super();
		setUnlocalizedName("itemMagicChalk");
		setRegistryName("itemMagicChalk");
		setCreativeTab(arcaneArts.tab);
		setMaxDamage(54);
	}
	
	@Override
	public boolean getHasSubtypes(){
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems){
		ItemStack stack = new ItemStack(item,1,0);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("runeType", 1);
		subItems.add(stack);
	}
	
	@Override
	public boolean isDamageable(){
		return true;
	}
	
	public String intToRuneType(int runeType){
		if (runeType == 1){
			return "Drawing: Rune of Earth";
		}
		if (runeType == 2){
			return "Drawing: Rune of Water";
		}
		if (runeType == 3){
			return "Drawing: Rune of Fire";
		}
		if (runeType == 4){
			return "Drawing: Rune of Air";
		}
		if (runeType == 5){
			return "Drawing: Rune of Light";
		}
		if (runeType == 6){
			return "Drawing: Rune of Void";
		}
		return "null";
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced){
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().hasKey("runeType")){
				list.add(intToRuneType(stack.getTagCompound().getInteger("runeType")));
			}
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (player.isSneaking()){
			if (stack.hasTagCompound()){
				if (stack.getTagCompound().hasKey("runeType")){
					List<String> runeTypes = stack.getTooltip(player,false);
					int type = stack.getTagCompound().getInteger("runeType")+1;
					if (type > 6){
						type = 1;
					}
					stack.getTagCompound().setInteger("runeType", type);
					if (!world.isRemote){
						player.addChatComponentMessage(new ChatComponentText(intToRuneType(type)));
					}
				}
			}
		}
		return stack;
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player){
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("runeType", 1);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
		if (player.isSneaking() == false){
			/*if (world.getBlockState(pos).getBlock() == aaItemManager.magicRune){
				
			}*/
			if (getDamage(stack) < 0){
				stack.stackSize = 0;
			}
			if (stack.hasTagCompound()){
				if (stack.getTagCompound().hasKey("runeType")){
					if (world.getBlockState(pos.add(arcaneArts.getPosFromSide(side)).add(new Vec3i(0,-1,0))).getBlock() != Blocks.air
							&& world.getBlockState(pos.add(arcaneArts.getPosFromSide(side)).add(new Vec3i(0,-1,0))).getBlock().isFullBlock()
							&& world.getBlockState(pos.add(arcaneArts.getPosFromSide(side)).add(new Vec3i(0,-1,0))).getBlock().isOpaqueCube()){
						world.setBlockState(pos.add(arcaneArts.getPosFromSide(side)), aaItemManager.magicRune.getStateFromMeta(stack.getTagCompound().getInteger("runeType")));
						if (world.isRemote){
							System.out.println("Placed chalk with state " + aaItemManager.magicRune.getStateFromMeta(stack.getTagCompound().getInteger("runeType")).toString());
						}
					}
				}
			}
			return true;
		}
		else return false;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(String name){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:"+name, "inventory"));
	}
}
