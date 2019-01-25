package com.buildworld.mods.core.blocks;

import com.buildworld.engine.graphics.loaders.assimp.StaticMeshesLoader;
import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.engine.graphics.mesh.meshes.CubeMesh;
import com.buildworld.game.Game;
import com.buildworld.game.graphics.Texture;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.Material;
import com.buildworld.game.blocks.types.Mundane;
import com.buildworld.game.events.IUpdateable;
import com.buildworld.mods.core.blocks.properties.Electricity;
import com.buildworld.mods.core.blocks.types.Electric;

public class Wire extends Block implements IUpdateable {

    private static Material material;

    public Wire() throws Exception
    {
        super("buildworld.mods.core.blocks", "wire", new Electric());
    }

    public Wire(Wire original) throws Exception
    {
        this();
    }

    @Override
    public Mesh[] makeMesh() throws Exception {
        return StaticMeshesLoader.load(Game.path + "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\wire\\wire.obj", "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\wire");
    }

    @Override
    public Block copy() throws Exception {
        return new Wire(this);
    }

    public Electricity getElectricity() throws Exception
    {
        return this.getBlockProperty(Electricity.class);
    }

    @Override
    protected void ready() throws Exception {
        Electricity electricity = this.getBlockProperty(Electricity.class);
        electricity.setResistance(1000f);
        electricity.setMaxamperage(2f);
        electricity.setMaxvoltage(150f);
    }

    @Override
    public void update(Block block) throws Exception {
        try {
            getElectricity().setAmperage(block.getBlockProperty(Electricity.class).getAmperage());
            getElectricity().setInputVoltage(block.getBlockProperty(Electricity.class).getOutputVoltage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateNeighbors(block);
    }
}
