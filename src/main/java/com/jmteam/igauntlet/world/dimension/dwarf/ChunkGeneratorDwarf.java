package com.jmteam.igauntlet.world.dimension.dwarf;

import com.jmteam.igauntlet.util.helpers.IChunkGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorOverworld;

import java.util.Random;

public class ChunkGeneratorDwarf extends ChunkGeneratorOverworld {

    World world;
    Random rand = new Random();
    IChunkGenerator pop;

    public ChunkGeneratorDwarf(World worldIn, long seed) {
        super(worldIn, seed, false, "");
        world = worldIn;
    }

    public ChunkGeneratorDwarf(World worldIn, long seed, IChunkGenerator pop) {
        this(worldIn, seed);
        this.pop = pop;
    }

    @Override
    public void replaceBiomeBlocks(int cx, int cz, ChunkPrimer primer, Biome[] biomesIn) {
        super.replaceBiomeBlocks(cx, cz, primer, biomesIn);
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                for (int y = 0; y < world.getHeight(); ++y) {
                    if (primer.getBlockState(x, y, z).getBlock() == Blocks.WATER) {
                        primer.setBlockState(x, y, z, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
    }

    @Override
    public void populate(int x, int z) {
        if (pop != null) {
            pop.gen(world, rand, x, z);
        }
    }

}
