package com.buildworld.mods.core.blocks.types;

import com.buildworld.game.blocks.types.IBlockType;

public class Electric implements IBlockType {
    @Override
    public String getDescription() {
        return "Blocks which understand electricity";
    }

    @Override
    public String getKey() {
        return "electric";
    }

    @Override
    public String getName() {
        return "Electric";
    }
}
