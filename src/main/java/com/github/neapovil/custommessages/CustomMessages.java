package com.github.neapovil.custommessages;

import java.io.File;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.github.neapovil.custommessages.command.EditCommand;
import com.github.neapovil.custommessages.command.EnabledCommand;
import com.github.neapovil.custommessages.listener.PlayerDeathListener;
import com.github.neapovil.custommessages.listener.PlayerJoinListener;
import com.github.neapovil.custommessages.listener.PlayerQuitListener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

public final class CustomMessages extends JavaPlugin implements Listener
{
    private static CustomMessages instance;
    private FileConfig config;
    public static final String ADMIN_COMMAND_PERMISSION = "custommessages.command.admin";

    @Override
    public void onEnable()
    {
        instance = this;

        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);

        this.saveResource("custommessages.json", false);

        this.config = FileConfig.builder(new File(this.getDataFolder(), "custommessages.json"))
                .autoreload()
                .autosave()
                .build();
        this.config.load();

        EditCommand.register();
        EnabledCommand.register();
    }

    @Override
    public void onDisable()
    {
    }

    public static CustomMessages getInstance()
    {
        return instance;
    }

    public FileConfig getFileConfig()
    {
        return this.config;
    }

    public boolean isCustomMessagesEnabled()
    {
        return this.config.get("enabled");
    }

    public String translate(String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public Component deserialize(String string)
    {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string);
    }
}
