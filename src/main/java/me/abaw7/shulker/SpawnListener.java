/*
 * Copyright (c) 2020. This source code is licensed under MIT. A copy of this license is available with the source code in the LICENSE.md file.
 */

package me.abaw7.shulker;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.PluginLogger;

import static org.bukkit.StructureType.END_CITY;

public class SpawnListener implements Listener {
    private final PluginLogger logger;

    public SpawnListener(PluginLogger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) { //onEntitySpawn(EntitySpawnEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Enderman) {
            //if(entity.getWorld().getEnvironment() == Environment.THE_END){
            Location location = entity.getLocation();
            World world = entity.getWorld();
            if (isEntityInCorrectBiome(entity)) { // If in a biome where end cities spawn
                if (isEntityCloseEnoughToNearestEndCity(entity)) { // If the nearest end city is within 100 blocks
                    if (isEntityOnSpawnableBlock(entity)) { // If the block is a part that spawns as part of the end city
                        e.setCancelled(true);
                        Shulker shulker = world.spawn(location, Shulker.class);
                        shulker.setRemoveWhenFarAway(true); // Enable normal mob despawn mechanics
                        shulker.setColor(DyeColor.WHITE);
                    }
                }
            }
        }
    }

    private boolean isEntityOnSpawnableBlock(Entity entity) {
        switch (entity.getLocation().subtract(0, 1, 0).getBlock().getType()) {
            case PURPUR_BLOCK:
            case PURPUR_SLAB:
            case PURPUR_PILLAR:
            case END_STONE_BRICKS:
            case END_STONE_BRICK_SLAB:
                return true;
            default:
                break;
        }
        switch (entity.getLocation().getBlock().getType()) {
            case PURPUR_BLOCK:
            case PURPUR_SLAB:
            case PURPUR_PILLAR:
            case END_STONE_BRICKS:
            case END_STONE_BRICK_SLAB:
                return true;
            default:
                return false;
        }
    }

    private boolean isEntityInCorrectBiome(Entity entity) {
        if (entity.getWorld().getEnvironment() == World.Environment.THE_END) {
            switch (entity.getLocation().getBlock().getBiome()) {
                case END_HIGHLANDS:
                case END_MIDLANDS:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    private boolean isEntityCloseEnoughToNearestEndCity(Entity entity) {
        Location origin = entity.getLocation();
        Location closestEndCity = entity.getWorld().locateNearestStructure(origin, END_CITY, 4, false);
        if (closestEndCity == null) {
            return false;
        }
        closestEndCity.setY(0);
        origin.setY(0);
        return closestEndCity.distance(origin) < 100;
    }
}
