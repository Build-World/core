package com.buildworld.mods.core.blocks;

import com.buildworld.engine.graphics.loaders.assimp.StaticMeshesLoader;
import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.game.Game;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.Material;
import com.buildworld.game.blocks.types.Mundane;
import com.buildworld.game.graphics.Texture;

public class Ice extends Block {
    private static Material material;

    public Ice() throws Exception
    {
        super("buildworld.mods.core.blocks", "ice", Mundane.make());
    }

    public Ice(Ice original) throws Exception
    {
        this();
    }

    @Override
    public Mesh[] makeMesh() throws Exception {
        return StaticMeshesLoader.load(Game.path + "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\ice\\ice.obj", "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\ice");
    }

    @Override
    public Block copy() throws Exception {
        return new Ice(this);
    }
}
