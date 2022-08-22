package me.devgabi.tempmute.command;

import me.devgabi.tempmute.datasource.UserDao;
import me.devgabi.tempmute.entity.User;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public final class MuteCommand implements CommandExecutor {

    private final UserDao userDao;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 1)  {
            sender.sendMessage("§cPlease specify an username!");

            return true;
        }

        String username = args[0];
        User user = userDao.findByName(username);

        if(user == null) {
            sender.sendMessage("§cThe user don't exists in database!");

            return true;
        }

        userDao.updateUserEntity(username, true);

        sender.sendMessage(String.format("§aUser %s has been successfully muted", username));

        return true;
    }
}
