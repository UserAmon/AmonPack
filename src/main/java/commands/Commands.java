package commands;

import java.util.ArrayList;
import java.util.List;

import methods_plugins.AmonPackPlugin;
import methods_plugins.Kopalnie;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Commands implements CommandExecutor {

	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	    if(cmd.getName().equalsIgnoreCase("reload")) {
	      	 if (sender instanceof Player) {
	    		 sender.sendMessage("Config Reloaded! ");
	    		 AmonPackPlugin.plugin.reloadConfig();
	    		 AmonPackPlugin.reloadcustom();
	    		 
	    	 } else {
	    		 System.out.println("Config reloaded!");
	    		 AmonPackPlugin.plugin.reloadConfig();
	    		 AmonPackPlugin.reloadcustom();
	        }}
	    if(cmd.getName().equalsIgnoreCase("QuestItems")) {
    		if (sender instanceof Player) {
       		 	Player player = (Player) sender;
       		for(String key : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Quests").getKeys(false)) {
				String name = null;
       			String type = AmonPackPlugin.getNewConfigz().getString("AmonPack.Quests." + key + ".Type");
       			if (AmonPackPlugin.getNewConfigz().getString("AmonPack.Quests." + key + ".Name") != null) {
           			name = ""+AmonPackPlugin.getNewConfigz().getString("AmonPack.Quests." + key + ".Name").replace("&", "§");
       			}
       			List<String> lorelist = new ArrayList<String>();
       			if (AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Quests." + key + ".Lore") != null) {
       			for(String lores : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Quests." + key + ".Lore").getKeys(false)) {
           		String lore = ""+AmonPackPlugin.getNewConfigz().getString("AmonPack.Quests." + key + ".Lore." + lores).replace("&", "§");;
           		if (lore != null) {
               		lorelist.add(ChatColor.translateAlternateColorCodes('&', lore));
           		}}}
       			ItemStack QuestItem = new ItemStack(Material.getMaterial(type), 1);
       	        ItemMeta QuestItemMeta = QuestItem.getItemMeta();
       			if (AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Quests." + key + ".Enchantment") != null) {
       			for(String enchname : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Quests." + key + ".Enchantment").getKeys(false)) {
           		int enchpower = AmonPackPlugin.getNewConfigz().getInt("AmonPack.Quests." + key + ".Enchantment." + enchname + ".EnchantmentLevel");
				QuestItemMeta.addEnchant(Enchantment.getByName(enchname), enchpower, true);
       			}}
       	        if (name != null) {
				QuestItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
      			}
				if (lorelist != null) {
				QuestItemMeta.setLore(lorelist);
      			}
				QuestItem.setItemMeta(QuestItemMeta);
       			player.getInventory().addItem(QuestItem);
       		}
			player.getInventory().addItem(Kopalnie.PickaxeTier1);
			player.getInventory().addItem(Kopalnie.PickaxeTier2);
			player.getInventory().addItem(Kopalnie.PickaxeTier3);
       	 } else {
             System.out.println("QuestItems");
           }
	    }
	    return false;
	}

	public static ItemStack QuestItemConfig(Player player, String itemname){
		ItemStack QuestItem = new ItemStack(Material.DIRT, 1);
		for(String key : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Items").getKeys(false)) {
			if (key.equalsIgnoreCase(itemname)){
			String name = null;
			String type = AmonPackPlugin.getNewConfigz().getString("AmonPack.Items." + key + ".Type");
			if (AmonPackPlugin.getNewConfigz().getString("AmonPack.Items." + key + ".Name") != null) {
				name = ""+AmonPackPlugin.getNewConfigz().getString("AmonPack.Items." + key + ".Name").replace("&", "§");
			}
			List<String> lorelist = new ArrayList<String>();
			if (AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Items." + key + ".Lore") != null) {
				for(String lores : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Items." + key + ".Lore").getKeys(false)) {
					String lore = ""+AmonPackPlugin.getNewConfigz().getString("AmonPack.Items." + key + ".Lore." + lores).replace("&", "§");;
					if (lore != null) {
						lorelist.add(ChatColor.translateAlternateColorCodes('&', lore));
					}}}
			QuestItem = new ItemStack(Material.getMaterial(type), 1);
			ItemMeta QuestItemMeta = QuestItem.getItemMeta();
			if (AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Items." + key + ".Enchantment") != null) {
			for(String enchname : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Items." + key + ".Enchantment").getKeys(false)) {
			int enchpower = AmonPackPlugin.getNewConfigz().getInt("AmonPack.Items." + key + ".Enchantment." + enchname + ".EnchantmentLevel");
			QuestItemMeta.addEnchant(Enchantment.getByName(enchname), enchpower, true);
			}}
			if (name != null) {
				QuestItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
			}
			if (lorelist != null) {
				QuestItemMeta.setLore(lorelist);
			}
			QuestItem.setItemMeta(QuestItemMeta);
		}}
		for(String key : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Quests").getKeys(false)) {
			if (key.equalsIgnoreCase(itemname)){
				String name = null;
				String type = AmonPackPlugin.getNewConfigz().getString("AmonPack.Quests." + key + ".Type");
				if (AmonPackPlugin.getNewConfigz().getString("AmonPack.Quests." + key + ".Name") != null) {
					name = ""+AmonPackPlugin.getNewConfigz().getString("AmonPack.Quests." + key + ".Name").replace("&", "§");
				}
				List<String> lorelist = new ArrayList<String>();
				if (AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Quests." + key + ".Lore") != null) {
					for(String lores : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Quests." + key + ".Lore").getKeys(false)) {
						String lore = ""+AmonPackPlugin.getNewConfigz().getString("AmonPack.Quests." + key + ".Lore." + lores).replace("&", "§");;
						if (lore != null) {
							lorelist.add(ChatColor.translateAlternateColorCodes('&', lore));
						}}}
				QuestItem = new ItemStack(Material.getMaterial(type), 1);
				ItemMeta QuestItemMeta = QuestItem.getItemMeta();
				if (AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Quests." + key + ".Enchantment") != null) {
					for(String enchname : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Quests." + key + ".Enchantment").getKeys(false)) {
						int enchpower = AmonPackPlugin.getNewConfigz().getInt("AmonPack.Quests." + key + ".Enchantment." + enchname + ".EnchantmentLevel");
						QuestItemMeta.addEnchant(Enchantment.getByName(enchname), enchpower, true);
					}}
				if (name != null) {
					QuestItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
				}
				if (lorelist != null) {
					QuestItemMeta.setLore(lorelist);
				}
				QuestItem.setItemMeta(QuestItemMeta);
			}}
		return QuestItem;
	}

}