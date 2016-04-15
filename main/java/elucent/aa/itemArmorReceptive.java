package elucent.aa;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class itemArmorReceptive extends ItemArmor {
	String model = "";
	public itemArmorReceptive(String model, ArmorMaterial material, int renderIndex, int armorType) {
		super(material, renderIndex, armorType);
		this.model = model;
		this.setUnlocalizedName(model);
		this.setRegistryName(model);
		setCreativeTab(arcaneArts.tab);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("arcanearts:"+model,"inventory"));
	}
}
