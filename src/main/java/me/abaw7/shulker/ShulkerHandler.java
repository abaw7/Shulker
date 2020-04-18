/*
 * Copyright (c) 2020. This source code is licensed under MIT. A copy of this license is available with the source code in the LICENSE.md file.
 */

package me.abaw7.shulker;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ShulkerHandler extends JavaPlugin implements Listener {
    private PluginLogger logger;


    @Override
    public void onEnable() {
        logger = new PluginLogger(this);
        logger.info("Shulker Handler loaded");


        SpawnListener spawnListener = new SpawnListener(logger);
        DeathListener deathListener = new DeathListener(logger);


        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(spawnListener, this);
        pluginManager.registerEvents(deathListener, this);

    }
}
