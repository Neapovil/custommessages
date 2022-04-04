package com.github.neapovil.custommessages.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.neapovil.custommessages.CustomMessages;

import net.kyori.adventure.text.Component;

public final class PlayerQuitListener implements Listener
{
    private final CustomMessages plugin = CustomMessages.getInstance();

    @EventHandler
    public void playerQuit(PlayerQuitEvent event)
    {
        if (!plugin.isCustomMessagesEnabled())
        {
            return;
        }

        final String message = plugin.getFileConfig().get("custommessages.playerLeft");
        final Component component = plugin.deserialize(message)
                .replaceText(config -> {
                    config.match("<player>").replacement(event.getPlayer().getName());
                });

        event.quitMessage(component);
    }
}
