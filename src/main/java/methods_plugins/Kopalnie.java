package methods_plugins;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.util.TempBlock;
import com.sun.tools.javac.jvm.Items;
import commands.Commands;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.Material.getMaterial;

public class Kopalnie implements Listener {
    public static ItemStack PickaxeTier1;
    public static ItemStack PickaxeTier2;
    public static ItemStack PickaxeTier3;
    public static void SetMiningItems() {
        List<String> lorelist = new ArrayList<String>();
        lorelist.add("" + ChatColor.DARK_PURPLE + "Rzadki Minerał");

        PickaxeTier1 = new ItemStack(Material.WOODEN_PICKAXE, 1);
        ItemMeta PickaxeTier1Meta = PickaxeTier1.getItemMeta();
        PickaxeTier1Meta.addEnchant(Enchantment.DURABILITY, 10, true);
        PickaxeTier1Meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
        PickaxeTier1Meta.setDisplayName("" + ChatColor.GRAY +ChatColor.BOLD + "Kilof Tier 1");
        PickaxeTier1.setItemMeta(PickaxeTier1Meta);

        PickaxeTier2 = new ItemStack(Material.IRON_PICKAXE, 1);
        ItemMeta PickaxeTier2Meta = PickaxeTier2.getItemMeta();
        PickaxeTier2Meta.addEnchant(Enchantment.DURABILITY, 10, true);
        PickaxeTier2Meta.addEnchant(Enchantment.DIG_SPEED, 4, true);
        PickaxeTier2Meta.setDisplayName("" + ChatColor.GRAY +ChatColor.BOLD + "Kilof Tier 2");
        PickaxeTier2.setItemMeta(PickaxeTier2Meta);

        PickaxeTier3 = new ItemStack(Material.GOLDEN_PICKAXE, 1);
        ItemMeta PickaxeTier3Meta = PickaxeTier3.getItemMeta();
        PickaxeTier3Meta.addEnchant(Enchantment.DURABILITY, 10, true);
        PickaxeTier3Meta.addEnchant(Enchantment.DIG_SPEED, 6, true);
        PickaxeTier3Meta.setDisplayName("" + ChatColor.GRAY +ChatColor.BOLD + "Kilof Tier 3");
        PickaxeTier3.setItemMeta(PickaxeTier3Meta);
    }



    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        int minezone = 0;
        for (int i = 0; i < AmonPackPlugin.MineList.size(); i++) {
        if (event.getBlock().getLocation().getWorld() == AmonPackPlugin.MineList.get(i).getWorld()){
        if (player.getLocation().distance(AmonPackPlugin.MineList.get(i)) < 100) {
            minezone = i;
        }}}
        if (event.getBlock().getLocation().getWorld() == AmonPackPlugin.MineList.get(minezone).getWorld()){
        if (player.getLocation().distance(AmonPackPlugin.MineList.get(minezone)) < 100) {
            event.setCancelled(true);
        if (player.getInventory().getItemInMainHand().isSimilar(PickaxeTier1)) {

        for(String key : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.MiningOresDrops").getKeys(false)) {
        if (event.getBlock().getType() == getMaterial(key)) {
                Kopanie(player, event.getBlock(),minezone);
        }}
        }else if (player.getInventory().getItemInMainHand().isSimilar(PickaxeTier2)) {
            for (Block blocks : GeneralMethods.getBlocksAroundPoint(event.getBlock().getLocation(), 1)) {
            for(String key : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.MiningOresDrops").getKeys(false)) {
                if (blocks.getType() == getMaterial(key)) {
                        Kopanie(player, blocks,minezone);
                    }}}
        }else if (player.getInventory().getItemInMainHand().isSimilar(PickaxeTier3)) {
            for (Block blocks : GeneralMethods.getBlocksAroundPoint(event.getBlock().getLocation(), 2)) {
                for(String key : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.MiningOresDrops").getKeys(false)) {
                    if (blocks.getType() == getMaterial(key)) {
                        Kopanie(player, blocks,minezone);
                    }}}
        }else {
            for(String key : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.MiningOresDrops").getKeys(false)) {
                if (event.getBlock().getType() == getMaterial(key)) {
                    Kopanie(player, event.getBlock(),minezone);
                }}
        }}else if (player.getInventory().getItemInMainHand().isSimilar(PickaxeTier1)) {
            event.setCancelled(true);
            player.sendMessage(""+ChatColor.RED+"Ten kilof służy tylko do kopania w Kopalniach");
        }}
    }
    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }

    private void Kopanie(Player player, Block b, int minezone) {
        int DropRandom = getRandom(0, 20);
        int OreRandom = getRandom(0, 20);
        Material mat = b.getType();

        List<String> Ores = new ArrayList<String>();
        for(String key : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Mining.Kopalnia"+(minezone+1)+".Ores").getKeys(false)) {
            for (int i = 0; i < AmonPackPlugin.getNewConfigz().getInt("AmonPack.Mining.Kopalnia"+(minezone+1)+".Ores." + key); i++) {
                Ores.add(key);
        }}
        if (OreRandom > Ores.size()) {
            b.setType(Material.STONE);
            TempBlock tb1 = new TempBlock(b, Material.AIR);
            tb1.setRevertTime(12000);
        } else if (OreRandom <= Ores.size()) {
            Random rand = new Random();
            String st = Ores.get(rand.nextInt(Ores.size()));
            b.setType(Material.getMaterial(st));
            TempBlock tb1 = new TempBlock(b, Material.AIR);
            tb1.setRevertTime(12000);
        }
            for(String key : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.MiningOresDrops").getKeys(false)) {
            if (mat == getMaterial(key)){
                player.getInventory().addItem(Commands.QuestItemConfig(player,AmonPackPlugin.getNewConfigz().getString("AmonPack.MiningOresDrops." + key)));
            }}

            if (AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Mining.Kopalnia"+(minezone+1)) != null){
            if (AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Mining.Kopalnia"+(minezone+1)+".Loot") != null){
        List<String> Loot = new ArrayList<String>();
        for(String key : AmonPackPlugin.getNewConfigz().getConfigurationSection("AmonPack.Mining.Kopalnia"+(minezone+1)+".Loot").getKeys(false)) {
            for (int i = 0; i < AmonPackPlugin.getNewConfigz().getInt("AmonPack.Mining.Kopalnia"+(minezone+1)+".Loot." + key); i++) {
                Loot.add(key);
            }}
        if (DropRandom < Loot.size()) {
            Random rand = new Random();
            String st = Loot.get(rand.nextInt(Loot.size()));
            player.sendMessage("" + ChatColor.GRAY + "Udało Ci się wydobyć " + Commands.QuestItemConfig(player,st).getItemMeta().getDisplayName());
            player.getInventory().addItem(Commands.QuestItemConfig(player,st));
        }}}}
}
