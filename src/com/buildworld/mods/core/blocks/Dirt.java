package com.buildworld.mods.core.blocks;

import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.engine.graphics.mesh.meshes.CubeMesh;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.Material;
import com.buildworld.game.blocks.types.Mundane;
import com.buildworld.game.graphics.Texture;

public class Dirt extends Block {
    private static Mesh mesh;

    public Dirt() throws Exception
    {
        super("buildworld.mods.core.blocks", "dirt", makeMesh(), Mundane.make());
    }

    private static Mesh makeMesh() throws Exception
    {
        if(mesh == null)
        {
            mesh = new CubeMesh().make(new Material(new Texture("C:\\Users\\using\\Desktop\\shawn\\build-world\\core\\src\\com\\buildworld\\mods\\core\\resources\\textures\\dirtblock.png")));
        }
        return mesh;
    }
}
