package elucent.aa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer.EnumStatus;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class aaElementRay extends Entity {
	public aaElementRay(World world) {
		super(world);
	}

	public double velX, velY, velZ, baseDamage = 0;
	aaElement element = null;
	Random random = new Random();
	
	public aaElementRay init(double x, double y, double z, double xVel, double yVel, double zVel, aaElement element, double baseDamage){
		this.setPosition(x+xVel, y+yVel, z+zVel);
		this.element = element;
		this.velX = xVel;
		this.velY = yVel;
		this.velZ = zVel;
		this.baseDamage = baseDamage;
		return this;
	}

	@Override
	public void entityInit() {
		
	}
	
	@Override
	public void onUpdate(){
		double colR = 0;
		double colG = 0;
		double colB = 0;
		boolean didStrike = false;
		if (element != null){
			colR = element.vColor.getX()/255.0;
			colG = element.vColor.getY()/255.0;
			colB = element.vColor.getZ()/255.0;
		}
		for (double i = 0; i < 40 && !didStrike; i ++){
			posX += velX;
			posY += velY;
			posZ += velZ;
			for (double j = 0; j < 4; j ++){
				getEntityWorld().spawnParticle(EnumParticleTypes.REDSTONE, posX+(j/4.0)*velX, posY+(j/4.0)*velY, posZ+(j/4.0)*velZ, colR-1.0, colG, colB, 0);
			}
			AxisAlignedBB bounds = AxisAlignedBB.fromBounds(posX-0.5, posY-0.5, posZ-0.5, posX+0.5, posY+0.5, posZ+0.5);
			List<EntityLivingBase> nearbyEntities = getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, bounds);
			for (int k = 0; k < nearbyEntities.size() && !didStrike; k ++){
				if (!nearbyEntities.get(k).isDead){
					nearbyEntities.get(k).attackEntityFrom(element.getDamageSource(), (float) baseDamage);
					didStrike = true;
				}
			}
			
			if (this.isEntityInsideOpaqueBlock()){
				didStrike = true;
			}
			
			if (didStrike){
				for (int j = 0; j < 10; j ++){
					double dx, dy, dz = 0;
					double xDir = random.nextFloat()*2.0*Math.PI;
					double yDir = random.nextFloat()*2.0*Math.PI;
					dx = 1.5*Math.sin(xDir)*Math.cos(yDir);
					dy = 1.5*Math.sin(yDir);
					dz = 1.5*Math.cos(xDir)*Math.cos(yDir);
					
					for (double l = 0; l < random.nextInt(10); l ++){
						double coeff = 2.0*((l/9.0)-0.5);
						this.getEntityWorld().spawnParticle(EnumParticleTypes.REDSTONE, posX+coeff*dx, posY+coeff*dy, posZ+coeff*dz, colR-1.0, colG, colB, 0);
					}
				}
				this.getEntityWorld().removeEntity(this);
			}
		}
		for (int j = 0; j < 10; j ++){
			double dx, dy, dz = 0;
			double xDir = random.nextFloat()*2.0*Math.PI;
			double yDir = random.nextFloat()*2.0*Math.PI;
			dx = 1.5*Math.sin(xDir)*Math.cos(yDir);
			dy = 1.5*Math.sin(yDir);
			dz = 1.5*Math.cos(xDir)*Math.cos(yDir);
			
			for (double l = 0; l < random.nextInt(10); l ++){
				double coeff = 2.0*((l/9.0)-0.5);
				this.getEntityWorld().spawnParticle(EnumParticleTypes.REDSTONE, posX+coeff*dx, posY+coeff*dy, posZ+coeff*dz, colR-1.0, colG, colB, 0);
			}
		}
		this.getEntityWorld().removeEntity(this);
	}
	

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		if (tag.hasKey("velX")){
			velX = tag.getDouble("velX");
		}
		if (tag.hasKey("velY")){
			velY = tag.getDouble("velY");
		}
		if (tag.hasKey("velZ")){
			velZ = tag.getDouble("velZ");
		}
		if (tag.hasKey("baseDamage")){
			baseDamage = tag.getDouble("baseDamage");
		}
		if (tag.hasKey("elementName")){
			element = aaElementManager.getElementFromString(tag.getString("elementName"));
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setDouble("velX", velX);
		tagCompound.setDouble("velY", velY);
		tagCompound.setDouble("velZ", velZ);
		tagCompound.setDouble("baseDamage", baseDamage);
		tagCompound.setString("elementName", element.name);
	}

}
