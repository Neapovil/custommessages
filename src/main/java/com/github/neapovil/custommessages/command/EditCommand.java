package com.github.neapovil.custommessages.command;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.github.neapovil.custommessages.CustomMessages;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.StringArgument;

public final class EditCommand
{
  private static final CustomMessages plugin = CustomMessages.getInstance();

  public static void register()
  {
    new CommandAPICommand("custommessage")
        .withPermission(CustomMessages.ADMIN_COMMAND_PERMISSION)
        .withArguments(new LiteralArgument("edit"))
        .withArguments(new StringArgument("path").replaceSuggestions(info -> {
          final UnmodifiableConfig path = plugin.getFileConfig().get("custommessages");
          return path.entrySet().stream().map(i -> i.getKey()).toArray(String[]::new);
        }))
        .withArguments(new GreedyStringArgument("message").replaceSuggestions(info -> {
          final String path = (String) plugin.getFileConfig().get("custommessages." + info.previousArgs()[0]);

          if (path == null)
          {
            return new String[] {};
          }

          return new String[] { path };
        }))
        .executes((sender, args) -> {
          final String path = (String) args[0];
          final String message = (String) args[1];

          if (plugin.getFileConfig().get("custommessages." + path) == null)
          {
            CommandAPI.fail("This custom message path doesn't exist");
          }

          plugin.getFileConfig().set("custommessages." + path, message);

          sender.sendMessage("Custom message " + path + " changed to " + plugin.translate(message));
        })
        .register();
  }
}
