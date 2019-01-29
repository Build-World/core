package com.buildworld.mods.core.blocks;

import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.types.Mundane;

public class Ice extends Block {

    public Ice() throws Exception {
        super("buildworld.mods.core.blocks", "ice", Mundane.make());
    }

    public Ice(Ice original) throws Exception {
        this();
    }

    @Override
    public void initialize() throws Exception {

    }

    @Override
    protected void ready() throws Exception {

    }

    @Override
    public String getResourcePath() {
        return "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\ice";
    }

    @Override
    public String getModelFilename() {
        return "ice.obj";
    }

    @Override
    public Block copy() throws Exception {
        return new Ice(this);
    }
}
