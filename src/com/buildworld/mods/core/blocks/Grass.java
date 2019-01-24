package com.buildworld.mods.core.blocks;

import com.buildworld.engine.graphics.loaders.assimp.StaticMeshesLoader;
import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.engine.graphics.mesh.meshes.CubeMesh;
import com.buildworld.game.Game;
import com.buildworld.game.graphics.Texture;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.Material;
import com.buildworld.game.blocks.types.Mundane;

public class Grass extends Block {

    private static Material material;

    public Grass() throws Exception
    {
        super("buildworld.mods.core.blocks", "grass", Mundane.make());
    }

    public Grass(Grass original) throws Exception
    {
        this();
    }

    @Override
    public Mesh[] makeMesh() throws Exception {
        return StaticMeshesLoader.load(Game.path + "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\dirt\\cube.obj", "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\dirt");
    }

    @Override
    public Block copy() throws Exception {
        return new Grass(this);
    }


}
