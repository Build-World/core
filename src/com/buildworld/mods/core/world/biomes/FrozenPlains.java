package com.buildworld.mods.core.world.biomes;

import com.buildworld.game.world.generators.Biome;
import com.buildworld.mods.core.blocks.*;

public class FrozenPlains extends Biome {

    public FrozenPlains() throws Exception {
        setCoreThickness(5);

        setTemperature(-25);
        setTemperatureRange(15);
        setPercipitation(0.2f);
        setPercipitationRange(0.1f);

        setCore(new Magma());
        setRock(new Rock());
        setCrust(new Dirt());
        setSurface(new Rock());
        setWater(new Ice());

        setSurfaceThickness(5);
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
        return "Frozen Plains";
    }
}
