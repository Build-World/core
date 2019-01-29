package com.buildworld.mods.core.blocks;

import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.types.Mundane;

public class Magma extends Block {

    public Magma() throws Exception
    {
        super("buildworld.mods.core.blocks", "magma", Mundane.make());
    }

    public Magma(Magma original) throws Exception
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
        return "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\magma";
    }

    @Override
    public String getModelFilename() {
        return "magma.obj";
    }

    @Override
    public Block copy() throws Exception {
        return new Magma(this);
    }
}
