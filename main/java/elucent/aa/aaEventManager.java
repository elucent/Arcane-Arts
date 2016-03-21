package elucent.aa;

import java.util.ArrayList;
import java.util.Optional;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class aaEventManager {
	@SubscribeEvent
	public void onEntityHurt(LivingHurtEvent e){
		if (e.entityLiving instanceof EntityPlayer){
			EntityPlayer p = (EntityPlayer)e.entityLiving;
			if (p.inventory.hasItem(aaItemManager.mjolnir)){
				if (e.source == DamageSource.lightningBolt){
					e.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerDrink(PlayerUseItemEvent.Start event){
		if (event.item.getItem() == Items.milk_bucket){
			event.setCanceled(true);
		}
	}
}
