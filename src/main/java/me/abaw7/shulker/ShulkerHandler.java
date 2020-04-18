/*
 * Copyright (c) 2020. This source code is licensed under MIT. A copy of this license is available with the source code in the LICENSE.md file.
 */

package me.abaw7.shulker;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

import static org.bukkit.StructureType.END_CITY;

public class ShulkerHandler extends JavaPlugin implements Listener {
    static private boolean isEntityOnSpawnableBlock(Entity entity) {
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

    static private boolean isEntityInCorrectBiome(Entity entity) {
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

    static private boolean isEntityCloseEnoughToNearestEndCity(Entity entity) {
        Location origin = entity.getLocation();
        Location closestEndCity = entity.getWorld().locateNearestStructure(origin, END_CITY, 4, false);
        if (closestEndCity == null) {
            return false;
        }
        return closestEndCity.distance(origin) < 100;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) { //onEntitySpawn(EntitySpawnEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Enderman) {
            //if(entity.getWorld().getEnvironment() == Environment.THE_END){
            Random random = new Random();
            int chance = random.nextInt(100);
            if (chance < 50) { // 50% chance of spawning a shulker
                Location location = entity.getLocation();
                World world = entity.getWorld();
                if (isEntityInCorrectBiome(entity)) {
                    if (isEntityCloseEnoughToNearestEndCity(entity)) {
                        if (isEntityOnSpawnableBlock(entity)) {
                            e.setCancelled(true);
                            Shulker shulker = world.spawn(location, Shulker.class);
                            shulker.setRemoveWhenFarAway(true); // Enable normal mob despawn mechanics
                        }
                    }
                }
            }
        }
    }
}
