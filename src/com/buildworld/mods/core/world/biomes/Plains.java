package com.buildworld.mods.core.world.biomes;

import com.buildworld.game.world.generators.Biome;
import com.buildworld.mods.core.blocks.*;

public class Plains extends Biome {

    public Plains() throws Exception {
        setCoreThickness(5);

        setTemperature(15);
        setTemperatureRange(10);
        setPercipitation(0.5f);
        setPercipitationRange(0.1f);

        setCore(new Magma());
        setRock(new Rock());
        setCrust(new Dirt());
        setSurface(new Grass());
        setWater(new Water());
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
        return "Plains";
    }
}
