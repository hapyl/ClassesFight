package ru.hapyl.classesfight.feature;

import kz.hapyl.spigotutils.module.chat.Chat;
import kz.hapyl.spigotutils.module.util.BukkitUtils;
import kz.hapyl.spigotutils.module.util.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.hapyl.classesfight.GameManager;
import ru.hapyl.classesfight.runnable.GameTask;
import ru.hapyl.classesfight.utils.sou;

import java.util.List;
import java.util.Set;

public class HealthIndicator {

	public HealthIndicator() {
		new GameTask() {
			@Override
			public void run() {

				final StringBuilder builder = new StringBuilder();
				final Set<Player> players = GameManager.current().getPlayers();

				int i = 0;
				for (final Player player : players) {
					final String health = formatHealth(player);
					builder.append(Placeholder.format("&a{playerName} &7- {health}", getShortName(player), health));
					if (i != (players.size() - 1)) {
						builder.append(" &8| ");
					}
					++i;
				}

				final String finalString = builder.toString();
				Spectator.getSpectators().forEach(player -> Chat.sendActionbar(player, finalString));

			}
		}.runTaskTimer(10, 10);
	}

	private String getShortName(Player player) {
		return player.getDisplayName().substring(0, 3);
	}

	private String formatHealth(Player player) {
		final double currentHealth = DamageFeature.getHealth(player);
		return ChatColor.RED + BukkitUtils.decimalFormat(currentHealth / 2) + " ❤";
	}
}
