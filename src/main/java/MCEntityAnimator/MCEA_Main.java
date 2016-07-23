package MCEntityAnimator;

import java.io.File;

import MCEntityAnimator.block.BlockBase;
import MCEntityAnimator.block.BlockGrid;
import MCEntityAnimator.distribution.DataHandler;
import MCEntityAnimator.item.ItemWeapon;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "MCEA")
public class MCEA_Main
{
	
	public static final String homePath = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
	public static final String animationPath = homePath + "/animation";
	public static final String version = "3.7.4.4";
	public static final DataHandler dataHandler = new DataHandler();

	
	@Mod.Instance("MCEA")
	public static MCEA_Main instance;

	@SidedProxy(serverSide = "MCEntityAnimator.CommonProxy", clientSide = "MCEntityAnimator.ClientProxy")
	public static CommonProxy proxy;

    public static final Item Dagger = new ItemWeapon().setUnlocalizedName("knife").setFull3D().setCreativeTab(CreativeTabs.tabCombat);
    public static final Item Crossbow = new ItemWeapon().setUnlocalizedName("crossbow").setFull3D().setCreativeTab(CreativeTabs.tabCombat);
    public static final Item Bayonet = new ItemWeapon().setUnlocalizedName("bayonet").setFull3D().setCreativeTab(CreativeTabs.tabCombat);
    public static final Item Musket = new ItemWeapon().setUnlocalizedName("musket").setFull3D().setCreativeTab(CreativeTabs.tabCombat);
    public static final Item Blunderbuss = new ItemWeapon().setUnlocalizedName("blunderbuss").setCreativeTab(CreativeTabs.tabCombat);
    public static final Item Blowgun = new ItemWeapon().setUnlocalizedName("blowgun").setFull3D().setCreativeTab(CreativeTabs.tabCombat);

    public static final Item Battleaxe = new ItemWeapon().setUnlocalizedName("battleaxe").setFull3D().setCreativeTab(CreativeTabs.tabCombat);
    public static final Item Halberd= new ItemWeapon().setUnlocalizedName("halberd").setFull3D().setCreativeTab(CreativeTabs.tabCombat);
    public static final Item Katana = new ItemWeapon().setUnlocalizedName("katana").setFull3D().setCreativeTab(CreativeTabs.tabCombat);
    public static final Item Spear = new ItemWeapon().setUnlocalizedName("spear").setFull3D().setCreativeTab(CreativeTabs.tabCombat);
    
    public static final Block Base = new BlockBase();
    public static final Block Grid = new BlockGrid();
    		
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{		
		instance = this;		
		proxy.init();
		proxy.registerBlocks();
		proxy.registerItems();

        MinecraftForge.EVENT_BUS.register(new EventHandler());
		
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
        {
            FMLCommonHandler.instance().bus().register(new KeyHandler());
        }
	}
	
	public static File getEntityAnimationFolder(String entityName)
	{
		File folder = new File(animationPath + "/" + entityName);
		folder.mkdirs();
		return folder;
	}

}
