package com.buildworld.mods.core.blocks;

import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.types.Mundane;

public class Grass extends Block {

    public Grass() throws Exception {
        super("buildworld.mods.core.blocks", "grass", Mundane.make());
    }

    public Grass(Grass original) throws Exception {
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
        return "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\grass";
    }

    @Override
    public String getModelFilename() {
        return "grass.obj";
    }

    @Override
    public Block copy() throws Exception {
        return new Grass(this);
    }


}
