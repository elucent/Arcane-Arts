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

public class ichor extends Item implements itemVariableResult{
	public ichor(){
		super();
		setUnlocalizedName("ichor");
		setRegistryName("ichor");
		setCreativeTab(arcaneArts.tab);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		tooltip.add("The blood of the gods, and the key to their power.");
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean selected){
		Random random = new Random();
		if (random.nextInt(5000) == 2500){
			((EntityLivingBase)player).addPotionEffect(new PotionEffect(Potion.regeneration.id,160,0));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:ichor","inventory"));
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
