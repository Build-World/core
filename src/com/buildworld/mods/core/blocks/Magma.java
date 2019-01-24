package com.buildworld.mods.core.blocks;

import com.buildworld.game.Game;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.Material;
import com.buildworld.game.blocks.types.Mundane;
import com.buildworld.game.graphics.Texture;

public class Magma extends Block {
    private static Material material;

    public Magma() throws Exception
    {
        super("buildworld.mods.core.blocks", "magma", makeMaterial(), Mundane.make());
    }

    public Magma(Magma original) throws Exception
    {
        this();
    }

    private static Material makeMaterial() throws Exception
    {
        if(material == null)
        {
            material = new Material(new Texture(Game.path + "\\core\\src\\com\\buildworld\\mods\\core\\resources\\textures\\magma.png"));
        }
        return material;
    }

    @Override
    public Block copy() throws Exception {
        return new Magma(this);
    }
}
