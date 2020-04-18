/*
 * Copyright (c) 2020. This source code is licensed under MIT. A copy of this license is available with the source code in the LICENSE.md file.
 */

package me.abaw7.shulker;

import org.bukkit.Material;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginLogger;

public class DeathListener implements Listener {
    private final PluginLogger logger;

    public DeathListener(PluginLogger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onShulkerDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Shulker) {
            logger.info("Shulker Death");
            Shulker shulker = (Shulker) event.getEntity();
            if (shulker.getEntitySpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
                logger.info("Setting drops to 2 shells");
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.SHULKER_SHELL, 2));
            }
        }
    }
}
