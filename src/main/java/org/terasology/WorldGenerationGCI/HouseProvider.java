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

import org.terasology.math.TeraMath;
import org.terasology.math.geom.Rect2i;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.WhiteNoise;
import org.terasology.world.generation.*;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

@Produces(HouseFacet.class)
@Requires(@Facet(value = SurfaceHeightFacet.class, border = @FacetBorder(sides = 4)))
public class HouseProvider implements FacetProvider
{
    private Noise noise ;

    @Override
    public void setSeed (long seed)
    {
        noise = new WhiteNoise(seed);
    }

    @Override
    public void process (GeneratingRegion region)
    {
        Border3D border = region.getBorderForFacet(HouseFacet.class).extendBy(0, 8,4);
        HouseFacet facet = new HouseFacet(region.getRegion(), border);
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);

        //Loop through every position, modify facet
        Rect2i worldRegion = surfaceHeightFacet.getWorldRegion();
        for (int wz = worldRegion.minY(); wz <= worldRegion.maxY(); wz ++)
        {
            for (int wx = worldRegion.minX(); wx <= worldRegion.maxX(); wx ++)
            {
                //Get the height of the surface
                int surfaceHeight = TeraMath.floorToInt(surfaceHeightFacet.getWorld(wx, wz));

                //Check if the current height is within the region (Above surface)
                if (surfaceHeight >= facet.getWorldRegion().minY() && surfaceHeight <= facet.getWorldRegion().maxY())
                {
                    if (noise.noise(wx, wz) > 0.99f)
                    {
                        //House created on the surface
                        facet.setWorld(wx, surfaceHeight, wz, new House());
                    }
                }
            }
        }

        //Pass new created facet (from loop) to the region
        region.setRegionFacet(HouseFacet.class, facet);
    }
}
