package com.github.neapovil.customdeathmessage.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.github.neapovil.customdeathmessage.CustomDeathMessage;

import net.kyori.adventure.text.Component;

public final class PlayerDeathListener implements Listener
{
  private final CustomDeathMessage plugin = CustomDeathMessage.getInstance();

  @EventHandler
  private void playerDeath(PlayerDeathEvent event)
  {
    if (!plugin.isCustomDeathMessageEnabled())
    {
      return;
    }

    final Player killer = event.getPlayer().getKiller();

    if (killer == null)
    {
      final String message = plugin.getFileConfig().get("custommessages.playerDeathNoKiller");

      final Component test = plugin.deserialize(message)
          .replaceText(config -> {
            config.match("<victim>");
            config.replacement(event.getPlayer().getName());
          });

      event.deathMessage(test);
    }
    else
    {
      final Player victim = event.getPlayer();
      final String message = plugin.getFileConfig().get("custommessages.playerDeath");

      final ItemStack itemstack = killer.getInventory().getItemInMainHand();

      final Component test = plugin.deserialize(message)
          .replaceText(config -> {
            config.match("<killer>").replacement(killer.getName());
          })
          .replaceText(config -> {
            config.match("<victim>").replacement(victim.getName());
          })
          .replaceText(config -> {
            config.match("<weapon>");

            if (itemstack.getType().equals(Material.AIR))
            {
              config.replacement("Hands");
            }
            else
            {
              config.replacement(itemstack.displayName().hoverEvent(itemstack.asHoverEvent()));
            }
          });

      event.deathMessage(test);
    }
  }
}
