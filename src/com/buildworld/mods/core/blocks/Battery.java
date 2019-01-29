package com.buildworld.mods.core.blocks;

import com.buildworld.game.blocks.Block;
import com.buildworld.game.events.IUpdate;
import com.buildworld.mods.core.blocks.properties.Electricity;
import com.buildworld.mods.core.blocks.types.Electric;

public class Battery extends Block implements IUpdate {

    private boolean requiresUpdate = true;

    public Battery() throws Exception {
        super("buildworld.mods.core.blocks", "battery", new Electric());
    }

    public Battery(Battery original) throws Exception {
        this();
    }

    @Override
    public void initialize() throws Exception {

    }

    @Override
    public String getResourcePath() {
        return "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\battery";
    }

    @Override
    public String getModelFilename() {
        return "battery.obj";
    }

    @Override
    public Block copy() throws Exception {
        return new Battery(this);
    }

    public Electricity getElectricity() throws Exception {
        return this.getBlockProperty(Electricity.class);
    }

    @Override
    protected void ready() throws Exception {
        Electricity electricity = this.getBlockProperty(Electricity.class);
        electricity.setResistance(0f);
        electricity.setMaxamperage(2f);
        electricity.setAmperage(0.001f);
        electricity.setMaxvoltage(150f);
        electricity.setInputVoltage(120f);
    }


    @Override
    public void update() throws Exception {
        if (requiresUpdate) {
            updateNeighbors(this);
            requiresUpdate = false;
        }
    }

    @Override
    public boolean requiresUpdates() {
        return true;
    }

    @Override
    public void update(Block block) {

    }
}
