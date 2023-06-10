package commands;

import java.util.List;

import methods_plugins.AmonPackPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;



public class SpellTree implements CommandExecutor {
	public static Inventory Binding;

	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (sender instanceof Player) {
		Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("SpellTree")) {
		if (args.length != 1) {
		player.sendMessage("Wybrałeś błędny żywioł");
	    return false;
	    }
  		for(String keys : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.SpellTree.Abilities").getKeys(false)) {
  		if (args[0].equalsIgnoreCase(keys)) {
  		ElementsTrees(player, args[0]);
  		return true;
  		}}}}
		return false;
	}
	
    public static void ElementsTrees(Player p, String st) {
    	List<String> AbilitiesList = AmonPackPlugin.getNewConfigz().getStringList("AmonPack.SpellTree.Abilities."+st.toLowerCase());
		int num = AbilitiesList.size();
		String stlow = st.toLowerCase().substring(0, 1).toUpperCase();
	    String stup = stlow + st.toLowerCase().substring(1);
		Binding = Bukkit.createInventory(p, 54, ChatColor.BLACK + stup);
		for (int loops = 0; loops < num; loops++) {
			String UwU = AbilitiesList.get(loops);
			ItemStack Abilities = new ItemStack(Material.PAPER);
			ItemMeta Abilities_item_meta = Abilities.getItemMeta();
			Abilities_item_meta.setDisplayName(UwU);
			Abilities.setItemMeta(Abilities_item_meta);
			Binding.setItem(loops, Abilities);
		   }
			ItemStack EXIT = new ItemStack(Material.BARRIER);
			ItemMeta EXIT_item_meta = EXIT.getItemMeta();
			EXIT_item_meta.setDisplayName(ChatColor.RED + "Go Back");
			EXIT.setItemMeta(EXIT_item_meta);
			Binding.setItem(53, EXIT);
		p.openInventory(Binding);
		
	}
	
}