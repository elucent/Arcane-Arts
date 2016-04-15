package elucent.aa;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class itemArmorRegenerative extends ItemArmor {
	String model = "";
	Random random = new Random();
	public itemArmorRegenerative(String model, ArmorMaterial material, int renderIndex, int armorType) {
		super(material, renderIndex, armorType);
		this.model = model;
		this.setUnlocalizedName(model);
		this.setRegistryName(model);
		setCreativeTab(arcaneArts.tab);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		if (stack.getItemDamage() > 0){
			if (random.nextInt(40) == 20){
				stack.setItemDamage(stack.getItemDamage()-1);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:"+model,"inventory"));
	}
}
