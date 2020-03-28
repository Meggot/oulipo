package com.curtail.dynamics;

import com.common.models.enums.CurtailType;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
public class CurtailProjectConfiguration {

    //configurations are current project configurations stored under the Project in json.

    /**
     *  {
     *      [
     *          {
     *              "type": "LIPOGRAM",
     *              "forbiddenCharacterList": "ABCDE"
     *          }
     *      ]
     *  }
     */
    Map<CurtailType, CurtailConfiguration> tailConfigurationMap;


    public interface CurtailConfiguration {

        CurtailType getCurtailType();
    }

    @Data
    @JsonTypeName("LIPOGRAM")
    @RequiredArgsConstructor
    public class LipogramConfiguration implements CurtailConfiguration {


        @Override
        public CurtailType getCurtailType() {
            return CurtailType.LIPOGRAM;
        }

        public final List<Character> forbiddenCharacterList;
    }

    @Data
    @JsonTypeName("REVERSE_LIPOGRAM")
    public class ReverseLipogramConfiguration implements CurtailConfiguration {

        @Override
        public CurtailType getCurtailType() {
            return CurtailType.REVERSE_LIPOGRAM;
        }

        List<Character> restrictedCharacterList;
    }
}
