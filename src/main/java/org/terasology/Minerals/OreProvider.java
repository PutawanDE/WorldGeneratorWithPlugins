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

import org.terasology.math.geom.Vector3i;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.generation.*;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

@Requires(@Facet(SurfaceHeightFacet.class))
public abstract class OreProvider implements FacetProviderPlugin
{
    public Noise oreNoise;

    protected int index;

    //Maximum height (y pos) that ore generate
    final protected int MAX_HEIGHT[] = new int[]
        {
                -10,    //Coal
                -20,    //Copper
                -20,    //Iron
                -40,    //Gold
                -60     //Diamond
        };

    //Rarity of ore; More closer to 1, more rare; 1 - never generated
    final protected float RARITY[] = new float[]
        {
                0.2f,    //Coal
                0.3f,    //Copper
                0.3f,    //Iron
                0.5f,    //Gold
                0.7f     //Diamond
        };

    //Vein size
    final protected int MAX_SIZE [] = new int[]
            {
                    5,    //Coal
                    5,    //Copper
                    5,    //Iron
                    3,    //Gold
                    2     //Diamond
            };

    @Override
    public void setSeed (long seed)
    {
        oreNoise = new SimplexNoise(seed + index);
    }

    protected OreFacet baseProcess (GeneratingRegion region, Border3D border, int index)
    {
        OreFacet facet = new OreFacet(region.getRegion(), border);
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);

        for (Vector3i pos : region.getRegion())
        {
            if (pos.y < MAX_HEIGHT[index] && pos.y < surfaceHeightFacet.getWorld(pos.x, pos.z) - 10)
            {
                float noiseLevel = oreNoise.noise(pos.x, pos.y, pos.z);
                //if noise is not much, less than, equal to rarity then set the value to 0
                if (noiseLevel <= RARITY[index])
                {
                    facet.setWorld(pos, 0);
                }
                else
                {
                    float subNoiseLevel = (1 - noiseLevel) / (1 - RARITY[index]);
                    float interval = subNoiseLevel / MAX_SIZE[index];
                    //Loop to set the facet's value (this will be used to set vein size in rasterizer)
                    for (int i = MAX_SIZE[index]; i > 0; i--)
                    {
                        if (subNoiseLevel > interval * (i - 1))
                        {
                            facet.setWorld(pos, i);
                            break;
                        }
                    }
                }
            }
        }
        return facet;
    }
}
