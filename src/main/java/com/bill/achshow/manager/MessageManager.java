package com.bill.achshow.manager;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MessageManager {
    private final static String TITLE =  ChatColor.COLOR_CHAR + "9[成就展示器] ";

    public void sendAdvancementNotFound(Player player, String name){
        player.sendMessage( TITLE + ChatColor.RED + " 查無關於 " + name +  " 的成就" );
        player.getWorld().playSound( player.getLocation() , Sound.BLOCK_ANVIL_LAND , 1.0f, 1.5f );
    }

    public void sendAdvancementSet( Player player, String name ){
        player.sendMessage( TITLE + ChatColor.WHITE + "你設定展示成就為：" + name );
        player.getWorld().playSound( player.getLocation() , Sound.ENTITY_PLAYER_LEVELUP , 1.0f, 1.5f );
    }
    public void sendAdvancementNotUnlock( Player player, String name ){
        player.sendMessage( TITLE + ChatColor.RED + " 抱歉，你並沒有解鎖 " + name + ChatColor.RED + " 成就" );
        player.getWorld().playSound( player.getLocation() , Sound.BLOCK_ANVIL_LAND , 1.0f, 1.5f );
    }
    public void sendAdvancementOwnList( Player player ){
        player.sendMessage( TITLE + ChatColor.WHITE + "以下是你解所的成就：" );
        player.getWorld().playSound( player.getLocation() , Sound.ENTITY_CHICKEN_EGG , 1.0f, 1.0f );
    }
    public void sendAdvancementNoInput( Player player ){
        player.sendMessage( TITLE + ChatColor.RED + "請輸入要顯示的成就" );
        player.getWorld().playSound( player.getLocation() , Sound.BLOCK_ANVIL_LAND , 1.0f, 1.5f );
    }
    public void sendActionNoInput( Player player ){
        player.sendMessage( TITLE + ChatColor.RED + "請輸入要執行的動作 set | showAll" );
        player.getWorld().playSound( player.getLocation() , Sound.BLOCK_ANVIL_LAND , 1.0f, 1.5f );
    }
    public void sendActionNotFound( Player player ){
        player.sendMessage( TITLE + ChatColor.RED + "查無此動作，請輸入要執行的動作 set | showAll" );
        player.getWorld().playSound( player.getLocation() , Sound.BLOCK_ANVIL_LAND , 1.0f, 1.5f );
    }
}
