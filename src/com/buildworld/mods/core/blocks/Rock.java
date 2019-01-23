package com.buildworld.mods.core.blocks;

import com.buildworld.game.Game;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.Material;
import com.buildworld.game.blocks.types.Mundane;
import com.buildworld.game.graphics.Texture;

public class Rock extends Block {
    private static Material material;

    public Rock() throws Exception
    {
        super("buildworld.mods.core.blocks", "rock", makeMaterial(), Mundane.make());
    }

    public Rock(Rock original) throws Exception
    {
        this();
    }

    private static Material makeMaterial() throws Exception
    {
        if(material == null)
        {
            material = new Material(new Texture(Game.path + "\\core\\src\\com\\buildworld\\mods\\core\\resources\\textures\\rock.png"));
        }
        return material;
    }

    @Override
    public Block copy() throws Exception {
        return new Rock(this);
    }
}
