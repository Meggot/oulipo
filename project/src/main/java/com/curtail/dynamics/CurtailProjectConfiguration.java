package com.curtail.dynamics;

import com.common.models.enums.CurtailType;
import lombok.Data;

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
    public class LipogramConfiguration implements CurtailConfiguration {


        @Override
        public CurtailType getCurtailType() {
            return CurtailType.LIPOGRAM;
        }

        List<Character> forbiddenCharacterList;
    }

    @Data
    public class ReverseLipogramConfiguration implements CurtailConfiguration {

        @Override
        public CurtailType getCurtailType() {
            return CurtailType.REVERSE_LIPOGRAM;
        }

        List<Character> restrictedCharacterList;
    }
}
