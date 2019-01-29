package com.buildworld.mods.core.blocks;

import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.types.Mundane;

public class Rock extends Block {

    public Rock() throws Exception
    {
        super("buildworld.mods.core.blocks", "rock", Mundane.make());
    }

    public Rock(Rock original) throws Exception
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
        return "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\rock";
    }

    @Override
    public String getModelFilename() {
        return "rock.obj";
    }

    @Override
    public Block copy() throws Exception {
        return new Rock(this);
    }
}
