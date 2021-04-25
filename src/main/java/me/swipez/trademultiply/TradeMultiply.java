package me.swipez.trademultiply;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class TradeMultiply extends JavaPlugin {

    public static TradeMultiply plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new OpenVIllagerInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new TradeItemListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
