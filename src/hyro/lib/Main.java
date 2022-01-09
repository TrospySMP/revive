package hyro.lib;

import com.gamerduck.LifeStealAPI;
import com.gamerduck.enums.LifeReason;
import com.gamerduck.events.LifeGainEvent;
import com.gamerduck.objects.LifeStealPlayer;
import hyro.lib.utils.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Message.consoleSend("&8----------------------");
        Message.consoleSend("&dRevive");
        Message.consoleSend("&aLoaded");
        Message.consoleSend("&8----------------------");

        getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        Message.consoleSend("&8----------------------");
        Message.consoleSend("&dRevive");
        Message.consoleSend("&cUnloaded");
        Message.consoleSend("&8----------------------");
    }

    @EventHandler
    public void revive(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (e.getInventory().getType() == InventoryType.ANVIL) {
            if(e.getSlotType() == InventoryType.SlotType.RESULT) {
                if (e.getCurrentItem().getType() == Material.DRAGON_EGG) {
                    String name = e.getCurrentItem().getItemMeta().getDisplayName();
                    if (getServer().getPlayer(name) == null) {
                        Message.sendToPlayer(player, "&d[REVIVE] &fHráč &d" + name + "&f je offline");
                        return;
                    }

                    getServer().dispatchCommand(getServer().getConsoleSender(), "gamemode survival "+name);
                    LifeGainEvent event = new LifeGainEvent(getServer().getPlayer(name), LifeReason.COMMAND, 20.0);
                    getServer().getPluginManager().callEvent(event);

                    e.getInventory().getItem(0).setType(Material.AIR);
                    e.getInventory().getItem(e.getSlot()).setType(Material.AIR);

                    Message.broadcastMessage("&d[REVIVE] &fHráč &d"+player.getName()+"&f oživil hráča &d"+name);
                }
            }
        }
    }
}
