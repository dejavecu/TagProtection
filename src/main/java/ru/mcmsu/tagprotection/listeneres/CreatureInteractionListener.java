package ru.mcmsu.tagprotection.listeneres;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class CreatureInteractionListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Entity target = event.getRightClicked();

        if (target == null || !(target instanceof LivingEntity) || target instanceof Player) {
            return;
        }

        LivingEntity targetCreature = (LivingEntity) target;
        String targetName = targetCreature.getCustomName();

        if (targetName != null && targetName.contains("'s")) {
            if (!isCreatureOwnedByPlayer(player, targetName)) {
                player.sendMessage(ChatColor.RED
                        + "This creature doesn't belongs to you!");
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamagesEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Entity damager = event.getDamager();
        Player player;
        if (damager instanceof Projectile) {
            ProjectileSource damageSource = ((Projectile) damager).getShooter();
            if (!(damageSource instanceof Player)) {
                return;
            }
            player = (Player) damageSource;
        }
        else if (damager instanceof Player)
        {
            player = (Player) damager;
        } else
        {
            return;
        }
        
        Entity victim = event.getEntity();
        if (!(victim instanceof LivingEntity) || victim instanceof Player)
        {
            return;
        }
        
        String targetName = ((LivingEntity) event.getEntity()).getCustomName();
        if (targetName != null && targetName.contains("'s")) {
            if (!isCreatureOwnedByPlayer(player, targetName)) {
                player.sendMessage(ChatColor.RED
                        + "This creature doesn't belongs to you!");
                event.setCancelled(true);
                return;
            }
        }
    }

    private Boolean isCreatureOwnedByPlayer(Player player, String creatureName) {
        return creatureName.contains(player.getName() + "'s");
    }
}
