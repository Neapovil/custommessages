package com.github.neapovil.custommessages.command;

import com.github.neapovil.custommessages.CustomMessages;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.BooleanArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;

public final class EnabledCommand
{
    private static CustomMessages plugin = CustomMessages.getInstance();

    public static void register()
    {
        new CommandAPICommand("custommessages")
                .withPermission(CustomMessages.ADMIN_COMMAND_PERMISSION)
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
