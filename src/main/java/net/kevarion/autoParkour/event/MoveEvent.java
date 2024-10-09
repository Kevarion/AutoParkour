package net.kevarion.autoParkour.event;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MoveEvent implements Listener {

    private static final Map<UUID, Location> playerNextJump = new HashMap<>();
    private static final int MAX_DISTANCE = 4;
    private static final Random random = new Random();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        Player player = event.getPlayer();

        if (from.getBlock() == to.getBlock() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ())
            return;

        Block targetBlock = to.getBlock().getRelative(BlockFace.DOWN);
        if (playerNextJump.containsKey(player.getUniqueId())
                && player.getLocation().getBlockY() < playerNextJump.get(player.getUniqueId()).getBlockY()) {
            player.sendTitle(ChatColor.RED + "FAIL", ChatColor.GRAY + "Try again!");

            playerNextJump.remove(player.getUniqueId());
            return;

        }

        if (targetBlock.getType() != Material.DIAMOND_BLOCK)
            return;

        targetBlock.setType(Material.EMERALD_BLOCK);
        player.playSound(targetBlock.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);

        Location nextJumpLocation = calculateNextJumpPosition(targetBlock.getLocation());

    }

    private Location calculateNextJumpPosition(Location old) {
        int deltaX = getRandomDelta();
        int deltaY = getRandomDelta(1);
        int deltaZ = getRandomDelta();

        return deltaX == 0 && deltaZ == 0
                ? old.clone().add(1, deltaY, 0)
                : old.clone().add(deltaX, deltaY, deltaZ);
    }

    private int getRandomDelta(int bound) {
        return random.nextInt(bound + 2) - bound;
    }

    private int getRandomDelta() {
        return getRandomDelta(MAX_DISTANCE);
    }

}
