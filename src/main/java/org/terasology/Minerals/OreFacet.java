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

import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseFieldFacet3D;

public class OreFacet extends BaseFieldFacet3D
{
    public  OreFacet (Region3i targetRegion, Border3D border)
    {
        super(targetRegion, border);
    }

    public class Coal extends OreFacet
    {
        public  Coal (Region3i targetRegion, Border3D border)
        {
            super(targetRegion, border);
        }
    }

    public class Copper extends OreFacet
    {
        public  Copper (Region3i targetRegion, Border3D border)
        {
            super(targetRegion, border);
        }
    }

    public class Iron extends OreFacet
    {
        public  Iron (Region3i targetRegion, Border3D border)
        {
            super(targetRegion, border);
        }
    }

    public class Gold extends OreFacet
    {
        public  Gold (Region3i targetRegion, Border3D border)
        {
            super(targetRegion, border);
        }
    }

    public class Diamond extends OreFacet
    {
        public  Diamond (Region3i targetRegion, Border3D border)
        {
            super(targetRegion, border);
        }

    }
}
