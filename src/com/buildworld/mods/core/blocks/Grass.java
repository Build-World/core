package com.buildworld.mods.core.blocks;

import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.engine.graphics.mesh.meshes.CubeMesh;
import com.buildworld.game.Game;
import com.buildworld.game.graphics.Texture;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.Material;
import com.buildworld.game.blocks.tests.MyBlock;
import com.buildworld.game.blocks.types.Mundane;

public class Grass extends Block {

    private static Material material;

    public Grass() throws Exception
    {
        super("buildworld.mods.core.blocks", "grass", makeMaterial(), Mundane.make());
    }

    public Grass(Grass original) throws Exception
    {
        this();
    }

    private static Material makeMaterial() throws Exception
    {
        if(material == null)
        {
            material = new Material(new Texture(Game.path + "\\core\\src\\com\\buildworld\\mods\\core\\resources\\textures\\grassblock.png"));
        }
        return material;
    }

    @Override
    public Block copy() throws Exception {
        return new Grass(this);
    }


}
