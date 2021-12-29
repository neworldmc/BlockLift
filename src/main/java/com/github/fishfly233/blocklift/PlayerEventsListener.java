package com.github.fishfly233.blocklift;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerEventsListener implements Listener {
    @EventHandler
    public void onPlayerJump(PlayerJumpEvent e) {
        Location srcLoc = e.getFrom();
        Material blockMaterial = srcLoc.clone().subtract(0, 1, 0).getBlock().getBlockData().getMaterial();
        if (Material.DIAMOND_BLOCK != blockMaterial) {
            return;
        }
        // 从下到上搜索第一块钻石块并传送
        Location blockLoc = srcLoc.clone();
        for (double y = srcLoc.getY(); y < srcLoc.getWorld().getMaxHeight(); y++) {
            blockLoc.setY(y);
            blockMaterial = blockLoc.getBlock().getBlockData().getMaterial();
            if (Material.AIR != blockMaterial) {
                if (Material.DIAMOND_BLOCK == blockMaterial) {
                    teleportPlayerWithParticle(e.getPlayer(), blockLoc);
                    break;
                }
                else return;
            }
        }
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent e) {
        if (e.isSneaking()) {
            Location srcLoc = e.getPlayer().getLocation();
            Material blockMaterial = srcLoc.clone().subtract(0, 1, 0).getBlock().getBlockData().getMaterial();
            if (Material.DIAMOND_BLOCK != blockMaterial) {
                return;
            }
            Location blockLoc = srcLoc.clone();
            for (double y = srcLoc.getY() - 2; y > srcLoc.getWorld().getMinHeight(); y--) {
                blockLoc.setY(y);
                blockMaterial = blockLoc.getBlock().getBlockData().getMaterial();
                if (Material.AIR != blockMaterial) {
                    if (Material.DIAMOND_BLOCK == blockMaterial) {
                        teleportPlayerWithParticle(e.getPlayer(), blockLoc);
                        break;
                    }
                    else return;
                }
            }
        }
    }

    private void teleportPlayerWithParticle(Player p, Location blockLoc) {
        blockLoc.add(0 , 1.1, 0);
        p.teleport(blockLoc);
        p.spawnParticle(Particle.END_ROD, blockLoc, 10);
        p.playSound(blockLoc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1.0f);
    }
}
