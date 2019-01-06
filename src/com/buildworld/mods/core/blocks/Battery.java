package com.buildworld.mods.core.blocks;

import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.engine.graphics.mesh.meshes.CubeMesh;
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
        super("buildworld.mods.core.blocks", "battery", makeMaterial(), new Electric());
    }

    public Battery(Battery original) throws Exception
    {
        this();
    }

    private static Material makeMaterial() throws Exception
    {
        if(material == null)
        {
            material = new Material(new Texture("C:\\Users\\using\\Desktop\\shawn\\build-world\\core\\src\\com\\buildworld\\mods\\core\\resources\\textures\\battery.png"));
        }
        return material;
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
