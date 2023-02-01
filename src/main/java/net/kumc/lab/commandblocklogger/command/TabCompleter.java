package net.kumc.lab.commandblocklogger.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    /**
     * Tab
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equals("cblogger")) {
            if (args.length == 1) {
                return (sender.hasPermission("cblogger")
                        ? Stream.of("save", "list", "search", "tp", "delete", "help")
                        : Stream.of("save", "list", "search", "tp", "delete", "help"))
                        .filter(e -> e.startsWith(args[0])).collect(Collectors.toList());
            }
            if (args.length == 2 && (args[0].equals("tp") || args[0].equals("delete"))) {
                return (sender.hasPermission("cblogger")
                        ? Stream.of("<id>")
                        : Stream.of("<id>"))
                        .filter(e -> e.startsWith(args[1])).collect(Collectors.toList());
            }
            if (args.length == 2 && args[0].equals("search")) {
                return (sender.hasPermission("cblogger")
                        ? Stream.of("<検索単語>")
                        : Stream.of("<検索単語>"))
                        .filter(e -> e.startsWith(args[1])).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }
}
