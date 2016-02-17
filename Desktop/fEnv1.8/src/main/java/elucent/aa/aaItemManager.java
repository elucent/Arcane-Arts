package elucent.aa;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class aaItemManager {
	public static Item itemMagicChalk, itemAlchemyFlask, itemMaterial, debugWand, swordReceptive;
	public static Block magicRune, earthInfuserBlock, waterInfuserBlock, airInfuserBlock, lightInfuserBlock, voidInfuserBlock, arcaneFocus, reducerBlock, diffuserBlock, fireInfuserBlock;
	
	public static ToolMaterial materialReceptiveMetal = EnumHelper.addToolMaterial("receptiveMetal", 3, 1063, 3.0f, 4, 30);
	
	public static void createItems(){
		GameRegistry.registerItem(itemMaterial = new itemMaterial());
		GameRegistry.registerItem(debugWand = new basicItem("debugWand",arcaneArts.tab),"debugWand");
		GameRegistry.registerItem(itemMagicChalk = new itemMagicChalk(),"itemMagicChalk");
		GameRegistry.registerItem(itemAlchemyFlask = new itemAlchemyFlask(),"itemAlchemyFlask");
		GameRegistry.registerItem(swordReceptive = new swordReceptive());
		GameRegistry.registerBlock(magicRune = new magicRune(),"magicRune");
		GameRegistry.registerBlock(arcaneFocus = new arcaneFocusBlock(),"arcaneFocus");
		GameRegistry.registerBlock(reducerBlock = new reducerBlock(),"reducerBlock");
		GameRegistry.registerBlock(diffuserBlock = new diffuserBlock(),"diffuserBlock");
		GameRegistry.registerBlock(fireInfuserBlock = new fireInfuserBlock(),"fireInfuserBlock");
		GameRegistry.registerBlock(earthInfuserBlock = new earthInfuserBlock(),"earthInfuserBlock");
		GameRegistry.registerBlock(waterInfuserBlock = new waterInfuserBlock(),"waterInfuserBlock");
		GameRegistry.registerBlock(airInfuserBlock = new airInfuserBlock(),"airInfuserBlock");
		GameRegistry.registerBlock(lightInfuserBlock = new lightInfuserBlock(),"lightInfuserBlock");
		GameRegistry.registerBlock(voidInfuserBlock = new voidInfuserBlock(),"voidInfuserBlock");
		GameRegistry.registerTileEntity(arcaneFocusEntity.class, "arcaneFocusEntity");
		GameRegistry.registerTileEntity(reducerEntity.class, "reducerEntity");
		GameRegistry.registerTileEntity(diffuserEntity.class, "diffuserEntity");
		GameRegistry.registerTileEntity(fireInfuserEntity.class, "fireInfuserEntity");
		GameRegistry.registerTileEntity(earthInfuserEntity.class, "earthInfuserEntity");
		GameRegistry.registerTileEntity(waterInfuserEntity.class, "waterInfuserEntity");
		GameRegistry.registerTileEntity(airInfuserEntity.class, "airInfuserEntity");
		GameRegistry.registerTileEntity(lightInfuserEntity.class, "lightInfuserEntity");
		GameRegistry.registerTileEntity(voidInfuserEntity.class, "voidInfuserEntity");
		ClientRegistry.bindTileEntitySpecialRenderer(arcaneFocusEntity.class, new arcaneFocusRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(fireInfuserEntity.class, new infuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(earthInfuserEntity.class, new infuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(waterInfuserEntity.class, new infuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(airInfuserEntity.class, new infuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(lightInfuserEntity.class, new infuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(voidInfuserEntity.class, new infuserRenderer());
	}
	public static void registerItemRenderers(){
		((itemMaterial)itemMaterial).initModel();
		((basicItem)debugWand).initModel("debugWand");
		((itemMagicChalk)itemMagicChalk).initModel("itemMagicChalk");
		((magicRune)magicRune).initModel();
		((arcaneFocusBlock)arcaneFocus).initModel();
		((reducerBlock)reducerBlock).initModel();
		((diffuserBlock)diffuserBlock).initModel();
		((fireInfuserBlock)fireInfuserBlock).initModel();
		((earthInfuserBlock)earthInfuserBlock).initModel();
		((waterInfuserBlock)waterInfuserBlock).initModel();
		((airInfuserBlock)airInfuserBlock).initModel();
		((lightInfuserBlock)lightInfuserBlock).initModel();
		((voidInfuserBlock)voidInfuserBlock).initModel();
		((itemAlchemyFlask)itemAlchemyFlask).initModel();
		((swordReceptive)swordReceptive).initModel();
	}
}
