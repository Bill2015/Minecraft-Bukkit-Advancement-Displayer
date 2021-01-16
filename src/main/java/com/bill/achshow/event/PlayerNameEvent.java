package com.bill.achshow.event;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.bill.achshow.manager.AdvancementManager;
import com.bill.achshow.util.AdvancementData;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerNameEvent implements Listener {

    private final AdvancementManager advancementManager;
    public PlayerNameEvent(AdvancementManager advancementManager) {
        this.advancementManager = advancementManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ConcurrentHashMap<UUID, AdvancementData> advanceMap = advancementManager.getPlayerAdvanceMap();
        Player player = event.getPlayer();
        //if the player are setting before
        if( advanceMap.containsKey( player.getUniqueId() ) ){
            AdvancementData data = advanceMap.get(  player.getUniqueId() );
            String name = String.join(" ", data.toString(), player.getName() );
            player.setDisplayName( name );
            player.setPlayerListName( name );
            player.setCustomName( name );
            player.setCustomNameVisible( true );
        }
    }

}
