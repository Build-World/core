package com.buildworld.mods.core.world.planets;

import com.buildworld.game.world.generators.Planet;

public class Earth extends Planet {

    public Earth() {
        setFeatureNoiseFeatureSizeModifier(9f);
        setHeightNoiseFeatureSize(1.5f);
        setMinimumSurfaceThickness(1);
    }

    public Earth(int seed) {
        super(seed);
        setFeatureNoiseFeatureSizeModifier(9f);
        setHeightNoiseFeatureSize(1.5f);
        setMinimumSurfaceThickness(1);
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
        return null;
    }
}
