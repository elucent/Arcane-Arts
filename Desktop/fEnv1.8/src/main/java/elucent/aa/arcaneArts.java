package elucent.aa;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = arcaneArts.MODID, version = arcaneArts.VERSION, name = arcaneArts.MODNAME)
public class arcaneArts {
	public static final String MODID = "arcanearts";
	public static final String VERSION = "0.1";
	public static final String MODNAME = "Arcane Arts";

    @Instance
    public static arcaneArts instance = new arcaneArts();
	
	@SidedProxy(clientSide="elucent.aa.clientProxy", serverSide="elucent.aa.serverProxy")
	public static commonProxy proxy;
	
	aaElementManager globalElementManager = new aaElementManager();
	
	public static CreativeTabs tab = new CreativeTabs("arcaneArts") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem(){
			return Items.coal;
		}
	};
	
	public static Vec3i getPosFromSide(EnumFacing side){
		if (side == EnumFacing.UP){
			return new Vec3i(0,1,0);
		}
		if (side == EnumFacing.DOWN){
			return new Vec3i(0,-1,0);
		}
		if (side == EnumFacing.WEST){
			return new Vec3i(-1,0,0);
		}
		if (side == EnumFacing.EAST){
			return new Vec3i(1,0,0);
		}
		if (side == EnumFacing.NORTH){
			return new Vec3i(0,0,-1);
		}
		if (side == EnumFacing.SOUTH){
			return new Vec3i(0,0,1);
		}
		return null;
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
	    aaItemManager.createItems();
	    this.proxy.preInit(e);
	    globalElementManager.initRecipes();
	    aaRecipeManager.createRecipes();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	    this.proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	    this.proxy.postInit(e);
	}
}
