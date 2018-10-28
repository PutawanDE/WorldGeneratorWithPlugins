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

import org.terasology.world.generator.RegisterWorldGenerator;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.engine.SimpleUri;
import org.terasology.registry.In;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;

@RegisterWorldGenerator(id = "WorldGenGCI", displayName = "World Generator GCI")
public class GCIWorldGenerator extends BaseFacetedWorldGenerator
{
    public GCIWorldGenerator(SimpleUri uri)
    {
        super(uri);
    }

    @In
    private WorldGeneratorPluginLibrary worldGenPluginLib;

    @Override
    protected WorldBuilder createWorld()
    {
        return new WorldBuilder(worldGenPluginLib)
                .addProvider(new SurfaceProvider())
                .addProvider(new SeaLevelProvider(0))
                .addProvider(new MountainsProvider())
                .addProvider(new HouseProvider())
                .addRasterizer(new GCIWorldRasterizer())
                .addRasterizer(new HouseRasterizer())
                .addPlugins();
    }
}
