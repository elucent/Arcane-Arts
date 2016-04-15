package elucent.aa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer.EnumStatus;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class aaEnergyRay extends Entity {
	public aaEnergyRay(World world) {
		super(world);
	}

	public double velX, velY, velZ, baseDamage = 0;
	public TileEntity initial = null;
	aaElement element = null;
	Random random = new Random();
	
	public aaEnergyRay init(double x, double y, double z, double xVel, double yVel, double zVel, aaElement element, double baseDamage, TileEntity te){
		this.setPosition(x+xVel, y+yVel, z+zVel);
		this.element = element;
		this.velX = xVel;
		this.velY = yVel;
		this.velZ = zVel;
		initial = te;
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
		
		boolean spawnParticles = true;
		
		for (double i = 0; i < 160 && !didStrike; i ++){
			posX += velX*0.25;
			posY += velY*0.25;
			posZ += velZ*0.25;
			for (double j = 0; j < 1; j ++){
				arcaneArts.proxy.spawnGlowFX(getEntityWorld(), posX, posY, posZ, colR, colG, colB);
			}
			
			BlockPos testPos = new BlockPos(Math.floor(posX+velX*0.25),Math.floor(posY+velY*0.25),Math.floor(posZ+velZ*0.25));
			if (this.getEntityWorld().getTileEntity(testPos) != null){
				if (this.getEntityWorld().getTileEntity(testPos) instanceof tileEnergyContainer && this.getEntityWorld().getTileEntity(testPos).getPos() != (initial.getPos())){
					tileEnergyContainer tec = ((tileEnergyContainer)this.getEntityWorld().getTileEntity(testPos));
					tec.setElementValue(this.element.name, tec.getElementValue(this.element.name)+this.baseDamage);
					this.getEntityWorld().getTileEntity(testPos).markDirty();
					didStrike = true;
					spawnParticles = false;
				}
			}
			else if (this.getEntityWorld().getBlockState(testPos).getBlock().isOpaqueCube()){
				didStrike = true;
			}
			
			if (didStrike){
				if (spawnParticles){
					for (int j = 0; j < 10; j ++){
						double dx, dy, dz = 0;
						double xDir = random.nextFloat()*2.0*Math.PI;
						double yDir = random.nextFloat()*2.0*Math.PI;
						dx = 0.5*Math.sin(xDir)*Math.cos(yDir);
						dy = 0.5*Math.sin(yDir);
						dz = 0.5*Math.cos(xDir)*Math.cos(yDir);
						
						for (double l = 0; l < random.nextInt(10); l ++){
							double coeff = 2.0*((l/9.0)-0.5);
							arcaneArts.proxy.spawnGlowFX(getEntityWorld(), posX+coeff*dx, posY+coeff*dy, posZ+coeff*dz, colR, colG, colB);
						}
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
				arcaneArts.proxy.spawnGlowFX(getEntityWorld(), posX+coeff*dx, posY+coeff*dy, posZ+coeff*dz, colR, colG, colB);
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
