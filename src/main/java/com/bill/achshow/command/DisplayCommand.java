package com.bill.achshow.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.bill.achshow.App;
import com.bill.achshow.manager.AdvancementManager;
import com.bill.achshow.manager.MessageManager;
import com.bill.achshow.util.AdvancementData;

import org.bukkit.Server;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class DisplayCommand implements CommandExecutor, TabCompleter {
    private final App plugin;
    private final AdvancementManager advancementManager;
    private final MessageManager messageManager;
    private final List<String> advancementTabList;
    private final List<String> actionTabList;
    public final static String CHECK_ACHIEVEMENT    = "showDone";
    public final static String SET_ACHIEVEMENT      = "set";

    public DisplayCommand(App plugin) {
        this.plugin = plugin;
        this.advancementManager = plugin.getAdvancementManager();
        this.messageManager     = plugin.getMessageManager();
        this.advancementTabList = new ArrayList<>();
        this.actionTabList      = new ArrayList<>();
        //generate tabl compelete on action
        actionTabList.add( SET_ACHIEVEMENT );
        actionTabList.add( CHECK_ACHIEVEMENT );

        //generate tab compelete
        for (AdvancementData data : advancementManager.getAdvencementList() ) {
            advancementTabList.add( data.getChinese() );
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // check arg length
            if (args.length >= 1 ) {
                // player show his all advancement 
                if (args[0].equalsIgnoreCase( CHECK_ACHIEVEMENT ) ) {

                    Server server = plugin.getServer();
                    ArrayList<AdvancementData> list = advancementManager.getAdvencementList();

                    messageManager.sendAdvancementOwnList( player );
                    // get whole advancement
                    for (AdvancementData data : list) {
                        AdvancementProgress progress = player.getAdvancementProgress( server.getAdvancement(data.getNamespacedKey()) );
                        if( progress.isDone() ){
                            player.sendMessage( data.toString() );
                        }
                    }
                }
                // player set advancement to display
                else if( args[0].equalsIgnoreCase( SET_ACHIEVEMENT ) ){
                    //check args is not null
                    if( args.length >= 2 ){
                        //check this advancement is exist
                        AdvancementData data = advancementManager.getAdvacementData( args[1] );
                        if( data == null ){
                            messageManager.sendAdvancementNotUnlock( player, args[1] );
                            return true;
                        }
                        //check the player is have this advancement
                        AdvancementProgress progress = player.getAdvancementProgress( plugin.getServer().getAdvancement( data.getNamespacedKey() ) );
                        if( progress.isDone() ){
                            messageManager.sendAdvancementSet(player, data.toString() );
                            String name = String.join(" ", data.toString(), player.getName() );
                            player.setDisplayName( name );
                            player.setPlayerListName( name );
                            player.setCustomName( name );
                            player.setCustomNameVisible( true );
                            

                            //update player advancement display to a map
                            ConcurrentHashMap<UUID, AdvancementData> advanceMap = advancementManager.getPlayerAdvanceMap();
                            advanceMap.put( player.getUniqueId(), data );
                        }
                        else{
                            messageManager.sendAdvancementNotUnlock( player , data.toString() );
                        }
                    }
                    else{
                        messageManager.sendAdvancementNoInput( player );
                    }
                }
                else{
                    messageManager.sendActionNotFound( player );
                }
            }
            else{
                messageManager.sendActionNoInput( player );
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        //create new array
        final List<String> completions = new ArrayList<>();
        if( args.length == 1 ){
            //copy matches of first argument from list (ex: if first arg is 'm' will return just 'minecraft')
            StringUtil.copyPartialMatches(args[0], actionTabList, completions);
        }
        else if( args.length == 2 ){
            //setting advancement
            if( args[0].equals( SET_ACHIEVEMENT )  ){

                //copy matches of first argument from list (ex: if first arg is 'm' will return just 'minecraft')
                StringUtil.copyPartialMatches(args[1], advancementTabList, completions);

                if( sender instanceof Player ){
                    Player player = (Player) sender;
                    Server server = plugin.getServer();
                    ArrayList<AdvancementData> list = advancementManager.getAdvencementList();
    
                    // remove the advancement in tab compelete, if player havn't done
                    for (AdvancementData data : list) {
                        AdvancementProgress progress = player.getAdvancementProgress(server.getAdvancement(data.getNamespacedKey()));
                        if( progress.isDone() == false ){
                            completions.removeIf( node -> node.equals( data.getChinese() ) );
                        }
                    }
                }
            }
        }
        //sort the list
        Collections.sort(completions);
        return completions;
    }
    
}
