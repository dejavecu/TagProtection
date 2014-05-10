package ru.mcmsu.tagprotection;

import org.bukkit.plugin.java.JavaPlugin;

import ru.mcmsu.tagprotection.listeneres.CreatureInteractionListener;

public class TagProtection extends JavaPlugin 
{
    @Override
    public void onEnable() {
    	getServer().getPluginManager().registerEvents(new CreatureInteractionListener(), this);
    }
 
    @Override
    public void onDisable() {
    }
}