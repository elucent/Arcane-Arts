package elucent.aa;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class aaEntityManager {
	public static void registerEntities(){
		EntityRegistry.registerModEntity(aaElementProjectile.class, "projectile", 0, arcaneArts.instance, 128, 20, true);
		RenderingRegistry.registerEntityRenderingHandler(aaElementProjectile.class, new aaElementProjectileRenderer(Minecraft.getMinecraft().getRenderManager()));
		EntityRegistry.registerModEntity(aaElementRay.class, "ray", 0, arcaneArts.instance, 128, 20, true);
		RenderingRegistry.registerEntityRenderingHandler(aaElementRay.class, new aaElementProjectileRenderer(Minecraft.getMinecraft().getRenderManager()));
	}
}
