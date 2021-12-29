package com.github.fishfly233.blocklift;

import org.bukkit.plugin.java.JavaPlugin;

public class BlockLiftMain extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new PlayerEventsListener(), this);
    }
}
