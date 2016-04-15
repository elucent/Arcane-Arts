package elucent.aa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer.EnumStatus;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class aaElementProjectile extends Entity {
	public double velX, velY, velZ, baseDamage, size = 0;
	aaElement element = null;
	public int lifetime = 120;
	Random random = new Random();
	public ArrayList<Vec3> pastPositions = new ArrayList<Vec3>();
	long shooterId = 0;
	
	public aaElementProjectile(World world) {
		super(world);
	}
	
	public aaElementProjectile init(double x, double y, double z, double xVel, double yVel, double zVel, aaElement element, double size, double baseDamage, int lifetime, Entity shooter){
		this.setPosition(x, y, z);
		this.element = element;
		this.velX = xVel;
		this.velY = yVel;
		this.velZ = zVel;
		this.size = size;
		this.baseDamage = baseDamage;
		this.lifetime = lifetime;
		shooterId = shooter.getUniqueID().getMostSignificantBits();
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
		if (element != null){
			colR = element.vColor.getX()/255.0;
			colG = element.vColor.getY()/255.0;
			colB = element.vColor.getZ()/255.0;
		}
		lifetime --;
		if (lifetime < 0){
			for (int j = 0; j < 10; j ++){
				double dx, dy, dz = 0;
				double xDir = random.nextFloat()*2.0*Math.PI;
				double yDir = random.nextFloat()*2.0*Math.PI;
				dx = 2.5*size*Math.sin(xDir)*Math.cos(yDir);
				dy = 2.5*size*Math.sin(yDir);
				dz = 2.5*size*Math.cos(xDir)*Math.cos(yDir);
				
				for (int i = 0; i < random.nextInt(10); i ++){
					double coeff = 2.0*((((double)i)/9.0)-0.5);
					this.getEntityWorld().spawnParticle(EnumParticleTypes.REDSTONE, posX+coeff*dx, posY+coeff*dy, posZ+coeff*dz, colR-1.0, colG, colB, 0);
				}
			}
			this.getEntityWorld().removeEntity(this);
		}
		boolean didStrike = false;
		for (double k = 0; k < 4; k ++){
			posX = posX+(k/4.0)*velX;
			posY = posY+(k/4.0)*velY;
			posZ = posZ+(k/4.0)*velZ;
			for (int i = 0; i < size*8.0; i ++){
				getEntityWorld().spawnParticle(EnumParticleTypes.REDSTONE, posX+size*0.5*random.nextFloat()-0.5, posY+size*0.5*random.nextFloat()-0.5, posZ+size*0.5*random.nextFloat()-0.5, colR-1.0, colG, colB, 0);
			}
			AxisAlignedBB hitBounds = AxisAlignedBB.fromBounds(posX-size*1.0, posY-size*1.0, posZ-size*1.0, posX+size*1.0, posY+size*1.0, posZ+size*1.0);
			List<EntityLivingBase> entities = this.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, hitBounds);
			for (int i = 0; i < entities.size() && didStrike == false; i ++){
				if (entities.get(i).getUniqueID().getMostSignificantBits() != shooterId){
					if (element != null){
						entities.get(i).attackEntityFrom(element.getDamageSource(), (float) baseDamage);
					}
					didStrike = true;
				}
			}
			AxisAlignedBB bounds = AxisAlignedBB.fromBounds(posX-size*0.125, posY-size*0.125, posZ-size*0.125, posX+size*0.125, posY+size*0.125, posZ+size*0.125);

			if (this.getEntityWorld().getBlockState(new BlockPos(Math.floor(posX+velX*0.25),Math.floor(posY+velY*0.25),Math.floor(posZ+velZ*0.25))).getBlock().isOpaqueCube()){
				didStrike = true;
			}
			
			if (didStrike){
				for (int j = 0; j < 10; j ++){
					double dx, dy, dz = 0;
					double xDir = random.nextFloat()*2.0*Math.PI;
					double yDir = random.nextFloat()*2.0*Math.PI;
					dx = 2.5*size*Math.sin(xDir)*Math.cos(yDir);
					dy = 2.5*size*Math.sin(yDir);
					dz = 2.5*size*Math.cos(xDir)*Math.cos(yDir);
					
					for (int i = 0; i < random.nextInt(10); i ++){
						double coeff = 2.0*((((double)i)/9.0)-0.5);
						this.getEntityWorld().spawnParticle(EnumParticleTypes.REDSTONE, posX+coeff*dx, posY+coeff*dy, posZ+coeff*dz, colR-1.0, colG, colB, 0);
					}
				}
				this.getEntityWorld().removeEntity(this);
			}
		}
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
		if (tag.hasKey("level")){
			size = tag.getDouble("size");
		}
		if (tag.hasKey("elementName")){
			element = aaElementManager.getElementFromString(tag.getString("elementName"));
		}
		if (tag.hasKey("lifetime")){
			lifetime = tag.getInteger("lifetime");
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setDouble("velX", velX);
		tagCompound.setDouble("velY", velY);
		tagCompound.setDouble("velZ", velZ);
		tagCompound.setDouble("baseDamage", baseDamage);
		tagCompound.setDouble("size", size);
		tagCompound.setString("elementName", element.name);
		tagCompound.setInteger("lifetime", lifetime);
	}

}
