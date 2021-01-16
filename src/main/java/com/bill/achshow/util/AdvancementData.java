package com.bill.achshow.util;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;

public class AdvancementData {
    private final NamespacedKey namespacedKey;
    private final String chinese;
    private final int level;
    private final static ChatColor LEVEL_COLOR[] = { ChatColor.GRAY, ChatColor.GREEN, ChatColor.BLUE, ChatColor.DARK_PURPLE, ChatColor.GOLD };
    public AdvancementData( String namespace, String chinese, int level ){
        this.namespacedKey  = new NamespacedKey( NamespacedKey.MINECRAFT , namespace );
        this.chinese        = chinese;
        this.level          = level;
    }
    public NamespacedKey getNamespacedKey() {
        return namespacedKey;
    }
    public String getChinese() {
        return chinese;
    }
    public int getLevel() {
        return level;
    }
    @Override
    public String toString(){
        return String.join("", String.valueOf( LEVEL_COLOR[ level ] ), " [", chinese, "] ", String.valueOf( ChatColor.RESET )  );
    }
}
