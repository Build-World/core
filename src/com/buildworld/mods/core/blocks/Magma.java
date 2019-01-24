package com.buildworld.mods.core.blocks;

import com.buildworld.engine.graphics.loaders.assimp.StaticMeshesLoader;
import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.game.Game;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.Material;
import com.buildworld.game.blocks.types.Mundane;
import com.buildworld.game.graphics.Texture;

public class Magma extends Block {
    private static Material material;

    public Magma() throws Exception
    {
        super("buildworld.mods.core.blocks", "magma", Mundane.make());
    }

    public Magma(Magma original) throws Exception
    {
        this();
    }

    @Override
    public Mesh[] makeMesh() throws Exception {
        return StaticMeshesLoader.load(Game.path + "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\dirt\\cube.obj", "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\dirt");
    }

    @Override
    public Block copy() throws Exception {
        return new Magma(this);
    }
}
