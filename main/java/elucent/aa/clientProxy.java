package elucent.aa;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.world.World;
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
	
	@Override
	public void spawnGlowFX(World world, double x, double y, double z, double r, double g, double b){
		glowFX fx = new glowFX(world, x, y, z, r, g, b);
		Minecraft.getMinecraft().effectRenderer.addEffect(fx);
	}
}
