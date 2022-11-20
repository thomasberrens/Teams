package org.rubixstudios.teams.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public abstract String getPrefix();

    public abstract String getDescription();

    public abstract String getName();

    public abstract boolean MeetsCondition(CommandSender sender, org.bukkit.command.Command command, String label, String[] args);

    public abstract void Run(CommandSender sender, org.bukkit.command.Command command, String label, String[] args);
}
