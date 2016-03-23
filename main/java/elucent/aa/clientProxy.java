package elucent.aa;

import net.minecraft.entity.projectile.EntityPotion;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class clientProxy extends commonProxy {
	
	public void preInit(FMLPreInitializationEvent event){
		super.preInit(event);
	}
	
	public void init(FMLInitializationEvent event){
		super.init(event);
		aaItemManager.registerItemRenderers();
		aaEntityManager.registerEntityRenderers();
	}
	
	public void postInit(FMLPostInitializationEvent event){
		super.postInit(event);
	}
}
