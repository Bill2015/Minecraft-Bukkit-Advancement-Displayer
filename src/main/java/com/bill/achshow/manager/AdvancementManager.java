package com.bill.achshow.manager;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.bill.achshow.App;
import com.bill.achshow.util.AdvancementData;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class AdvancementManager {
    /** save all of advancement */
    private final ArrayList<AdvancementData> advancementList = new ArrayList<>();
    /** save the advancement of player showing */
    private ConcurrentHashMap<UUID, AdvancementData> playerAdvanceMap = new ConcurrentHashMap<>();
    private final App plugin;
    private final String TYPES[] = {"story", "nether", "end", "adventure", "husbandry"};
    public AdvancementManager(App plugin){
        this.plugin = plugin;
        advancementLoader();
        loadPlayerData();
    }

    private void loadPlayerData() {
        plugin.getLogger().info("Waiting 2 seconds before loading saved data.");
        new BukkitRunnable() {
            @Override
            public void run() {
                ConcurrentHashMap<UUID, AdvancementData> data  = plugin.getDataManager().loadPlayerData();
                if( data != null ){
                    playerAdvanceMap = data;
                }
            }
        }.runTaskLater(plugin, 40L);
    }

    private void advancementLoader(){
        FileConfiguration config = plugin.getConfig();
        for (String type : TYPES) {
            for(int i = 0; ; i++ ){
                Object object = config.get( String.join(".", "advancements", type, String.valueOf(i) ) );
    
                // check is a instance memorySection
                if( object instanceof MemorySection ){
                    MemorySection data = (MemorySection)object;
    
                    //get data of achievement
                    String key  = String.join("/", type,  data.getString("name") );
                    String name = data.getString("chinese");
                    int level   = data.getInt("level");
                    AdvancementData advancementData = new AdvancementData( key, name, level );
                    advancementList.add( advancementData );
                }
                else break;
            }
        }
    }

    public ArrayList<AdvancementData> getAdvencementList() {
        return advancementList;
    }
    public ConcurrentHashMap<UUID, AdvancementData> getPlayerAdvanceMap() {
        return playerAdvanceMap;
    }
    public void getPlayerAdvanceMap( ConcurrentHashMap<UUID, AdvancementData> map ) {
        this.playerAdvanceMap = map;
    }

    public AdvancementData getAdvacementData( String chineseName ){
        for( AdvancementData data : advancementList ){
            if( data.getChinese().equals( chineseName )  )
                return data;
        }
        return null;
    }



    

}
