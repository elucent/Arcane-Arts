package elucent.aa;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class aaEntityManager {
	public static void registerEntities(){
		EntityRegistry.registerModEntity(aaElementProjectile.class, "projectile", 0, arcaneArts.instance, 128, 20, true);
		EntityRegistry.registerModEntity(aaElementRay.class, "ray", 0, arcaneArts.instance, 128, 20, true);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerEntityRenderers(){
		RenderingRegistry.registerEntityRenderingHandler(aaElementProjectile.class, new aaElementProjectileRenderer(Minecraft.getMinecraft().getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(aaElementRay.class, new aaElementProjectileRenderer(Minecraft.getMinecraft().getRenderManager()));
	}
}
