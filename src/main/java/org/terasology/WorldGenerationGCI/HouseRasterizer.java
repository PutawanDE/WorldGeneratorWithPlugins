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
package org.terasology.WorldGenerationGCI;

import org.terasology.math.ChunkMath;
import org.terasology.math.Region3i;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generation.facets.SeaLevelFacet;

import java.util.Map;

public class HouseRasterizer implements WorldRasterizer
{
    private Block stone;

    @Override
    public void initialize()
    {
        stone = CoreRegistry.get(BlockManager.class).getBlock("Core:Stone");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion)
    {
        HouseFacet houseFacet = chunkRegion.getFacet(HouseFacet.class);
        SeaLevelFacet seaLevelFacet = chunkRegion.getFacet(SeaLevelFacet.class);
        int seaLevel = seaLevelFacet.getSeaLevel();

        for (Map.Entry<BaseVector3i, House> entry : houseFacet.getWorldEntries().entrySet())
        {
            Vector3i centerHousePosition = new Vector3i(entry.getKey());
            if (centerHousePosition.y > seaLevel)
            {
                int extend = entry.getValue().getExtend();
                centerHousePosition.add(0, extend, 0);
                //Create walls region extend from center of the house
                Region3i walls = Region3i.createFromCenterExtents(centerHousePosition, extend);
                //Create inside region extend from center to almost edge of walls region (partly overlapping)
                Region3i inside = Region3i.createFromCenterExtents(centerHousePosition, extend - 1);

                //Loop through each of positions in the cube(house)
                for (Vector3i newBlockPosition : walls) {
                    //Place stone blocks at the region where walls region and inside region are not overlapping
                    if (chunkRegion.getRegion().encompasses(newBlockPosition) && !inside.encompasses(newBlockPosition)) {
                        chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), stone);
                    }
                    //Resulted in a hollow cube
                }
            }
        }
    }
}
