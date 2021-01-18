package com.bill.achshow;


import java.util.Objects;

import com.bill.achshow.command.DisplayCommand;
import com.bill.achshow.event.PlayerNameEvent;
import com.bill.achshow.manager.AdvancementManager;
import com.bill.achshow.manager.DataManager;
import com.bill.achshow.manager.MessageManager;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.chat.Chat;

public final class App extends JavaPlugin {

    private AdvancementManager  advancementManager;
    private MessageManager      messageManager;
    private DataManager         dataManager;
    @Override public void onEnable() {


        saveDefaultConfig();
        

        advancementManager  = new AdvancementManager( this );
        messageManager      = new MessageManager();
        dataManager         = new DataManager( this );
        

        DisplayCommand command = new DisplayCommand( this );

        Objects.requireNonNull( getCommand("advancementDisplay") ).setExecutor( command );
        Objects.requireNonNull( getCommand("advancementDisplay") ).setTabCompleter( command );

        
        Objects.requireNonNull( getCommand("ad") ).setExecutor( command );
        Objects.requireNonNull( getCommand("ad") ).setTabCompleter( command );

        getServer().getPluginManager().registerEvents(new PlayerNameEvent(advancementManager), this);

    }

    @Override
    public void onDisable() {
        dataManager.savePlayerData();
    }
    public AdvancementManager getAdvancementManager() {
        return advancementManager;
    }
    public MessageManager getMessageManager() {
        return messageManager;
    }
    public DataManager getDataManager() {
        return dataManager;
    }



}
