package com.curtail.tails;

import com.common.models.enums.CurtailType;

public interface AbstractCurtail {

    boolean canHandle(CurtailType curtailType);

    boolean isTextValid(String string);
}
