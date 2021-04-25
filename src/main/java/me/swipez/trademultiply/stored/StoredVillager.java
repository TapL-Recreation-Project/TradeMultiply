package me.swipez.trademultiply.stored;

import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class StoredVillager {
    public static HashMap<UUID, Villager> villagerHashMap = new HashMap<>();
    public static HashMap<UUID, Material> itemStackHashMap = new HashMap<>();
}
