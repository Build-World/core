package com.buildworld.mods.core.blocks;

import com.buildworld.engine.graphics.loaders.assimp.StaticMeshesLoader;
import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.engine.graphics.mesh.meshes.CubeMesh;
import com.buildworld.game.Game;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.Material;
import com.buildworld.game.blocks.types.Mundane;
import com.buildworld.game.events.IUpdate;
import com.buildworld.game.graphics.Texture;
import com.buildworld.mods.core.blocks.properties.Electricity;
import com.buildworld.mods.core.blocks.types.Electric;

public class Battery extends Block implements IUpdate {

    private boolean requiresUpdate = true;

    private static Material material;

    public Battery() throws Exception
    {
        super("buildworld.mods.core.blocks", "battery", new Electric());
    }

    public Battery(Battery original) throws Exception
    {
        this();
    }

    @Override
    public Mesh[] makeMesh() throws Exception {
        return StaticMeshesLoader.load(Game.path + "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\battery\\battery.obj", "\\core\\src\\com\\buildworld\\mods\\core\\resources\\models\\battery");
    }

    @Override
    public Block copy() throws Exception {
        return new Battery(this);
    }

    public Electricity getElectricity() throws Exception
    {
        return this.getBlockProperty(Electricity.class);
    }

    @Override
    protected void ready() throws Exception {
        Electricity electricity = this.getBlockProperty(Electricity.class);
        electricity.setResistance(0f);
        electricity.setMaxamperage(2f);
        electricity.setAmperage(0.001f);
        electricity.setMaxvoltage(150f);
        electricity.setInputVoltage(120f);
    }


    @Override
    public void update() throws Exception {
        if(requiresUpdate)
        {
            updateNeighbors(this);
            requiresUpdate = false;
        }
    }

    @Override
    public boolean requiresUpdates() {
        return true;
    }

    @Override
    public void update(Block block) {

    }
}
