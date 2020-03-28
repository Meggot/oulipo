package com.curtail.dynamics;

import com.common.models.enums.CurtailType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class CurtailProjectConfigurationTest {

    Map<CurtailType, CurtailProjectConfiguration.CurtailConfiguration> tailConfigurationMap;

    @Before
    public void setup() {
        tailConfigurationMap = Maps.newHashMap();
    }

    @Test
    public void testDeserialzeLipogram() {
        CurtailProjectConfiguration.LipogramConfiguration lipogramConfig =
                new CurtailProjectConfiguration.LipogramConfiguration(Lists.charactersOf("hjkl"));
        tailConfigurationMap.put(CurtailType.LIPOGRAM,
                lipogramConfig)
        CurtailProjectConfiguration projectConfiguration = new CurtailProjectConfiguration();
        projectConfiguration.setTailConfigurationMap(tailConfigurationMap);
    }

}