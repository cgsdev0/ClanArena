package com.shaneschulte.mc.clanarena.commands;

import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateCmd implements CmdProperties, Listener {

    private static HashMap<String, BossBar> currentPlayerBossBars = new HashMap<>();
    private static HashMap<String, String> currentArenaNameByPlayer = new HashMap<>();
    private static int minSpawns = 6;

    // Stage 01: selecting region
    private static ArrayList<String> playersInTheMiddleOfDesignatingThe2PointsNeededToCreateAnArenaRegion = new ArrayList<>();

    private static HashMap<String, Location> currentPos1ByPlayer = new HashMap<>();
    private static HashMap<String, Location> currentPos2ByPlayer = new HashMap<>();

    // Stage 02: selecting spawn points
    private static ArrayList<String> playersInTheMiddleOfSelectingSpawnPoints = new ArrayList<>();
    private static ArrayList<String> playersAddingSecondTeamSpawnPoints = new ArrayList<>();

    private static HashMap<String, ArrayList> currentTeam1SpawnsByPlayer = new HashMap<>();
    private static HashMap<String, ArrayList> currentTeam2SpawnsByPlayer = new HashMap<>();

    @Override
    public void perform (Player p, String allArgs, String[] args) {
        // if just /ca create
        if (args.length <= 1) {
            MsgUtils.error(p, "please specify a " + MsgUtils.Colors.VARIABLE + "name");
        }

        else if (isPlayerAlreadyCreating(p.getName())) {
            MsgUtils.error(p, "You are already in the middle of creating an arena. Scroll up in chat if you need help or disconnect // die to reset this process");
        }

        // if /ca create <name>
        else {
            String name = args[1];
            currentArenaNameByPlayer.put(p.getName(), args[1]);
            playersInTheMiddleOfDesignatingThe2PointsNeededToCreateAnArenaRegion.add(p.getName());
            MsgUtils.success(p,"Creating arena \"" + MsgUtils.Colors.VARIABLE + name + MsgUtils.Colors.SUCCESS + "\"");
            MsgUtils.sendMessage(p, MsgUtils.Colors.VARIABLE + "Left Click " + MsgUtils.Colors.INFO + "the first point in the " + MsgUtils.Colors.VARIABLE + "Arena" + MsgUtils.Colors.INFO + "'s region");
            MsgUtils.sendMessage(p, MsgUtils.Colors.VARIABLE + "Right Click " + MsgUtils.Colors.INFO + "the second point in the " + MsgUtils.Colors.VARIABLE + "Arena" + MsgUtils.Colors.INFO + "'s region");

            BossBar bar = Bukkit.getServer().createBossBar("PROGRESS", BarColor.PINK, BarStyle.SEGMENTED_12);
            currentPlayerBossBars.put(p.getName(), bar);
            bar.addPlayer(p);
            updateProgressBossBar(p.getName());
        }

    }

    /**
     * Used to set the region in stage 01
     */
    @EventHandler
    public void onPlayerInteract (PlayerInteractEvent event) {
        // define player
        Player player = event.getPlayer();

        // if player is in the middle of designating the 2 points
        if (isPlayerSelectingRegion(player.getName())) {

            // on left click block
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.setCancelled(true);
                currentPos1ByPlayer.put(player.getName(), event.getClickedBlock().getLocation());
                if (isDoneSelectingPositions(player.getName()))
                    MsgUtils.sendMessage(player, "Type \"" + MsgUtils.Colors.SUCCESS + "done" + MsgUtils.Colors.INFO + "\" in chat when done");
                updateProgressBossBar(player.getName());
            }

            // on right click block
            else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
                currentPos2ByPlayer.put(player.getName(), event.getClickedBlock().getLocation());
                if (isDoneSelectingPositions(player.getName()))
                    MsgUtils.sendMessage(player, "Type \"" + MsgUtils.Colors.SUCCESS + "done" + MsgUtils.Colors.INFO + "\" in chat when done");
                updateProgressBossBar(player.getName());
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
        Player player = event.getPlayer();

        // if player is in the middle of designating the 2 points
        if (isPlayerSelectingRegion(player.getName()) && isDoneSelectingPositions(player.getName()) && event.getMessage().equalsIgnoreCase("done")) {
            event.setCancelled(true);
            playersInTheMiddleOfDesignatingThe2PointsNeededToCreateAnArenaRegion.remove(player.getName());
            currentPos1ByPlayer.remove(player.getName());
            currentPos2ByPlayer.remove(player.getName());

            // switch to phase 02
            MsgUtils.sendMessage(player, "Next, stand where you want team1 to spawn and type \"" + MsgUtils.Colors.SUCCESS + "add" + MsgUtils.Colors.INFO + "\"");
            playersInTheMiddleOfSelectingSpawnPoints.add(player.getName());
            updateProgressBossBar(player.getName());
        }

        else if (isPlayerSelectingSpawns(player.getName()) && event.getMessage().equalsIgnoreCase("add")) {
            event.setCancelled(true);

            // if team 01 spawns
            if (!(playersAddingSecondTeamSpawnPoints.contains(player.getName()))) {

                // if no spawns set yet, add the first spawn and exit
                if (!(currentTeam1SpawnsByPlayer.containsKey(player.getName()))) {
                    ArrayList<Location> newList = new ArrayList<>();
                    newList.add(player.getLocation());
                    currentTeam1SpawnsByPlayer.put(player.getName(), newList);
                    updateProgressBossBar(player.getName());
                    return;
                }

                // if there are already spawns being set, add to the list
                ArrayList<Location> newList = currentTeam1SpawnsByPlayer.get(player.getName());
                newList.add(player.getLocation());
                currentTeam1SpawnsByPlayer.replace(player.getName(), newList);
                updateProgressBossBar(player.getName());

                if (currentTeam1SpawnsByPlayer.get(player.getName()).size() > minSpawns -1) MsgUtils.sendMessage(player, MsgUtils.Colors.VARIABLE + "" +
                    currentTeam1SpawnsByPlayer.get(player.getName()).size() + MsgUtils.Colors.INFO + " spawns for team1. Type \"" + MsgUtils.Colors.SUCCESS + "next" +
                    MsgUtils.Colors.INFO + "\" to move on");
            }

            // if team 02 spawns
            else {
                // if no spawns set yet, add the first spawn and exit
                if (!(currentTeam2SpawnsByPlayer.containsKey(player.getName()))) {
                    ArrayList<Location> newList = new ArrayList<>();
                    newList.add(player.getLocation());
                    currentTeam2SpawnsByPlayer.put(player.getName(), newList);
                    updateProgressBossBar(player.getName());
                    return;
                }

                // if there are already spawns being set, add to the list
                ArrayList<Location> newList = currentTeam2SpawnsByPlayer.get(player.getName());
                newList.add(player.getLocation());
                currentTeam2SpawnsByPlayer.replace(player.getName(), newList);
                updateProgressBossBar(player.getName());

                if (currentTeam2SpawnsByPlayer.get(player.getName()).size() > minSpawns -1) MsgUtils.sendMessage(player, MsgUtils.Colors.VARIABLE + "" +
                        currentTeam2SpawnsByPlayer.get(player.getName()).size() + MsgUtils.Colors.INFO + " spawns for team1. Type \"" + MsgUtils.Colors.SUCCESS + "done" +
                        MsgUtils.Colors.INFO + "\" to move on");
            }
        }

        else if (playersInTheMiddleOfSelectingSpawnPoints.contains(player.getName()) && !(currentTeam2SpawnsByPlayer.containsKey(player.getName())) && !(playersAddingSecondTeamSpawnPoints.contains(player.getName())) && event.getMessage().equalsIgnoreCase("next")) {
            event.setCancelled(true);
            playersAddingSecondTeamSpawnPoints.add(player.getName());
            MsgUtils.sendMessage(player, "Working on team2 spawns now...");
            updateProgressBossBar(player.getName());
        }

        else if (playersAddingSecondTeamSpawnPoints.contains(player.getName()) && currentTeam2SpawnsByPlayer.get(player.getName()).size() >= minSpawns && event.getMessage().equalsIgnoreCase("done")) {
            event.setCancelled(true);

            //TODO: create and write arena to file here
            //TODO: spawn points have to be in arena
            //TODO: arena vert region? yes // no

            MsgUtils.success(player, "Arena " + MsgUtils.Colors.VARIABLE + currentArenaNameByPlayer.get(player.getName()) + MsgUtils.Colors.SUCCESS + " created!");

            resetPlayerCreationProgress(player.getName());
        }
    }

    @EventHandler
    public void onPlayerQuit (PlayerQuitEvent event) {
        if (isPlayerAlreadyCreating(event.getPlayer().getName()))
            resetPlayerCreationProgress(event.getPlayer().getName());
    }

    @EventHandler
    public void onChangedWorld (PlayerChangedWorldEvent event) {
        if (isPlayerAlreadyCreating(event.getPlayer().getName()))
            resetPlayerCreationProgress(event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerIsKilled (PlayerDeathEvent event) {
        if (isPlayerAlreadyCreating(event.getEntity().getName()))
            resetPlayerCreationProgress(event.getEntity().getName());
    }

    /**
     * Cancels the creation process for <name>
     * @param name the player being reset
     */
    private void resetPlayerCreationProgress(String name) {
        playersInTheMiddleOfDesignatingThe2PointsNeededToCreateAnArenaRegion.remove(name);

        playersAddingSecondTeamSpawnPoints.remove(name);
        playersInTheMiddleOfSelectingSpawnPoints.remove(name);

        currentPos1ByPlayer.remove(name);
        currentPos2ByPlayer.remove(name);

        currentTeam1SpawnsByPlayer.remove(name);
        currentTeam2SpawnsByPlayer.remove(name);

        if (currentPlayerBossBars.containsKey(name)) currentPlayerBossBars.get(name).removeAll();
        currentPlayerBossBars.remove(name);

        currentArenaNameByPlayer.remove(name);
    }

    /**
     * Gets the current bossbar title in the creation process
     * @param name the player who's title is being retrieved
     * @return the current bossbar title
     */
    private String getCurrentPositionsStatus(String name) {
        String str = "PROGRESS";

        // if selecting a region
        if (isPlayerSelectingRegion(name)) {
            str = MsgUtils.Colors.INFO + "(&eL&7) - ";
            str += (currentPos1ByPlayer.containsKey(name)) ? (MsgUtils.Colors.SUCCESS) : (MsgUtils.Colors.ERROR);
            str += "&lTop Left &r" + MsgUtils.Colors.INFO + "click ";
            str += (currentPos2ByPlayer.containsKey(name)) ? (MsgUtils.Colors.SUCCESS) : (MsgUtils.Colors.ERROR);
            str += "&lBottom Right &r" + MsgUtils.Colors.INFO;
            str += "- (&eR&7)";
        }

        // if selecting spawn points
        if (isPlayerSelectingSpawns(name)) {
            int team1SpawnsLeft = 0, team2SpawnsLeft = 0;

            if (currentTeam1SpawnsByPlayer.containsKey(name)) team1SpawnsLeft = ((minSpawns - currentTeam1SpawnsByPlayer.get(name).size() < 0) ? 0 : minSpawns - currentTeam1SpawnsByPlayer.get(name).size());
            if (currentTeam2SpawnsByPlayer.containsKey(name)) team2SpawnsLeft = ((minSpawns - currentTeam2SpawnsByPlayer.get(name).size() < 0) ? 0 : minSpawns - currentTeam2SpawnsByPlayer.get(name).size());

            if (!(playersAddingSecondTeamSpawnPoints.contains(name))) {
                str = MsgUtils.Colors.HIGHLIGHT + "&lTeam1&r" + MsgUtils.Colors.INFO + ": " + MsgUtils.Colors.VARIABLE +
                        (team1SpawnsLeft) +
                    MsgUtils.Colors.INFO + " spawns left (\"" + MsgUtils.Colors.SUCCESS + "add" + MsgUtils.Colors.INFO + "\")";
            } else {
                str = MsgUtils.Colors.HIGHLIGHT + "&lTeam2&r" + MsgUtils.Colors.INFO + ": " + MsgUtils.Colors.VARIABLE +
                    (team2SpawnsLeft) +
                    MsgUtils.Colors.INFO + " spawns left (\"" + MsgUtils.Colors.SUCCESS + "add" + MsgUtils.Colors.INFO + "\")";
            }
        }

        return str;
    }

    /**
     * Gets the current bossbar progress in the creation progress (0.0 - 1.0)
     * @param name the player who's bossbar progress is being retrieved
     * @return the current progress on the bossbar (0.0 - 1.0)
     */
    private float getCurrentPositionProgress(String name) {
        float floaty = 0f;

        // if selecting a region
        if (isPlayerSelectingRegion(name)) {
            if (currentPos1ByPlayer.containsKey(name)) floaty += 1f;
            if (currentPos2ByPlayer.containsKey(name)) floaty += 1f;

            floaty /= 2f;
        }

        // if selecting spawn points
        else if (isPlayerSelectingSpawns(name)) {
            if (currentTeam1SpawnsByPlayer.containsKey(name)) floaty += currentTeam1SpawnsByPlayer.get(name).size();
            if (floaty > 6f) floaty = 6f;

            if (currentTeam2SpawnsByPlayer.containsKey(name)) floaty += currentTeam2SpawnsByPlayer.get(name).size();
            if (floaty > 12f) floaty = 12f;

            floaty /= 12f;
        }

        return floaty;
    }

    /**
     * refreshes the bossbar for a certain player
     * @param name the player whose bossbar will be refreshed
     */
    private void updateProgressBossBar(String name) {
        if (!(currentPlayerBossBars.containsKey(name))) return;
        BossBar bar = currentPlayerBossBars.get(name);

        bar.setTitle(ChatColor.translateAlternateColorCodes('&', getCurrentPositionsStatus(name)));
        bar.setProgress(getCurrentPositionProgress(name));
    }

    /**
     * Gets weather a player is currently in the creation process
     * @param name the player in question
     * @return is the player currently creating an arena?
     */
    private boolean isPlayerAlreadyCreating(String name) {
        if (playersInTheMiddleOfDesignatingThe2PointsNeededToCreateAnArenaRegion.contains(name) || playersInTheMiddleOfSelectingSpawnPoints.contains(name)) {
            return true;
        }

        else {
            return false;
        }
    }

    /**
     * Gets weather the player is done selecting region positions or not
     * @param name the player in question
     * @return is the player done selecting a region?
     */
    private boolean isDoneSelectingPositions(String name) {
        if (currentPos1ByPlayer.containsKey(name) && currentPos2ByPlayer.containsKey(name)) return true;
        else return false;
    }

    /**
     * Gets weather the player is currently selecting region positions or not
     * @param name the player in question
     * @return is the player currently selecting a region?
     */
    private boolean isPlayerSelectingRegion(String name) {
        return playersInTheMiddleOfDesignatingThe2PointsNeededToCreateAnArenaRegion.contains(name);
    }

    /**
     * Gets weather the player is currently selecting team spawns or not
     * @param name the player in question
     * @return is the player currently selecting team spawns?
     */
    private boolean isPlayerSelectingSpawns(String name) {
        return playersInTheMiddleOfSelectingSpawnPoints.contains(name);
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
