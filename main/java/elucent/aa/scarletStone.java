package elucent.aa;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class scarletStone extends ItemTool implements itemVariableResult{
	public scarletStone(){
		super(0, aaItemManager.materialScarlet, null);
		setUnlocalizedName("scarletStone");
		setRegistryName("scarletStone");
		setCreativeTab(arcaneArts.tab);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		tooltip.add("An orb of condensed tranmuting energy.");
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:scarletStone","inventory"));
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
		Block block = world.getBlockState(pos).getBlock();
		if (block == Blocks.dirt){
			world.setBlockState(pos, Blocks.gravel.getDefaultState());
			return true;
		}
		if (block == Blocks.gravel){
			world.setBlockState(pos, Blocks.dirt.getDefaultState());
			return true;
		}
		if (block == Blocks.cobblestone){
			world.setBlockState(pos, Blocks.stone.getDefaultState());
			return true;
		}
		if (block == Blocks.stone){
			world.setBlockState(pos, Blocks.cobblestone.getDefaultState());
			return true;
		}
		return false;
	}

	@Override
	public double getCorrectness(ItemStack stack) {
		return 1.0;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack){
		ItemStack newStack = stack.copy();
		newStack.damageItem(1, null);
		return newStack;
	}

	@Override
	public ItemStack initFromCorrectness(double correctness) {
		ItemStack result = new ItemStack(this,1);
		if (correctness < 0.7){
			result.stackSize = 0;
		}
		return result;
	}
}
