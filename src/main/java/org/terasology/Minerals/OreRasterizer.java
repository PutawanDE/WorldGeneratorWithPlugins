/*
 * Copyright 2018 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.Minerals;

import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.Requires;
import org.terasology.world.generation.WorldRasterizerPlugin;
import org.terasology.world.generation.facets.SurfaceHeightFacet;
import org.terasology.world.generator.plugin.RegisterPlugin;

@RegisterPlugin
@Requires(@Facet(SurfaceHeightFacet.class))
public class OreRasterizer implements WorldRasterizerPlugin
{
    private Block coal, copper, iron, gold, diamond;

    @Override
    public void initialize()
    {
        coal = CoreRegistry.get(BlockManager.class).getBlock("Core:CoalOre");
        copper = CoreRegistry.get(BlockManager.class).getBlock("Core:CopperOre");
        iron = CoreRegistry.get(BlockManager.class).getBlock("Core:IronOre");
        gold = CoreRegistry.get(BlockManager.class).getBlock("Core:GoldOre");
        diamond = CoreRegistry.get(BlockManager.class).getBlock("Core:DiamondOre");
    }

    @Override
    public void generateChunk (CoreChunk chunk, Region chunkRegion)
    {
        generateChunk(chunk, chunkRegion, coal, chunkRegion.getFacet(OreFacet.Coal.class));
        generateChunk(chunk, chunkRegion, copper, chunkRegion.getFacet(OreFacet.Copper.class));
        generateChunk(chunk, chunkRegion, iron, chunkRegion.getFacet(OreFacet.Iron.class));
        generateChunk(chunk, chunkRegion, gold, chunkRegion.getFacet(OreFacet.Gold.class));
        generateChunk(chunk, chunkRegion, diamond, chunkRegion.getFacet(OreFacet.Diamond.class));
    }

    private void generateChunk (CoreChunk chunk, Region chunkRegion, Block ore, OreFacet oreFacet)
    {
        SurfaceHeightFacet surfaceHeightFacet = chunkRegion.getFacet(SurfaceHeightFacet.class);

        for (Vector3i pos : chunkRegion.getRegion())
        {
            float surfaceHeight = surfaceHeightFacet.getWorld(pos.x, pos.z);
            //Get the vein size from ore facet -- set using the noise and loop
            int veinSize = (int)oreFacet.getWorld(pos);

            //Check the vein size and place ore blocks accordingly
            switch (veinSize)
            {
                case (1):
                    chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                break;

                case (2):
                {
                    chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                    if (pos.add(0, -1, 0).y < surfaceHeight - 10 && chunkRegion.getRegion().encompasses(pos))
                        chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                }
                break;

                case (3):
                {
                    chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                    if (pos.add(0, -1, 0).y < surfaceHeight - 10 && chunkRegion.getRegion().encompasses(pos))
                        chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                    if (pos.add(0, 0, -1).y < surfaceHeight - 10 && chunkRegion.getRegion().encompasses(pos))
                        chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                }
                break;

                case (4):
                {
                    chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                    if (pos.add(0, -1, 0).y < surfaceHeight - 10 && chunkRegion.getRegion().encompasses(pos))
                        chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                    if (pos.add(0, 0, -1).y < surfaceHeight - 10 && chunkRegion.getRegion().encompasses(pos))
                        chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                    if (pos.add(0, 1, 0).y < surfaceHeight - 10 && chunkRegion.getRegion().encompasses(pos))
                        chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                }
                break;

                case (5):
                {
                    chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                    if (pos.add(0, -1, 0).y < surfaceHeight - 10 && chunkRegion.getRegion().encompasses(pos))
                        chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                    if (pos.add(0, 0, -1).y < surfaceHeight - 10 && chunkRegion.getRegion().encompasses(pos))
                        chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                    if (pos.add(0, 1, 0).y < surfaceHeight - 10 && chunkRegion.getRegion().encompasses(pos))
                        chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                    if (pos.add(1, -1, 0).y < surfaceHeight - 10 && chunkRegion.getRegion().encompasses(pos))
                        chunk.setBlock(ChunkMath.calcBlockPos(pos), ore);
                }
                break;

                default: break;
            }
        }
    }
}
