package com.shaneschulte.mc.clanarena.commands;

import com.shaneschulte.mc.clanarena.arena.SetupArena;
import com.shaneschulte.mc.clanarena.arena.SetupArena.ArenaSetupState;
import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class CreateCmd implements CmdProperties, Listener {

    private static HashMap<String, SetupArena> playersArenas = new HashMap<>();

    @Override
    public void perform (Player p, String allArgs, String[] args) {
        // if just /ca create
        if (args.length <= 1) {
            MsgUtils.error(p, "please specify a " + MsgUtils.Colors.VARIABLE + "name");
        }

        // if player is already creating an arena
        else if (isPlayerAlreadyCreating(p)) {
            MsgUtils.error(p, "You are already in the middle of creating an arena. Scroll up in chat if you need help or disconnect // die to reset this process");
        }

        // if /ca create <name>
        else {
            String arenaName = args[1];
            playersArenas.put(p.getName(), new SetupArena(p, arenaName));
            MsgUtils.success(p,"Creating arena \"" + MsgUtils.Colors.VARIABLE + arenaName + MsgUtils.Colors.SUCCESS + "\"");
            MsgUtils.sendMessage(p, MsgUtils.Colors.VARIABLE + "Left Click " + MsgUtils.Colors.INFO + "the first point in the " + MsgUtils.Colors.VARIABLE + "Arena" + MsgUtils.Colors.INFO + "'s region");
            MsgUtils.sendMessage(p, MsgUtils.Colors.VARIABLE + "Right Click " + MsgUtils.Colors.INFO + "the second point in the " + MsgUtils.Colors.VARIABLE + "Arena" + MsgUtils.Colors.INFO + "'s region");
        }

    }

    /**
     * Used to set the region in stage 01
     */
    @EventHandler
    public void onPlayerInteract (PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (!(playersArenas.containsKey(p.getName()))) return;

        // if player is in the middle of designating the 2 points
        if (isPlayerAlreadyCreating(p)) {

            // on left click block
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.setCancelled(true);
                getSetupArena(p).setRegionPos1(event.getClickedBlock().getLocation());
                if (getSetupArena(p).isDoneSelectingRegion())
                    MsgUtils.sendMessage(p, "Type \"" + MsgUtils.Colors.SUCCESS + "done" + MsgUtils.Colors.INFO + "\" in chat when done");
            }

            // on right click block
            else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
                getSetupArena(p).setRegionPos2(event.getClickedBlock().getLocation());
                if (getSetupArena(p).isDoneSelectingRegion())
                    MsgUtils.sendMessage(p, "Type \"" + MsgUtils.Colors.SUCCESS + "done" + MsgUtils.Colors.INFO + "\" in chat when done");
            }
        }
    }

    /**
     * Used to transition from stage 01 -> stage 02 ("done")
     * Used to transition from stage 02 -> complete ("done")
     * Used to add spawn points in stage 02 -> "add"
     * Used to transition which team is having spawn points updated -> "next"
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (!(playersArenas.containsKey(p.getName()))) return;

        // if player is in the middle of designating the 2 points
        if (event.getMessage().equalsIgnoreCase("done") && getSetupArena(p).getArenaState().equals(ArenaSetupState.SELECTING_REGION) && getSetupArena(p).isDoneSelectingRegion()) {
            event.setCancelled(true);

            // switch to phase 02
            MsgUtils.sendMessage(p, "Next, stand where you want team1 to spawn and type \"" + MsgUtils.Colors.SUCCESS + "add" + MsgUtils.Colors.INFO + "\"");
            getSetupArena(p).setArenaState(ArenaSetupState.SELECTING_TEAM1_SPAWNS);
        }

        // if selecting spawns
        else if (event.getMessage().equalsIgnoreCase("add") && (getSetupArena(p).getArenaState().equals(ArenaSetupState.SELECTING_TEAM1_SPAWNS) || getSetupArena(p).getArenaState().equals(ArenaSetupState.SELECTING_TEAM2_SPAWNS))) {
            event.setCancelled(true);

            // if team 01 spawns
            if (getSetupArena(p).getArenaState().equals(ArenaSetupState.SELECTING_TEAM1_SPAWNS)) {

                // add to the list
                getSetupArena(p).addFirstTeamSpawn(p.getLocation());
            }

            // if team 02 spawns
            else {

                // add to the list
                getSetupArena(p).addSecondTeamSpawn(p.getLocation());
            }
        }

        else if (event.getMessage().equalsIgnoreCase("next") && getSetupArena(p).getArenaState().equals(ArenaSetupState.SELECTING_TEAM1_SPAWNS) && getSetupArena(p).canMoveOnFromSettingFirstTeamSpawns()) {
            event.setCancelled(true);
            getSetupArena(p).setArenaState(ArenaSetupState.SELECTING_TEAM2_SPAWNS);
            MsgUtils.sendMessage(p, "Working on team2 spawns now...");
        }

        else if (event.getMessage().equalsIgnoreCase("done") && getSetupArena(p).getArenaState().equals(ArenaSetupState.SELECTING_TEAM2_SPAWNS) && getSetupArena(p).canMoveOnFromSettingSecondTeamSpawns()) {
            event.setCancelled(true);

            //TODO: write and initialize arena to file in SetupArena create() // hide() method
            //TODO: spawn points have to be in arena
            //TODO: arena vert region? yes // no

            MsgUtils.success(p, "Arena " + MsgUtils.Colors.VARIABLE + getSetupArena(p).getArenaName() + MsgUtils.Colors.SUCCESS + " created!");
            exitArenaSetup(p);
        }
    }

    @EventHandler
    public void onPlayerQuit (PlayerQuitEvent event) {
        if (isPlayerAlreadyCreating(event.getPlayer()))
            exitArenaSetup(event.getPlayer());
    }

    @EventHandler
    public void onChangedWorld (PlayerChangedWorldEvent event) {
        if (isPlayerAlreadyCreating(event.getPlayer()))
            exitArenaSetup(event.getPlayer());
    }

    @EventHandler
    public void onPlayerIsKilled (PlayerDeathEvent event) {
        if (isPlayerAlreadyCreating(event.getEntity()))
            exitArenaSetup(event.getEntity());
    }

    /**
     * Returns the SetupArena that p is currently setting up
     * @param p the player in question
     * @return the player's current setup arena, null if empty
     */
    private SetupArena getSetupArena(Player p) {
        return playersArenas.get(p.getName());
    }

    /**
     * Cancels the creation process for <name>
     * @param p the player being reset
     */
    private void exitArenaSetup(Player p) {
        getSetupArena(p).hide();
        playersArenas.remove(p.getName());
    }

    /**
     * Gets weather a player is currently in the creation process
     * @param p the player in question
     * @return is the player currently creating an arena?
     */
    private boolean isPlayerAlreadyCreating(Player p) {
        return (playersArenas.containsKey(p.getName()));
    }

    @Override
    public String getCommand() {
        return "create";
    }

    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public String getUsage() {
        return "/ca create <name>";
    }

    @Override
    public String getHelpMessage() {
        return "create an arena";
    }

    @Override
    public String getPermission() {
        return "clanarena.create";
    }
}
