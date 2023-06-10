package methods_plugins;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CollectItem implements Listener {
    Inventory menu;

    private final Map<Player, Integer> clickCounts;

    public CollectItem(Plugin plugin) {
        this.clickCounts = new HashMap<>();
    }
    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }
    private void openMenu(Player player) {
        menu = Bukkit.createInventory(player, 9, "Kliknij kamień!");
        int DropRandom = getRandom(0, 8);
        menu.setItem(DropRandom, new ItemStack(Material.STONE));
        player.openInventory(menu);
    }
    private void addItemToInventory(Player player) {
        ItemStack stone = new ItemStack(Material.STONE);
        player.getInventory().addItem(stone);
        player.sendMessage("Otrzymałeś kamień!");
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (event.getBlock().getType() == Material.STONE) {
            event.setCancelled(true);
            clickCounts.put(player, 0);
            openMenu(player);
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();
        if (inventory != null && inventory.equals(menu)) {
            event.setCancelled(true);
                if (clickedItem != null && clickedItem.getType() == Material.STONE) {
                    int clickCount = clickCounts.get(player) + 1;
                    clickCounts.put(player, clickCount);
                    openMenu(player);
                    player.sendMessage("test "+ clickCounts.get(player));
                    if (clickCount >= 3) {
                        addItemToInventory(player);
                        player.sendMessage("Udało ci się zdobyć kamień!");
                        player.closeInventory();
                        clickCounts.remove(player);
                    }}
        }
    }
}
