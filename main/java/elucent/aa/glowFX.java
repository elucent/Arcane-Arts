
package elucent.aa;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class glowFX extends EntityFX {
	Random random = new Random();
	public double colorR = 0;
	public double colorG = 0;
	public double colorB = 0;
	public int lifetime = 20;
	public ResourceLocation texture = new ResourceLocation("arcanearts:entity/magicParticle");
	protected glowFX(World worldIn, double x, double y, double z, double r, double g, double b) {
		super(worldIn, x,y,z,1,1,1);
		this.colorR = r;
		this.colorG = g;
		this.colorB = b;
		this.setRBGColorF(1, 1, 1);
		this.motionX = 0;
		this.motionY = 0;
		this.particleMaxAge = 10;
		this.motionZ = 0;
	    TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
		this.setParticleIcon(sprite);
	}
	
	@Override
	public int getFXLayer(){
		return 1;
	}
	
	@Override
	public float getAlpha(){
		return 0.99F;
	}
	
	@Override
	public void onUpdate(){
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		if (random.nextInt(4) >= 2){
			this.particleAge ++;
		}
		if (this.particleAge > this.particleMaxAge){
			getEntityWorld().removeEntity(this);
			this.kill();
		}
		float lifeCoeff = ((float)this.particleMaxAge-(float)this.particleAge)/(float)this.particleMaxAge;
		this.particleRed = (float)colorR*(1.0f-lifeCoeff)+lifeCoeff;
		this.particleGreen = (float)colorG*(1.0f-lifeCoeff)+lifeCoeff;
		this.particleBlue = (float)colorB*(1.0f-lifeCoeff)+lifeCoeff;
		this.particleAlpha = lifeCoeff;
	}
	
	@Override
	public void onEntityUpdate(){
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		if (random.nextInt(4) >= 2){
			lifetime --;
		}
		if (lifetime == 0){
			getEntityWorld().removeEntity(this);
			this.kill();
		}
	}
}
