package me.swipez.trademultiply;

import me.swipez.trademultiply.stored.StoredVillager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class OpenVIllagerInventoryListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractAtEntityEvent event){
        if (event.getRightClicked().getType().equals(EntityType.VILLAGER)){
            Villager villager = (Villager) event.getRightClicked();
            Player player = event.getPlayer();
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (villager.isTrading()){
                        StoredVillager.villagerHashMap.put(player.getUniqueId(), villager);
                    }
                }
            }.runTaskLater(TradeMultiply.plugin, 1);
        }
    }
    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event){
        if (event.getInventory().getType().equals(InventoryType.MERCHANT)){
            MerchantInventory merchantInventory = (MerchantInventory) event.getInventory();
            if (merchantInventory.getMerchant() instanceof Villager){
                Villager villager = (Villager) merchantInventory.getMerchant();
                if (StoredVillager.villagerHashMap.containsValue(villager)){
                    StoredVillager.villagerHashMap.remove(event.getPlayer().getUniqueId());
                }
            }
        }
    }
}
