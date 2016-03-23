package elucent.aa;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class aaItemManager {
	public static Item regenerativeMetal, swordRegenerative, shovelRegenerative, pickaxeRegenerative, hoeRegenerative, axeRegenerative, scarletStone, starfireGun, beamPistol, invigoratingUrn, invigoratingUrn2, vial, dainsleif, laevateinn, crissaegrim, ichor, itemMagicChalk, compoundMatter, itemAlchemyFlask, itemMaterial, debugWand, swordReceptive, pickaxeReceptive, axeReceptive, shovelReceptive, hoeReceptive, itemLuminousShield, forgeHammer, mjolnir;
	public static Block advancedFormatorBlock, simpleFormatorBlock, containerDrainBlock, magicRune, crucibleBlock, earthInfuserBlock, waterInfuserBlock, airInfuserBlock, lightInfuserBlock, voidInfuserBlock, arcaneFocus, reducerBlock, diffuserBlock, fireInfuserBlock, vesselBlock, arcaneForgeBlock;
	
	public static ToolMaterial materialReceptiveMetal = EnumHelper.addToolMaterial("receptiveMetal", 3, 1063, 8.0f, 3, 30);
	public static ToolMaterial materialMjolnir = EnumHelper.addToolMaterial("mjolnir", 4, 861, 13.0f, 9, 20);
	public static ToolMaterial materialCrissaegrim = EnumHelper.addToolMaterial("crissaegrim", 4, 536, 11.0f, 7, 30);
	public static ToolMaterial materialLaevateinn = EnumHelper.addToolMaterial("laevateinn", 4, 768, 12.0f, 8, 24);
	public static ToolMaterial materialDainsleif = EnumHelper.addToolMaterial("dainsleif", 4, 662, 10.0f, 6, 10);
	public static ToolMaterial materialScarlet = EnumHelper.addToolMaterial("scarlet", 0, 64, 0, 0, 0);
	public static ToolMaterial materialRegenerativeMetal = EnumHelper.addToolMaterial("regenerativeMetal",4, 194, 10.0f, 4, 20);
	
	
	public static void initOre(){
		OreDictionary.registerOre("dustRedstone", scarletStone);
		OreDictionary.registerOre("dustGlowstone", scarletStone);
		OreDictionary.registerOre("gemQuartz", scarletStone);
		OreDictionary.registerOre("gemLapis", scarletStone);
		OreDictionary.registerOre("slimeball", scarletStone);
	}
	
	public static void createItems(){
		GameRegistry.registerItem(starfireGun = new starfireGun());
		GameRegistry.registerItem(scarletStone = new scarletStone());
		GameRegistry.registerItem(beamPistol = new beamPistol());
		GameRegistry.registerItem(regenerativeMetal = new regenerativeMetal());
		GameRegistry.registerItem(swordRegenerative = new swordRegenerative());
		GameRegistry.registerItem(pickaxeRegenerative = new pickaxeRegenerative());
		GameRegistry.registerItem(axeRegenerative = new axeRegenerative());
		GameRegistry.registerItem(shovelRegenerative = new shovelRegenerative());
		GameRegistry.registerItem(hoeRegenerative = new hoeRegenerative());
		GameRegistry.registerItem(vial = new vial());
		GameRegistry.registerItem(invigoratingUrn = new invigoratingUrn());
		GameRegistry.registerItem(invigoratingUrn2 = new invigoratingUrn2());
		GameRegistry.registerItem(laevateinn = new laevateinn());
		GameRegistry.registerItem(dainsleif = new dainsleif());
		GameRegistry.registerItem(itemMaterial = new itemMaterial());
		GameRegistry.registerItem(compoundMatter = new compoundMatter());
		GameRegistry.registerItem(debugWand = new basicItem("debugWand",arcaneArts.tab),"debugWand");
		GameRegistry.registerItem(forgeHammer = new basicItem("forgeHammer",arcaneArts.tab).setMaxStackSize(1),"forgeHammer");
		GameRegistry.registerItem(itemMagicChalk = new itemMagicChalk(),"itemMagicChalk");
		GameRegistry.registerItem(itemAlchemyFlask = new itemAlchemyFlask(),"itemAlchemyFlask");
		GameRegistry.registerItem(swordReceptive = new swordReceptive());
		GameRegistry.registerItem(pickaxeReceptive = new pickaxeReceptive());
		GameRegistry.registerItem(axeReceptive = new axeReceptive());
		GameRegistry.registerItem(shovelReceptive = new shovelReceptive());
		GameRegistry.registerItem(hoeReceptive = new hoeReceptive());
		GameRegistry.registerItem(itemLuminousShield = new itemLuminousShield());
		GameRegistry.registerItem(mjolnir = new mjolnir());
		GameRegistry.registerItem(ichor = new ichor());
		GameRegistry.registerItem(crissaegrim = new crissaegrim());
		GameRegistry.registerBlock(magicRune = new magicRune(),"magicRune");
		GameRegistry.registerBlock(simpleFormatorBlock = new simpleFormatorBlock());
		GameRegistry.registerBlock(advancedFormatorBlock = new advancedFormatorBlock());
		GameRegistry.registerBlock(arcaneFocus = new arcaneFocusBlock(),"arcaneFocus");
		GameRegistry.registerBlock(reducerBlock = new reducerBlock(),"reducerBlock");
		GameRegistry.registerBlock(diffuserBlock = new diffuserBlock(),"diffuserBlock");
		GameRegistry.registerBlock(fireInfuserBlock = new fireInfuserBlock(),"fireInfuserBlock");
		GameRegistry.registerBlock(earthInfuserBlock = new earthInfuserBlock(),"earthInfuserBlock");
		GameRegistry.registerBlock(waterInfuserBlock = new waterInfuserBlock(),"waterInfuserBlock");
		GameRegistry.registerBlock(airInfuserBlock = new airInfuserBlock(),"airInfuserBlock");
		GameRegistry.registerBlock(lightInfuserBlock = new lightInfuserBlock(),"lightInfuserBlock");
		GameRegistry.registerBlock(voidInfuserBlock = new voidInfuserBlock(),"voidInfuserBlock");
		GameRegistry.registerBlock(vesselBlock = new vesselBlock(), "vesselBlock");
		GameRegistry.registerBlock(crucibleBlock = new crucibleBlock(), "crucibleBlock");
		GameRegistry.registerBlock(arcaneForgeBlock = new arcaneForgeBlock(), "arcaneForgeBlock");
		GameRegistry.registerBlock(containerDrainBlock = new containerDrainBlock(), "containerDrainBlock");
		GameRegistry.registerTileEntity(arcaneFocusEntity.class, "arcaneFocusEntity");
		GameRegistry.registerTileEntity(reducerEntity.class, "reducerEntity");
		GameRegistry.registerTileEntity(diffuserEntity.class, "diffuserEntity");
		GameRegistry.registerTileEntity(fireInfuserEntity.class, "fireInfuserEntity");
		GameRegistry.registerTileEntity(earthInfuserEntity.class, "earthInfuserEntity");
		GameRegistry.registerTileEntity(waterInfuserEntity.class, "waterInfuserEntity");
		GameRegistry.registerTileEntity(airInfuserEntity.class, "airInfuserEntity");
		GameRegistry.registerTileEntity(lightInfuserEntity.class, "lightInfuserEntity");
		GameRegistry.registerTileEntity(voidInfuserEntity.class, "voidInfuserEntity");
		GameRegistry.registerTileEntity(vesselEntity.class, "vesselEntity");
		GameRegistry.registerTileEntity(crucibleEntity.class, "crucibleEntity");
		GameRegistry.registerTileEntity(arcaneForgeEntity.class, "arcaneForgeEntity");
		GameRegistry.registerTileEntity(containerDrainEntity.class, "containerDrainEntity");
		GameRegistry.registerTileEntity(simpleFormatorEntity.class, "simpleFormatorEntity");
		GameRegistry.registerTileEntity(advancedFormatorEntity.class, "advancedFormatorEntity");
		materialReceptiveMetal.setRepairItem(new ItemStack(itemMaterial,1,15));
		materialRegenerativeMetal.setRepairItem(new ItemStack(regenerativeMetal,1,15));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemRenderers(){
		ClientRegistry.bindTileEntitySpecialRenderer(arcaneFocusEntity.class, new arcaneFocusRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(fireInfuserEntity.class, new infuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(earthInfuserEntity.class, new infuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(waterInfuserEntity.class, new infuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(airInfuserEntity.class, new infuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(lightInfuserEntity.class, new infuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(voidInfuserEntity.class, new infuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(vesselEntity.class, new vesselRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(crucibleEntity.class, new crucibleRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(arcaneForgeEntity.class, new arcaneForgeRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(containerDrainEntity.class, new containerDrainRenderer());
		((vial)vial).initModel();
		((crissaegrim)crissaegrim).initModel();
		((dainsleif)dainsleif).initModel();
		((itemMaterial)itemMaterial).initModel();
		((compoundMatter)compoundMatter).initModel();
		((basicItem)debugWand).initModel("debugWand");
		((basicItem)forgeHammer).initModel("forgeHammer");
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
		((pickaxeReceptive)pickaxeReceptive).initModel();
		((axeReceptive)axeReceptive).initModel();
		((shovelReceptive)shovelReceptive).initModel();
		((hoeReceptive)hoeReceptive).initModel();
		((itemLuminousShield)itemLuminousShield).initModel();
		((vesselBlock)vesselBlock).initModel();
		((crucibleBlock)crucibleBlock).initModel();
		((simpleFormatorBlock)simpleFormatorBlock).initModel();
		((advancedFormatorBlock)advancedFormatorBlock).initModel();
		((arcaneForgeBlock)arcaneForgeBlock).initModel();
		((containerDrainBlock)containerDrainBlock).initModel();
		((mjolnir)mjolnir).initModel();
		((laevateinn)laevateinn).initModel();
		((ichor)ichor).initModel();
		((invigoratingUrn)invigoratingUrn).initModel();
		((invigoratingUrn2)invigoratingUrn2).initModel();
		((beamPistol)beamPistol).initModel();
		((starfireGun)starfireGun).initModel();
		((scarletStone)scarletStone).initModel();
		((regenerativeMetal)regenerativeMetal).initModel();
		((swordRegenerative)swordRegenerative).initModel();
		((pickaxeRegenerative)pickaxeRegenerative).initModel();
		((axeRegenerative)axeRegenerative).initModel();
		((shovelRegenerative)shovelRegenerative).initModel();
		((hoeRegenerative)hoeRegenerative).initModel();
	}
}
