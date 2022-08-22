package me.devgabi.tempmute.listener;

import me.devgabi.tempmute.entity.User;
import me.devgabi.tempmute.datasource.UserDao;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public final class PlayerListener implements Listener {

    private final UserDao userDao;

    @EventHandler
    @SuppressWarnings("unused")
    public void onPlayerJoin(PlayerJoinEvent event) {
        String username = event.getPlayer().getName();

        User user = userDao.findByName(username);

        if(user == null) return;

        userDao.getUserCache().registerOrUpdate(username, user);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onPlayerQuit(PlayerQuitEvent event) {
        String username = event.getPlayer().getName();

        userDao.getUserCache().remove(username);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        User user = userDao.getUserCache().findByName(player.getName());

        if(!user.isMuted()) return;

        event.setCancelled(true);
        player.sendMessage("§cYou can't send messages while you're muted!");
    }

}
