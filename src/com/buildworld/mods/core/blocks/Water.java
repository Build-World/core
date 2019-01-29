package com.buildworld.mods.core.blocks;

import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.types.Mundane;

public class Water extends Block {

    public Water() throws Exception
    {
        super("buildworld.mods.core.blocks", "water", Mundane.make());
    }

    public Water(Water original) throws Exception
    {
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
        return "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\water";
    }

    @Override
    public String getModelFilename() {
        return "water.obj";
    }

    @Override
    public Block copy() throws Exception {
        return new Water(this);
    }
}
