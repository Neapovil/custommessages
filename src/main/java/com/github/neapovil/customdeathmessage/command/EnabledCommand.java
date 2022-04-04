package com.github.neapovil.customdeathmessage.command;

import com.github.neapovil.customdeathmessage.CustomDeathMessage;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.BooleanArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;

public final class EnabledCommand
{
  private static CustomDeathMessage plugin = CustomDeathMessage.getInstance();

  public static void register()
  {
    new CommandAPICommand("custommessages")
        .withPermission(CustomDeathMessage.ADMIN_COMMAND_PERMISSION)
        .withArguments(new LiteralArgument("enabled"))
        .withArguments(new BooleanArgument("boolean"))
        .executes((sender, args) -> {
          final boolean bool = (boolean) args[0];

          plugin.getFileConfig().set("enabled", bool);

          sender.sendMessage("CustomMessages enabled changed to: " + bool);
        })
        .register();
  }
}
