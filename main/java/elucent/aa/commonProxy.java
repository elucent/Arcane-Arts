package elucent.aa;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class commonProxy {
	
	public void preInit(FMLPreInitializationEvent event){
		
	}
	
	public void init(FMLInitializationEvent event){
		aaEntityManager.registerEntities();
	}
	
	public void postInit(FMLPostInitializationEvent event){
		NetworkRegistry.INSTANCE.registerGuiHandler(arcaneArts.instance, new aaGuiHandler());
	}

	public void spawnGlowFX(World world, double x, double y, double z, double r, double g, double b) {
		
	}
}
