package com.shaneschulte.mc.clanarena.arena;

import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ArenaSetup {

    /**
     * States for the ArenaSetup
     */
    public enum ArenaSetupState {
        SELECTING_REGION, SELECTING_TEAM1_SPAWNS, SELECTING_TEAM2_SPAWNS, COMPLETE,
    }

    /**
     * Minimum spawns to set for each team
     */
    private static int minSpawns = 4;

    private Player owner;
    private ArenaSetupState arenaState;

    private BossBar bossBar;
    private String arenaName;

    // Stage 01
    private Location regionPos1, regionPos2;

    // Stage 02
    private ArrayList<Location> firstTeamSpawns, secondTeamSpawns;

    // todo: state manager with messages in here (switch statement in setArenaState())

    /**
     * Creates a ArenaSetup to be modified on the fly. When complete, saves + creates arena
     * @param owner The creator of this arena
     * @param arenaName The name of this arena
     */
    public ArenaSetup(Player owner, String arenaName) {
        this.bossBar = Bukkit.getServer().createBossBar("PROGRESS", BarColor.PINK, BarStyle.SEGMENTED_12);
        this.bossBar.addPlayer(owner);

        this.arenaName = arenaName;

        this.firstTeamSpawns = new ArrayList<>();
        this.secondTeamSpawns = new ArrayList<>();

        this.owner = owner;
        this.arenaState = ArenaSetupState.SELECTING_REGION;

        refreshBossBar();
    }

    /**
     * Updates the progress bar
     */
    private void refreshBossBar() {
        String title = getBarTitle();
        float progress = getBarProgress();

        bossBar.setTitle(ChatColor.translateAlternateColorCodes('&', title));
        bossBar.setProgress(progress);
    }

    /**
     * Returns the bar progress (0.0 - 1.0)
     * @return the current bar progress (0.0 - 1.0)
     */
    private float getBarProgress() {
        float floaty = 0f;

        // if selecting a region
        if (arenaState.equals(ArenaSetupState.SELECTING_REGION)) {
            if (regionPos1 != null) floaty += 1f;
            if (regionPos2 != null) floaty += 1f;

            floaty /= 2f;
        }

        // if selecting spawn points
        else if (arenaState.equals(ArenaSetupState.SELECTING_TEAM1_SPAWNS) || arenaState.equals(ArenaSetupState.SELECTING_TEAM2_SPAWNS)) {
            floaty += firstTeamSpawns.size();
            if (floaty > (float) minSpawns) floaty = (float) minSpawns;

            floaty += secondTeamSpawns.size();
            if (floaty > (minSpawns * 2f)) floaty = (minSpawns * 2f);

            floaty /= (minSpawns * 2f);
        }

        return floaty;
    }

    /**
     * Returns the progress bar title
     * @return the current progress bar title
     */
    private String getBarTitle() {
        StringBuilder barText = new StringBuilder();

        // if selecting a region
        if (arenaState.equals(ArenaSetupState.SELECTING_REGION)) {
            barText.append(MsgUtils.Colors.INFO);
            barText.append("(&eL&7) - ");
            barText.append((regionPos1 != null) ? (MsgUtils.Colors.SUCCESS) : (MsgUtils.Colors.ERROR));
            barText.append("&lTop Left &r");
            barText.append(MsgUtils.Colors.INFO);
            barText.append("click ");
            barText.append((regionPos2 != null) ? (MsgUtils.Colors.SUCCESS) : (MsgUtils.Colors.ERROR));
            barText.append("&lBottom Right &r");
            barText.append(MsgUtils.Colors.INFO);
            barText.append("- (&eR&7)");
        }

        // if selecting spawn points
        else if (arenaState.equals(ArenaSetupState.SELECTING_TEAM1_SPAWNS) || arenaState.equals(ArenaSetupState.SELECTING_TEAM2_SPAWNS)) {
            int team1SpawnsLeft = ((minSpawns - firstTeamSpawns.size() < 0) ? 0 : minSpawns - firstTeamSpawns.size());
            int team2SpawnsLeft = ((minSpawns - secondTeamSpawns.size() < 0) ? 0 : minSpawns - secondTeamSpawns.size());

            if (arenaState.equals(ArenaSetupState.SELECTING_TEAM1_SPAWNS)) {
                barText.append(MsgUtils.Colors.HIGHLIGHT);
                barText.append("&lTeam1&r");
                barText.append(MsgUtils.Colors.INFO);
                barText.append(": ");
                barText.append(MsgUtils.Colors.VARIABLE);
                barText.append(team1SpawnsLeft);
                barText.append(MsgUtils.Colors.INFO);
                barText.append(" spawns left (\"");
                barText.append(MsgUtils.Colors.SUCCESS);
                barText.append("add");
                barText.append(MsgUtils.Colors.INFO);
                barText.append("\")");
            } else {
                barText.append(MsgUtils.Colors.HIGHLIGHT);
                barText.append("&lTeam2&r");
                barText.append(MsgUtils.Colors.INFO);
                barText.append(": ");
                barText.append(MsgUtils.Colors.VARIABLE);
                barText.append(team2SpawnsLeft);
                barText.append(MsgUtils.Colors.INFO);
                barText.append(" spawns left (\"");
                barText.append(MsgUtils.Colors.SUCCESS);
                barText.append("add");
                barText.append(MsgUtils.Colors.INFO);
                barText.append("\")");
            }
        }

        return barText.toString();
    }

    /**
     * Hides the ArenaSetup
     * todo: change to complete() and save + initialize the arena
     */
    public void hide() {
        this.bossBar.removeAll();
        this.setArenaState(ArenaSetupState.COMPLETE);
    }

    // QOL checks
    public boolean isRegionSelected() {
        return (regionPos1 != null && regionPos2 != null);
    }


    // Getters & Setters
    public void setArenaState (ArenaSetupState arenaState) {
        this.arenaState = arenaState;
        refreshBossBar();
    }

    public ArenaSetupState getArenaState () {
        return arenaState;
    }

    public String getArenaName() {
        return arenaName;
    }

    public void setRegionPos1(Location regionPos1) {
        this.regionPos1 = regionPos1;
        refreshBossBar();
    }

    public void setRegionPos2(Location regionPos2) {
        this.regionPos2 = regionPos2;
        refreshBossBar();
    }

    public void addFirstTeamSpawn(Location location) {
        firstTeamSpawns.add(location);
        refreshBossBar();

        if (firstTeamSpawns.size() > minSpawns -1) MsgUtils.sendMessage(owner, MsgUtils.Colors.VARIABLE + "" +
            firstTeamSpawns.size() + MsgUtils.Colors.INFO + " spawns for team1. Type \"" + MsgUtils.Colors.SUCCESS + "next" +
            MsgUtils.Colors.INFO + "\" to move on");
    }

    public void addSecondTeamSpawn(Location location) {
        secondTeamSpawns.add(location);
        refreshBossBar();

        if (secondTeamSpawns.size() > minSpawns -1) MsgUtils.sendMessage(owner, MsgUtils.Colors.VARIABLE + "" +
            secondTeamSpawns.size() + MsgUtils.Colors.INFO + " spawns for team2. Type \"" + MsgUtils.Colors.SUCCESS + "done" +
            MsgUtils.Colors.INFO + "\" when done");
    }

    public boolean canMoveOnFromSettingFirstTeamSpawns() {
        return (firstTeamSpawns.size() >= minSpawns) && (secondTeamSpawns.size() <= 0);
    }

    public boolean canMoveOnFromSettingSecondTeamSpawns() {
        return (secondTeamSpawns.size() >= minSpawns);
    }
}
