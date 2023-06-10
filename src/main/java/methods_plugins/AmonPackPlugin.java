package methods_plugins;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.Element.ElementType;
import com.projectkorra.projectkorra.Element.SubElement;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.storage.SQLite;

import commands.Commands;
import commands.CommandsListener;
import commands.Friends;
import commands.SpellTree;


public class AmonPackPlugin extends JavaPlugin {
	public static Plugin plugin;
	JavaPlugin jp = this;
	public static SQLite sqlite;
    private static Element BladesElement;
    private static Element SmokeElement;
	public FileConfiguration config = this.getConfig();
	static File newConfig;
	private static FileConfiguration newConfigz;
	static List<Location> MineList = new ArrayList<Location>();
	
    @Override
    public void onEnable() {
    	plugin = this;
        getLogger().info("AmonPack włączony");
        CoreAbility.registerPluginAbilities(jp, "abilities");
        this.getServer().getPluginManager().registerEvents(new CollectItem(this), this);
        this.getServer().getPluginManager().registerEvents(new AbilitiesListener(), this);
        this.getServer().getPluginManager().registerEvents(new Kopalnie(), this);
        BladesElement = new SubElement("Blades", Element.CHI, ElementType.BLOCKING, this) {
        };
        SmokeElement = new SubElement("Smoke", Element.FIRE, ElementType.BENDING, this) {
        };
        BladesAbility.CreateSwords();
		Kopalnie.SetMiningItems();
        this.getCommand("reload").setExecutor(new Commands());
        this.getCommand("QuestItems").setExecutor(new Commands());
        this.getCommand("spelltree").setExecutor(new SpellTree());
        Bukkit.getPluginManager().registerEvents(new CommandsListener(), this);
        createconf();
        sqlConnection();
        sqlTableCheck();
        newConfig = new File(getDataFolder(), "newconfig.yml");
        setNewConfigz(YamlConfiguration.loadConfiguration(newConfig));
        saveNewConfig();

		for(String key : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Mining").getKeys(false)) {
			Double X = AmonPackPlugin.getNewConfigz().getDouble("AmonPack.Mining." + key + ".X");
			Double Y = AmonPackPlugin.getNewConfigz().getDouble("AmonPack.Mining." + key + ".Y");
			Double Z = AmonPackPlugin.getNewConfigz().getDouble("AmonPack.Mining." + key + ".Z");
			String World = AmonPackPlugin.getNewConfigz().getString("AmonPack.Mining." + key + ".World");
			Location loc = new Location(Bukkit.getWorld(World), X, Y, Z);
			//Location loc = new Location(Bukkit.getWorld("PodZadaniowy"), 10, 10, 10);
			MineList.add(loc);
		}

    }
    
    public static SQLite mysqllite() {
		return sqlite;
    }
    
    public static void reloadcustom() {
    YamlConfiguration.loadConfiguration(newConfig);
    saveNewConfig();
    }
    
    
    public static void saveNewConfig(){
    	try{
    	if (!getNewConfigz().contains("AmonPack")) {
        getNewConfigz().set("AmonPack.SpellTree.Abilities.fire", new String[]{"FireBlast","SmokeDaggers",});
        getNewConfigz().set("AmonPack.SpellTree.Abilities.air", new String[]{"AirPressure","AirBlast",});
        getNewConfigz().set("AmonPack.SpellTree.Abilities.earth", new String[]{"SandBreath","EarthBlast",});
        getNewConfigz().set("AmonPack.SpellTree.Abilities.chi", new String[]{"QuickStrike","Counter",});
        getNewConfigz().set("AmonPack.SpellTree.Abilities.water", new String[]{"IceArch","Torrent",});

		getNewConfigz().set("AmonPack.Mining.Kopalnia1.X",-95);
		getNewConfigz().set("AmonPack.Mining.Kopalnia1.Y",-57);
		getNewConfigz().set("AmonPack.Mining.Kopalnia1.Z", -97);
		getNewConfigz().set("AmonPack.Mining.Kopalnia1.World", "PodZadaniowy");
        getNewConfigz().set("AmonPack.Mining.Kopalnia1.Loot.Skyrim", 2);
        getNewConfigz().set("AmonPack.Mining.Kopalnia1.Loot.Celestyn", 1);
        getNewConfigz().set("AmonPack.Mining.Kopalnia1.Ores.CRIMSON_HYPHAE", 1);
        getNewConfigz().set("AmonPack.Mining.Kopalnia1.Ores.PRISMARINE", 1);
        getNewConfigz().set("AmonPack.Mining.Kopalnia1.Ores.IRON_ORE", 4);
        getNewConfigz().set("AmonPack.Mining.Kopalnia1.Ores.COAL_ORE", 4);
/*
		getNewConfigz().set("AmonPack.Mining.Kopalnia1.X",-3779);
		getNewConfigz().set("AmonPack.Mining.Kopalnia1.Y",21);
		getNewConfigz().set("AmonPack.Mining.Kopalnia1.Z", 238);
		getNewConfigz().set("AmonPack.Mining.Kopalnia1.World", "AvatarServGlownyNowy");
        getNewConfigz().set("AmonPack.Mining.Kopalnia1.Loot.Skyrim", 2);
        getNewConfigz().set("AmonPack.Mining.Kopalnia1.Loot.Celestyn", 1);
        getNewConfigz().set("AmonPack.Mining.Kopalnia1.Ores.CRIMSON_HYPHAE", 1);
        getNewConfigz().set("AmonPack.Mining.Kopalnia1.Ores.PRISMARINE", 1);
        getNewConfigz().set("AmonPack.Mining.Kopalnia1.Ores.IRON_ORE", 4);
        getNewConfigz().set("AmonPack.Mining.Kopalnia1.Ores.COAL_ORE", 4);

		getNewConfigz().set("AmonPack.Mining.Kopalnia2.X",2880);
		getNewConfigz().set("AmonPack.Mining.Kopalnia2.Y",40);
		getNewConfigz().set("AmonPack.Mining.Kopalnia2.Z", 566);
        getNewConfigz().set("AmonPack.Mining.Kopalnia2.World", "AvatarServGlownyNowy");
		getNewConfigz().set("AmonPack.Mining.Kopalnia2.Loot.Ksymil", 1);
		getNewConfigz().set("AmonPack.Mining.Kopalnia2.Ores.IRON_ORE", 3);
		getNewConfigz().set("AmonPack.Mining.Kopalnia2.Ores.COAL_ORE", 4);
		getNewConfigz().set("AmonPack.Mining.Kopalnia2.Ores.GLOWSTONE", 1);

		getNewConfigz().set("AmonPack.Mining.Kopalnia3.X",-3906);
		getNewConfigz().set("AmonPack.Mining.Kopalnia3.Y",53);
		getNewConfigz().set("AmonPack.Mining.Kopalnia3.Z", 184);
        getNewConfigz().set("AmonPack.Mining.Kopalnia3.World", "AvatarServGlownyNowy");
		getNewConfigz().set("AmonPack.Mining.Kopalnia3.Ores.GRAVEL", 7);
		getNewConfigz().set("AmonPack.Mining.Kopalnia3.Ores.SAND", 8);
		getNewConfigz().set("AmonPack.Mining.Kopalnia3.Ores.CLAY", 3);
		getNewConfigz().set("AmonPack.Mining.Kopalnia3.Ores.DIRT", 2);
*/
		getNewConfigz().set("AmonPack.MiningOresDrops.COAL_ORE", "COAL");
		getNewConfigz().set("AmonPack.MiningOresDrops.IRON_ORE", "RAW_IRON");
		getNewConfigz().set("AmonPack.MiningOresDrops.STONE", "COBBLESTONE");
		getNewConfigz().set("AmonPack.MiningOresDrops.GLOWSTONE", "Ksymil");
		getNewConfigz().set("AmonPack.MiningOresDrops.CRIMSON_HYPHAE", "Skyrim");
		getNewConfigz().set("AmonPack.MiningOresDrops.PRISMARINE", "Celestyn");
		getNewConfigz().set("AmonPack.MiningOresDrops.GRAVEL", "GRAVEL");
		getNewConfigz().set("AmonPack.MiningOresDrops.SAND", "SAND");
		getNewConfigz().set("AmonPack.MiningOresDrops.CLAY", "CLAY_BALL");
		getNewConfigz().set("AmonPack.MiningOresDrops.DIRT", "DIRT");

		getNewConfigz().set("AmonPack.Items.COBBLESTONE.Type", "COBBLESTONE");
		getNewConfigz().set("AmonPack.Items.COAL.Type", "COAL");
		getNewConfigz().set("AmonPack.Items.RAW_IRON.Type", "RAW_IRON");
		getNewConfigz().set("AmonPack.Items.GRAVEL.Type", "GRAVEL");
		getNewConfigz().set("AmonPack.Items.SAND.Type", "SAND");
		getNewConfigz().set("AmonPack.Items.DIRT.Type", "DIRT");
		getNewConfigz().set("AmonPack.Items.CLAY_BALL.Type", "CLAY_BALL");
		getNewConfigz().set("AmonPack.Quests.Ksymil.Type", "HONEYCOMB");
		getNewConfigz().set("AmonPack.Quests.Ksymil.Name", "&6&lKsymil");
		getNewConfigz().set("AmonPack.Quests.Ksymil.Enchantment.DURABILITY.EnchantmentLevel", 5);
		getNewConfigz().set("AmonPack.Quests.Ksymil.Lore.Lore1", "&5Rzadki Minerał");
		getNewConfigz().set("AmonPack.Quests.Celestyn.Type", "TUBE_CORAL");
		getNewConfigz().set("AmonPack.Quests.Celestyn.Name", "&3&lCelestyn");
		getNewConfigz().set("AmonPack.Quests.Celestyn.Enchantment.DURABILITY.EnchantmentLevel", 5);
		getNewConfigz().set("AmonPack.Quests.Celestyn.Lore.Lore1", "&5Rzadki Minerał");
		getNewConfigz().set("AmonPack.Quests.Skyrim.Type", "REDSTONE");
		getNewConfigz().set("AmonPack.Quests.Skyrim.Name", "&4&lSkyrim");
		getNewConfigz().set("AmonPack.Quests.Skyrim.Enchantment.DURABILITY.EnchantmentLevel", 5);
		getNewConfigz().set("AmonPack.Quests.Skyrim.Lore.Lore1", "&5Rzadki Minerał");
		getNewConfigz().set("AmonPack.Quests.Jadeit.Type", "SCUTE");
		getNewConfigz().set("AmonPack.Quests.Jadeit.Name", "&a&lJadeit");
		getNewConfigz().set("AmonPack.Quests.Jadeit.Enchantment.DURABILITY.EnchantmentLevel", 5);
		getNewConfigz().set("AmonPack.Quests.Jadeit.Lore.Lore1", "&5Rzadki Minerał");
		getNewConfigz().set("AmonPack.Quests.Bazalt.Type", "FLINT");
		getNewConfigz().set("AmonPack.Quests.Bazalt.Name", "&8&lBazalt");
		getNewConfigz().set("AmonPack.Quests.Bazalt.Enchantment.DURABILITY.EnchantmentLevel", 5);
		getNewConfigz().set("AmonPack.Quests.Bazalt.Lore.Lore1", "&5Rzadki Minerał");
		getNewConfigz().set("AmonPack.Quests.Meteoryt.Type", "FIRE_CHARGE");
		getNewConfigz().set("AmonPack.Quests.Meteoryt.Name", "&4&lSztabka Meteorytu");
		getNewConfigz().set("AmonPack.Quests.Meteoryt.Enchantment.PROTECTION_FIRE.EnchantmentLevel", 10);
		getNewConfigz().set("AmonPack.Quests.Meteoryt.Lore.Lore1", "&5&nBardzo&5 rzadki Minerał");
		getNewConfigz().set("AmonPack.Quests.Drobniak.Type", "IRON_NUGGET");
		getNewConfigz().set("AmonPack.Quests.Drobniak.Name", "&7&lDrobniak");
		getNewConfigz().set("AmonPack.Quests.Drobniak.Enchantment.DURABILITY.EnchantmentLevel", 3);
		getNewConfigz().set("AmonPack.Quests.Drobniak.Lore.Lore1", "&fWaluta wykorzystywana w handlu na calym swiecie");
    	}
    	getNewConfigz().save(newConfig);
    	}catch(Exception e){
    	e.printStackTrace();
    	}}
    
    public void sqlConnection() {
    	sqlite = new SQLite(plugin.getLogger(),
    	                "AmonPack",
    	                "AmonPackSQL.db",
    	                plugin.getDataFolder().getAbsolutePath());
    	try {
    	sqlite.open();
    	    } catch (Exception e) {
    	        plugin.getLogger().info(e.getMessage());
    	        getPluginLoader().disablePlugin(plugin);
    	    }
    	if (sqlite.open() != null) {
    		getLogger().info("Baza danych połączona!");
    	}
    	}
    
    public void sqlTableCheck() {
    	PreparedStatement ps;
		try {
			ps = mysqllite().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS FriendsTable (Player1 VARCHAR(50))");
	    	ps.executeUpdate();
	    	ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
      /*if(sqlite.tableExists("friends_table")){
      return;
      }else{
      sqlite.modifyQuery("CREATE TABLE friends_table (id INT PRIMARY KEY, playername VARCHAR(50),  registerdate VARCHAR(50));");
      sqlite.modifyQuery("INSERT INTO friends_table(playername, registerdate) VALUES('Amon_SuS', 'Amon_SuS2');");
      plugin.getLogger().info("friends_table has been created");
      }*/}
    
    @Override
    public void onDisable() {
    	sqlite.close();
        getLogger().info("AmonPack dezaktywowany");
    }
    
    public static Element getBladesElement() {
        return BladesElement;
    }
    public static Element getSmokeElement() {
        return SmokeElement;
    }
    
    public void createconf() {
    	 config.addDefault("AmonPack.Water.Ice.IcySpace.Cooldown", 12000);
    	 config.addDefault("AmonPack.Water.Ice.IcySpace.Range", 5);
         config.addDefault("AmonPack.Water.Ice.IcySpace.Duration", 10000);
         config.addDefault("AmonPack.Water.Ice.IcySpace.NightAugment.Cooldown", 5000);
         config.addDefault("AmonPack.Water.Ice.IcySpace.NightAugment.Range", 12);
         config.addDefault("AmonPack.Water.Ice.IcySpace.NightAugment.Duration", 60000);
         config.addDefault("AmonPack.Water.Ice.IcySpace.FullMoonAugment.Cooldown", 12000);
         config.addDefault("AmonPack.Water.Ice.IcySpace.FullMoonAugment.Range", 8);
         config.addDefault("AmonPack.Water.Ice.IcySpace.FullMoonAugment.Duration", 20000);
         config.addDefault("AmonPack.Water.Ice.IcySpace.1stPhaseDelay", 3);
         config.addDefault("AmonPack.Water.Ice.IcySpace.2ndPhaseDelay", 6);
         config.addDefault("AmonPack.Elemental.Water.DryGrassRevert", 15000);
         config.addDefault("AmonPack.Elemental.Water.DryGrassRange", 3);
  		config.addDefault("AmonPack.Elemental.Smoke.BlindnessDuration", 40);
  		config.addDefault("AmonPack.Elemental.Smoke.PoisonDuration", 40);
  		config.addDefault("AmonPack.Elemental.Smoke.PoisonPower", 1);
  		config.addDefault("AmonPack.Elemental.Smoke.SlowPower", 3);
  		config.addDefault("AmonPack.Elemental.Smoke.SlowDuration", 40);
  		config.addDefault("AmonPack.Elemental.Smoke.AffectUser", false);
         config.addDefault("AmonPack.Earth.Sand.SandWave.Cooldown", 6000);
         config.addDefault("AmonPack.Earth.Sand.SandWave.Range", 15);
         config.addDefault("AmonPack.Earth.Sand.SandWave.Duration", 4000);
         config.addDefault("AmonPack.Earth.Sand.SandWave.Size", 6);
         config.addDefault("AmonPack.Earth.Sand.SandWave.DeBuffPower", 2);
         config.addDefault("AmonPack.Earth.Sand.SandWave.DebuffDuration", 50);
         config.addDefault("AmonPack.Earth.Sand.SandWave.BurrowPower", 1);
         config.addDefault("AmonPack.Earth.Sand.SandBreath.Cooldown", 6000);
         config.addDefault("AmonPack.Earth.Sand.SandBreath.Range", 12);
         config.addDefault("AmonPack.Earth.Sand.SandBreath.Duration", 4000);
         config.addDefault("AmonPack.Earth.Sand.SandBreath.ChargeTime", 2000);
         config.addDefault("AmonPack.Earth.Sand.SandBreath.ChargedBreathBuff", true);
         config.addDefault("AmonPack.Earth.Sand.SandBreath.SpeedOnSand", 6);
         config.addDefault("AmonPack.Earth.Sand.SandBreath.SpeedOnEarth", 2);
         config.addDefault("AmonPack.Earth.Sand.SandBreath.DeBuffPower", 2);
         config.addDefault("AmonPack.Earth.Sand.SandBreath.DebuffDuration", 60);
         config.addDefault("AmonPack.Earth.Sand.SandBreath.Damage", 1);
         config.addDefault("AmonPack.Earth.Sand.SandBreath.DurationToUseBreath", 120);
         config.addDefault("AmonPack.Earth.Sand.SandBreath.CanDebuffEnemy", true);
 		 config.addDefault("AmonPack.Earth.Metal.SteelShackles.Range", 35);
 		 config.addDefault("AmonPack.Earth.Metal.SteelShackles.Hitbox", 3);
 		 config.addDefault("AmonPack.Earth.Metal.SteelShackles.DamageFirst", 2);
 		 config.addDefault("AmonPack.Earth.Metal.SteelShackles.DamageSecond", 1);
 		 config.addDefault("AmonPack.Earth.Metal.SteelShackles.StunRange", 15);
 		 config.addDefault("AmonPack.Earth.Metal.SteelShackles.StunDuration", 60);
 		 config.addDefault("AmonPack.Earth.Metal.SteelShackles.TimeToEscape", 60);
 		 config.addDefault("AmonPack.Earth.Metal.SteelShackles.DurabilityCost", 20);
 		 config.addDefault("AmonPack.Earth.Metal.SteelShackles.Cooldown", 7000);
 		 config.addDefault("AmonPack.Earth.Metal.MetalFlex.CooldownNormal", 10000);
 		 config.addDefault("AmonPack.Earth.Metal.MetalFlex.CooldownCrysis", 40000);
 		 config.addDefault("AmonPack.Earth.Metal.MetalFlex.SpeedPower", 3);
 		 config.addDefault("AmonPack.Earth.Metal.MetalFlex.CrysisDuration", 100);
 		 config.addDefault("AmonPack.Earth.Metal.MetalFlex.LowLevel", 5);
 		 config.addDefault("AmonPack.Earth.Metal.MetalCompress.CooldownMin", 4000);
 		 config.addDefault("AmonPack.Earth.Metal.MetalCompress.CooldownMax", 8000);
 		 config.addDefault("AmonPack.Earth.Metal.MetalCompress.Damage", 2);
 		 config.addDefault("AmonPack.Earth.Metal.MetalCompress.MaxChargeTime", 1700);
 		 config.addDefault("AmonPack.Earth.Metal.MetalCompress.Duration", 5);
 		 config.addDefault("AmonPack.Earth.Metal.MetalCompress.DurabilityCostMin", 10);
 		 config.addDefault("AmonPack.Earth.Metal.MetalCompress.DurabilityCostMax", 30);
 		 
 		config.addDefault("AmonPack.Chi.Blades.Slash.Dmg-1", 1);
 		config.addDefault("AmonPack.Chi.Blades.Slash.Dmg-2", 1);
 		config.addDefault("AmonPack.Chi.Blades.Slash.Dmg-3", 2);
 		config.addDefault("AmonPack.Chi.Blades.Slash.Cooldown", 4000);
 		config.addDefault("AmonPack.Chi.Blades.Slash.SpeedPower", 3);
 		config.addDefault("AmonPack.Chi.Blades.Slash.SpeedDuration", 20);
 		config.addDefault("AmonPack.Chi.Blades.Slash.InvDuration", 20);
 		config.addDefault("AmonPack.Chi.Blades.Slash.EvadePower", 1);
 		
 		config.addDefault("AmonPack.Chi.Blades.Pierce.SpeedPower", 3);
 		config.addDefault("AmonPack.Chi.Blades.Pierce.SpeedDuration", 60);
 		config.addDefault("AmonPack.Chi.Blades.Pierce.Dmg-1", 1);
 		config.addDefault("AmonPack.Chi.Blades.Pierce.Dmg-2", 3);
 		config.addDefault("AmonPack.Chi.Blades.Pierce.Cooldown", 4000);
 		config.addDefault("AmonPack.Chi.Blades.Pierce.DashPower", 2);
 		
 		config.addDefault("AmonPack.Chi.Blades.Stab.Uses", 4);
 		config.addDefault("AmonPack.Chi.Blades.Stab.Dmg-Left", 2);
 		config.addDefault("AmonPack.Chi.Blades.Stab.Dmg-Right", 2);
 		config.addDefault("AmonPack.Chi.Blades.Stab.Cooldown", 4000);
 		
 		config.addDefault("AmonPack.Chi.Blades.Counter.MaxHoldTime", 2500);
 		config.addDefault("AmonPack.Chi.Blades.Counter.EvadePower", 1);
 		config.addDefault("AmonPack.Chi.Blades.Counter.Cooldown", 4000);
 		config.addDefault("AmonPack.Chi.Blades.Counter.DashInAir", true);
 		
 		config.addDefault("AmonPack.Air.AirPressure.MaxHoldTime", 4000);
 		config.addDefault("AmonPack.Air.AirPressure.Cooldown", 4000);
 		config.addDefault("AmonPack.Air.AirPressure.Dmg", 2);
 		config.addDefault("AmonPack.Air.AirPressure.Range-Sphere", 20);
 		config.addDefault("AmonPack.Air.AirPressure.Range-Pull", 4);
 		config.addDefault("AmonPack.Air.AirPressure.PushPower", 1.5);
 		config.addDefault("AmonPack.Air.AirPressure.MinHoldTime", 500);
 		config.addDefault("AmonPack.Air.AirPressure.CanControlSphere", false);
 		
 		config.addDefault("AmonPack.Fire.Smoke.SmokeSurge.Cooldown", 4000);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeSurge.Range", 30);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeSurge.Dmg", 1);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeSurge.SlowPower", 2);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeSurge.SlowDuration", 40);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeSurge.PoisonPower", 1);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeSurge.PoisonDuration", 40);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeSurge.BlindnessDuration", 40);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeSurge.SmokeZoneDuration", 100);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeSurge.SmokeZoneRange", 3);
 		
 		config.addDefault("AmonPack.Fire.Smoke.SmokeDaggers.Cooldown", 4000);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeDaggers.Range", 30);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeDaggers.Dmg", 1);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeDaggers.SlowPower", 2);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeDaggers.SlowDuration", 40);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeDaggers.PoisonPower", 1);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeDaggers.PoisonDuration", 40);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeDaggers.BlindnessDuration", 40);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeDaggers.SmokeZoneDuration", 100);
 		config.addDefault("AmonPack.Fire.Smoke.SmokeDaggers.SmokeZoneRange", 2);
 		
 		config.addDefault("AmonPack.Water.Ice.IceArch.Cooldown", 4000);
 		config.addDefault("AmonPack.Water.Ice.IceArch.Range", 20);
 		config.addDefault("AmonPack.Water.Ice.IceArch.ChargeTime", 1500);
        config.addDefault("AmonPack.Water.Ice.IceArch.Damage", 2);
 		config.addDefault("AmonPack.Water.Ice.IceArch.Arch-Width", 3);
 		config.addDefault("AmonPack.Water.Ice.IceArch.Arch-Duration", 5000);
        config.addDefault("AmonPack.Water.Ice.IceArch.Arch-Thickness", 2);
 		config.addDefault("AmonPack.Water.Ice.IceArch.CanFreeze", false);
 		config.addDefault("AmonPack.Water.Ice.IceArch.FreezeDuration", 2000);
 		
 		config.addDefault("AmonPack.Water.Ice.IceArch.NightAugment.Cooldown", 4000);
 		config.addDefault("AmonPack.Water.Ice.IceArch.NightAugment.ChargeTime", 1000);
 		config.addDefault("AmonPack.Water.Ice.IceArch.NightAugment.Range", 30);
        config.addDefault("AmonPack.Water.Ice.IceArch.NightAugment.Damage", 3);
 		config.addDefault("AmonPack.Water.Ice.IceArch.NightAugment.Arch-Width", 4);
 		config.addDefault("AmonPack.Water.Ice.IceArch.NightAugment.Arch-Duration", 5000);
        config.addDefault("AmonPack.Water.Ice.IceArch.NightAugment.Arch-Thickness", 3);
 		config.addDefault("AmonPack.Water.Ice.IceArch.NightAugment.CanFreeze", false);
 		config.addDefault("AmonPack.Water.Ice.IceArch.NightAugment.FreezeDuration", 2000);
 		
 		config.addDefault("AmonPack.Water.Ice.IceArch.FullMoonAugment.Cooldown", 1000);
 		config.addDefault("AmonPack.Water.Ice.IceArch.FullMoonAugment.ChargeTime", 500);
 		config.addDefault("AmonPack.Water.Ice.IceArch.FullMoonAugment.Range", 40);
        config.addDefault("AmonPack.Water.Ice.IceArch.FullMoonAugment.Damage", 5);
 		config.addDefault("AmonPack.Water.Ice.IceArch.FullMoonAugment.Arch-Width", 5);
 		config.addDefault("AmonPack.Water.Ice.IceArch.FullMoonAugment.Arch-Duration", 5000);
        config.addDefault("AmonPack.Water.Ice.IceArch.FullMoonAugment.Arch-Thickness", 4);
 		config.addDefault("AmonPack.Water.Ice.IceArch.FullMoonAugment.CanFreeze", false);
 		config.addDefault("AmonPack.Water.Ice.IceArch.FullMoonAugment.FreezeDuration", 2000);

		config.options().copyDefaults(true);
        saveConfig();
    }

	public static FileConfiguration getNewConfigz() {
		return newConfigz;
	}

	public void setNewConfigz(FileConfiguration newConfigz) {
		AmonPackPlugin.newConfigz = newConfigz;
	}
    
}