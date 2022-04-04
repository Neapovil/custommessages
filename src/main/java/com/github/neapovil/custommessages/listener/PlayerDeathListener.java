package com.github.neapovil.custommessages.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.github.neapovil.custommessages.CustomMessages;

import net.kyori.adventure.text.Component;

public final class PlayerDeathListener implements Listener
{
    private final CustomMessages plugin = CustomMessages.getInstance();

    @EventHandler
    private void playerDeath(PlayerDeathEvent event)
    {
        if (!plugin.isCustomMessagesEnabled())
        {
            return;
        }

        final Player killer = event.getPlayer().getKiller();

        if (killer == null)
        {
            final String message = plugin.getFileConfig().get("custommessages.playerDeathNoKiller");

            final Component component = plugin.deserialize(message)
                    .replaceText(config -> {
                        config.match("<victim>");
                        config.replacement(event.getPlayer().getName());
                    });

            event.deathMessage(component);
        }
        else
        {
            final Player victim = event.getPlayer();
            final String message = plugin.getFileConfig().get("custommessages.playerDeath");

            final ItemStack itemstack = killer.getInventory().getItemInMainHand();

            final Component component = plugin.deserialize(message)
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

            event.deathMessage(component);
        }
    }
}
