package com.github.neapovil.customdeathmessage;

import java.io.File;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.github.neapovil.customdeathmessage.command.EditCommand;
import com.github.neapovil.customdeathmessage.command.EnabledCommand;
import com.github.neapovil.customdeathmessage.listener.PlayerDeathListener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

public final class CustomDeathMessage extends JavaPlugin implements Listener
{
  private static CustomDeathMessage instance;
  private FileConfig config;
  public static final String ADMIN_COMMAND_PERMISSION = "custommessages.command.admin";

  @Override
  public void onEnable()
  {
    instance = this;

    this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);

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

  public static CustomDeathMessage getInstance()
  {
    return instance;
  }

  public FileConfig getFileConfig()
  {
    return this.config;
  }

  public boolean isCustomDeathMessageEnabled()
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
