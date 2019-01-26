package com.buildworld.mods.core.world.biomes;

import com.buildworld.game.world.generators.Biome;
import com.buildworld.mods.core.blocks.*;

public class DesertPlains extends Biome {

    public DesertPlains() throws Exception {
        setCoreThickness(5);

        setTemperature(35);
        setTemperatureRange(15);
        setPercipitation(0.1f);
        setPercipitationRange(0.1f);

        setCore(new Magma());
        setRock(new Rock());
        setCrust(new Dirt());
        setSurface(new Magma());
        setWater(new Water());

        setSurfaceThickness(3);
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getName() {
        return "Desert Plains";
    }
}
