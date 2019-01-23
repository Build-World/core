package com.buildworld.mods.core.world.biomes;

import com.buildworld.game.world.generators.Biome;
import com.buildworld.mods.core.blocks.Dirt;
import com.buildworld.mods.core.blocks.Grass;
import com.buildworld.mods.core.blocks.Rock;

public class Plains extends Biome {

    public Plains() throws Exception {
        setCore(new Dirt());
        setRock(new Rock());
        setCrust(new Dirt());
        setSurface(new Grass());
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
