package com.github.neapovil.customdeathmessage;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class CustomDeathMessage extends JavaPlugin implements Listener
{
    private static CustomDeathMessage instance;

    @Override
    public void onEnable()
    {
        instance = this;

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable()
    {
    }

    public static CustomDeathMessage getInstance()
    {
        return instance;
    }

    @EventHandler
    private void playerDeath(PlayerDeathEvent event)
    {
        final Component component = Component.text(event.getPlayer().getName(), NamedTextColor.DARK_AQUA);

        final Player killer = event.getPlayer().getKiller();

        if (killer == null)
        {
            event.deathMessage(component.append(Component.text(" died", NamedTextColor.DARK_RED)));
        }
        else
        {
            final Component component1 = Component.text(killer.getName(), NamedTextColor.DARK_AQUA);
            final Component component2 = Component.text(" smashed ", NamedTextColor.DARK_RED);
            final Component component3 = Component.text(" using ", NamedTextColor.DARK_RED);

            final ItemStack itemstack = killer.getInventory().getItemInMainHand();

            final Component component4 = itemstack.getType().equals(Material.AIR)
                    ? Component.text("Hands", NamedTextColor.GOLD)
                    : itemstack.displayName().color(NamedTextColor.GOLD).hoverEvent(itemstack.asHoverEvent());

            final Component built = component1.append(component2)
                    .append(component)
                    .append(component3)
                    .append(component4);

            event.deathMessage(built);
        }
    }
}
