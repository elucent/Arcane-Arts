package elucent.aa;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class regenerativeMetal extends Item implements itemVariableResult{
	public regenerativeMetal(){
		super();
		setUnlocalizedName("regenerativeMetal");
		setRegistryName("regenerativeMetal");
		setCreativeTab(arcaneArts.tab);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		tooltip.add("It seems solid, but any dent you make on it will heal.");
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:regenerativeMetal","inventory"));
	}

	@Override
	public double getCorrectness(ItemStack stack) {
		return 1.0;
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
