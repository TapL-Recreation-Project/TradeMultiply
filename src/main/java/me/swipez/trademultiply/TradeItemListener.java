package me.swipez.trademultiply;

import me.swipez.trademultiply.stored.StoredVillager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TradeItemListener implements Listener {

    Random random = new Random();

    @EventHandler
    public void onPlayerTrade(InventoryClickEvent event){
        if (event.getClickedInventory() == null){
            return;
        }
        if (event.getClickedInventory().getType().equals(InventoryType.MERCHANT)){
            Inventory inventory = event.getClickedInventory();
            MerchantInventory merchantInventory = (MerchantInventory) inventory;
            if (inventory.getItem(2) != null){
                if (event.getCurrentItem().isSimilar(inventory.getItem(2))){
                    Player player = (Player) event.getWhoClicked();
                    ItemStack itemStack = event.getCurrentItem().clone();
                    ItemStack actualStack = event.getCurrentItem();
                    actualStack.setAmount(1);
                    StoredVillager.itemStackHashMap.put(player.getUniqueId(), actualStack.getType());
                    BukkitTask task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            List<MerchantRecipe> newRecipesList = new ArrayList<>();
                            List<MerchantRecipe> finalList = new ArrayList<>();
                            Merchant villager = merchantInventory.getMerchant();
                            if (villager.getRecipes() != null) {
                                for (MerchantRecipe recipe : villager.getRecipes()) {
                                    newRecipesList.add(recipe);
                                }
                                for (MerchantRecipe recipe : newRecipesList) {
                                    ItemStack itemStack = recipe.getResult();
                                    int newAmount = itemStack.getAmount()+1;
                                    if (newAmount > 127){
                                        newAmount = 127;
                                    }
                                    itemStack.setAmount(newAmount);
                                    MerchantRecipe recipe1 = new MerchantRecipe(itemStack, recipe.getUses(), recipe.getMaxUses(), recipe.hasExperienceReward(), recipe.getVillagerExperience(), recipe.getPriceMultiplier());
                                    recipe1.setIngredients(recipe.getIngredients());
                                    finalList.add(recipe1);
                                }
                                villager.setRecipes(finalList);
                                BukkitTask task1 = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        event.getWhoClicked().openMerchant(StoredVillager.villagerHashMap.get(event.getWhoClicked().getUniqueId()), true);
                                        player.setItemOnCursor(itemStack);
                                    }
                                }.runTaskLater(TradeMultiply.plugin, (long) 0.2);
                            }
                        }
                    }.runTaskLater(TradeMultiply.plugin, 1);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDropsItem(PlayerDropItemEvent event){
        if (StoredVillager.itemStackHashMap.containsKey(event.getPlayer().getUniqueId())){
            if (event.getItemDrop().getItemStack().getType().equals(StoredVillager.itemStackHashMap.get(event.getPlayer().getUniqueId()))){
                if (event.getItemDrop().getItemStack().getAmount() == 1){
                    event.getItemDrop().remove();
                    StoredVillager.itemStackHashMap.remove(event.getPlayer().getUniqueId());
                }
            }
        }
    }
}
