import com.buildworld.game.mod.IMod;
import com.buildworld.game.state.GameStateService;
import com.buildworld.game.state.states.GameState;
import com.buildworld.mods.core.blocks.DirtBlock;
import com.shawnclake.morgencore.core.component.services.Services;

/**
 * MOD File for Build WOrld
 * Core mod will implement all of the core features for buildworld
 * DO NOT ADD CODE TO THIS FILE OUTSIDE OF THE OVERRIDDEN FUNCTIONS
 * ALSO DO NOT CREATE ADDITIONAL FUNCTIONS IN THIS FILE
 * ALL OTHER CODE MUST GO INSIDE OF THE PACKAGE com.buildworld.mods.core
 */
public class Mod implements IMod {
    /**
     * WARNING WARNING WARNING
     * DO NOT ADD CODE TO THIS FILE OUTSIDE OF THE OVERRIDDEN FUNCTIONS
     * ALSO DO NOT CREATE ADDITIONAL FUNCTIONS IN THIS FILE
     * ALL OTHER CODE MUST GO INSIDE OF THE PACKAGE com.buildworld.mods.core
     */

    /**
     *
     * @return
     */
    @Override
    public String getKey() {
        return "core";
    }

    @Override
    public String getName() {
        return "Core Mod";
    }

    @Override
    public String getDescription() {
        return "Provides main gameplay features";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public void onBoot() throws Exception {

    }

    @Override
    public void onLoad() throws Exception {
        new DirtBlock().register();
    }

    @Override
    public void onReady() throws Exception {

        GameState gs = (GameState)Services.getService(GameStateService.class).getCurrentState();
        gs.getWorld().setBlock(0,40,0, new DirtBlock());
    }

    @Override
    public void onPlay() throws Exception {

    }

    @Override
    public void onEnable() throws Exception {

    }

    @Override
    public void onDisable() throws Exception {

    }

    @Override
    public void onTick() throws Exception {

    }

    @Override
    public void onDraw() throws Exception {

    }
}
