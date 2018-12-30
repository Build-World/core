import com.buildworld.engine.graphics.Window;
import com.buildworld.game.blocks.properties.BlockPropertyService;
import com.buildworld.game.mod.IMod;
import com.buildworld.game.state.GameStateService;
import com.buildworld.game.state.GameStatesService;
import com.buildworld.game.state.State;
import com.buildworld.mods.core.blocks.Dirt;
import com.buildworld.mods.core.blocks.Grass;
import com.buildworld.mods.core.blocks.Wire;
import com.buildworld.mods.core.blocks.properties.Electricity;
import com.buildworld.mods.core.states.GameState;
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
    public void onBoot(Window window) throws Exception {

        // Adding game state
        State state = new GameState();
        state.init(window);
        Services.getService(GameStatesService.class).add(state);
    }

    @Override
    public void onLoad() throws Exception {
        // Changing to game state
        Services.getService(GameStateService.class).change(GameState.class);

        // Registering block properties
        Services.getService(BlockPropertyService.class).add(new Electricity());

        // Registering blocks
        new Dirt().register();
        new Grass().register();
        new Wire().register();
    }

    @Override
    public void onReady() throws Exception {
        GameState gs = (GameState)Services.getService(GameStateService.class).getCurrentState();
        gs.generateWorld();
        Wire w1 = new Wire();
        w1.getElectricity().setResistance(0.05f);
        gs.getWorld().setBlock(0,40,0, new Dirt());
        gs.getWorld().setBlock(0,32,1, w1);
        gs.getWorld().setBlock(1,32,1, new Wire());
        gs.getWorld().setBlock(2,32,1, new Wire());
        gs.getWorld().setBlock(3,32,1, new Wire());
        gs.getWorld().setBlock(4,32,1, new Wire());
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
