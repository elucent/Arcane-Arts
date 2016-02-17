package elucent.aa;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class basicItem extends Item {
	public basicItem(String name, CreativeTabs tab){
		super();
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(arcaneArts.tab);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(String name){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
