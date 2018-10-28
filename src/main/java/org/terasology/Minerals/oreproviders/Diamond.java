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
package org.terasology.Minerals.oreproviders;

import org.terasology.Minerals.OreFacet;
import org.terasology.Minerals.OreProvider;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generator.plugin.RegisterPlugin;

@RegisterPlugin
@Produces(OreFacet.Diamond.class)
public class Diamond extends OreProvider
{
    protected int index = 4;

    @Override
    public void setSeed (long seed)
    {
        oreNoise = new SimplexNoise(seed + index * 8);
    }

    @Override
    public void process (GeneratingRegion region)
    {
        //Border for diamond facet
        Border3D border = region.getBorderForFacet(OreFacet.Diamond.class);

        OreFacet facet = baseProcess(region, border, index);
        //Create, set the facet for diamond
        OreFacet.Diamond classFacet = facet.new Diamond(region.getRegion(), border);
        classFacet.set(facet.getInternal());
        //Apply
        region.setRegionFacet(OreFacet.Diamond.class, classFacet);
    }
}
