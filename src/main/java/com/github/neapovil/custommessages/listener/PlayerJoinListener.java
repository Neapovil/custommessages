package com.github.neapovil.custommessages.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.neapovil.custommessages.CustomMessages;

import net.kyori.adventure.text.Component;

public final class PlayerJoinListener implements Listener
{
    private final CustomMessages plugin = CustomMessages.getInstance();

    @EventHandler
    public void playerJoin(PlayerJoinEvent event)
    {
        if (!plugin.isCustomMessagesEnabled())
        {
            return;
        }

        final String message = plugin.getFileConfig().get("custommessages.playerJoin");
        final Component component = plugin.deserialize(message)
                .replaceText(config -> {
                    config.match("<player>").replacement(event.getPlayer().getName());
                });

        event.joinMessage(component);
    }
}
