package com.buildworld.mods.core.blocks;

import com.buildworld.engine.graphics.textures.Texture;
import com.buildworld.game.blocks.Block;
import com.buildworld.game.blocks.Material;

public class DirtBlock extends Block {
    public DirtBlock() throws Exception {
        super(Material.make(new Texture("D:\\Programming\\Projects\\Build-World\\core\\src\\com\\buildworld\\mods\\core\\resources\\textures\\dirtblock.png")), "buildworld.mods.core.blocks", "dirtblock");
    }
}
