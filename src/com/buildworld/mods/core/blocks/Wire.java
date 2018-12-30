package com.buildworld.mods.core.blocks;

import com.buildworld.engine.graphics.mesh.Mesh;
import com.buildworld.engine.graphics.mesh.meshes.CubeMesh;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.Material;
import com.buildworld.game.events.IUpdate;
import com.buildworld.game.graphics.Texture;
import com.buildworld.mods.core.blocks.properties.Electricity;
import com.buildworld.mods.core.blocks.types.Electric;

public class Wire extends Block implements IUpdate {

    private static Mesh mesh;

    public Wire() throws Exception
    {
        super("buildworld.mods.core.blocks", "wire", makeMesh(), new Electric());
    }

    private static Mesh makeMesh() throws Exception
    {
        if(mesh == null)
        {
            mesh = new CubeMesh().make(new Material(new Texture("C:\\Users\\using\\Desktop\\shawn\\build-world\\core\\src\\com\\buildworld\\mods\\core\\resources\\textures\\wireblock.png")));
        }
        return mesh;
    }

    public Electricity getElectricity() throws Exception
    {
        return this.getBlockProperty(Electricity.class);
    }

    @Override
    protected void ready() throws Exception {
        Electricity electricity = this.getBlockProperty(Electricity.class);
        electricity.setResistance(0.001f);
        electricity.setMaxamperage(2f);
        electricity.setMaxvoltage(150f);
    }

    @Override
    public void update() {
        try {
            Electricity electricity = this.getBlockProperty(Electricity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean requiresUpdates() {
        return true;
    }
}
