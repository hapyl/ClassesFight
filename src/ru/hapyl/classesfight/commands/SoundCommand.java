package ru.hapyl.classesfight.commands;

import kz.hapyl.spigotutils.module.chat.Chat;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.hapyl.classesfight.utils.SoundLib;
import ru.hapyl.classesfight.utils.Validator;

import java.util.Collections;
import java.util.List;

public class SoundCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("sound")) {

            if (!(commandSender instanceof Player)) return true;

            Player player = (Player) commandSender;

            // format => /sound (Sound) (Pitch)
            if (args.length == 2) {

                Sound sound = Validator.getEnumValue(Sound.class, args[0]);
                float pitch = (float) Validator.getDouble(args[1]);

                if (pitch < 0.0D || pitch > 2.0D) {
                    Chat.sendMessage(player, "&cInvalid Argument. Pitch must be float type and between 0.0 and 2.0!");
                    return true;
                }

                if (sound == null) {
                    player.sendMessage("Sound not found.");
                    return true;
                }

                SoundLib.play(sound, pitch, player);
                Chat.sendMessage(player, "&aPlayed sound %s with pitch %s.", sound.name().toLowerCase(), pitch);

            } else Chat.sendMessage(player, "&cMissing Arguments. Usage => /sound (Sound) (Float)");

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("sound")) {
            if (args.length == 1) {
                final List<String> list = Chat.tabCompleterSort(Chat.arrayToList(Sound.values()), args);
                // non strict item search
                String last = args[args.length - 1].toLowerCase();
                for (Sound value : Sound.values()) {
                    String str = value.name().toLowerCase();
                    if (str.contains(last)) list.add(str);
                }
                return list;
            }

            if (args.length == 2) {
                return Collections.singletonList("1.0");
            }
        }
        return null;
    }
}
