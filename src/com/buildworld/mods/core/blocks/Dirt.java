package com.buildworld.mods.core.blocks;

import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.types.Mundane;

public class Dirt extends Block {

    public Dirt() throws Exception {
        super("buildworld.mods.core.blocks", "dirt", Mundane.make());
    }

    public Dirt(Dirt original) throws Exception {
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
        return "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\dirt";
    }

    @Override
    public String getModelFilename() {
        return "dirt.obj";
    }

    @Override
    public Block copy() throws Exception {
        return new Dirt(this);
    }
}
