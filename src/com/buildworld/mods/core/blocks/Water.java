package com.buildworld.mods.core.blocks;

import com.buildworld.game.Game;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.Material;
import com.buildworld.game.blocks.types.Mundane;
import com.buildworld.game.graphics.Texture;

public class Water extends Block {
    private static Material material;

    public Water() throws Exception
    {
        super("buildworld.mods.core.blocks", "water", makeMaterial(), Mundane.make());
    }

    public Water(Water original) throws Exception
    {
        this();
    }

    private static Material makeMaterial() throws Exception
    {
        if(material == null)
        {
            material = new Material(new Texture(Game.path + "\\core\\src\\com\\buildworld\\mods\\core\\resources\\textures\\water.png"));
        }
        return material;
    }

    @Override
    public Block copy() throws Exception {
        return new Water(this);
    }
}
