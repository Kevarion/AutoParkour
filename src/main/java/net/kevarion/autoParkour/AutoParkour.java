package net.kevarion.autoParkour;

import net.kevarion.autoParkour.event.MoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoParkour extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
    }
}
