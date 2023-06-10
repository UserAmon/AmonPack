package commands;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;



public class CommandsListener implements Listener {

	@EventHandler
	public void GuiClick(InventoryClickEvent event) {
		//Player p = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) {
			event.setCancelled(true);
			return;
        }
        if (event.getClickedInventory() == null) {
			event.setCancelled(true);
			return;
        }
		if (event.getClickedInventory() == SpellTree.Binding) {
			if (event.getCurrentItem().getType() == Material.BARRIER) {
				event.setCancelled(true);
			}
			if (event.getCurrentItem().getType() == Material.PAPER) {
				event.setCancelled(true);
			}
	}}
	}
