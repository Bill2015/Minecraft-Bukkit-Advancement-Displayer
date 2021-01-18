package com.bill.achshow.manager;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.bill.achshow.App;
import com.bill.achshow.util.AdvancementData;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataManager {
    private App plugin;
    private FileConfiguration dataConfig;
    private File dataFile;

    public DataManager(App plugin){
        this.plugin = plugin;
        createDataFile();
    }

    public ConcurrentHashMap<UUID, AdvancementData> loadPlayerData(){
        ConcurrentHashMap<UUID, AdvancementData> playerMap = new ConcurrentHashMap<>();

        for(int i = 0; ; i++ ){
            Object object = (MemorySection)dataConfig.get( String.join(".", "data", String.valueOf(i) ) );
            if( object instanceof MemorySection ){
                MemorySection data = (MemorySection)object;
                
                UUID uuid =  UUID.fromString( data.getString("player") );
                for (AdvancementData advancementData : plugin.getAdvancementManager().getAdvencementList()  ) {
                    if( advancementData.getNamespacedKey().getKey().equals( data.getString("advancement") ) ){
                        playerMap.put( uuid , advancementData );
                        break;
                    }
                }
            }
            else break;
        }
        if (dataFile.delete()) {
            plugin.getLogger().info("Loaded saved data!");
        }
        return playerMap;
    }

    public void savePlayerData() {
        try {
            AdvancementManager advancementManager = plugin.getAdvancementManager();
            ConcurrentHashMap<UUID, AdvancementData> playerMap = advancementManager.getPlayerAdvanceMap();
            int i = 0;
            for ( UUID uuid : playerMap.keySet() ) {
                String parent = String.join(".", "data", Integer.toString(i));
                dataConfig.set(parent + ".player", uuid.toString() );
                dataConfig.set(parent + ".advancement", playerMap.get( uuid ).getNamespacedKey().getKey() );
                i += 1;
            }

            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDataFile() {
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        if ( !dataFile.exists() ) {
            try {
                if (dataFile.createNewFile()) {
                    plugin.getLogger().info("Loaded data file!");
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        dataConfig = new YamlConfiguration();
        try {
            dataConfig.load(dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
