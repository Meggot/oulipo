package com.curtail.tails;

import com.common.models.enums.CurtailType;

import java.util.List;

public class LipogramTail implements AbstractCurtail {

    List<Character> charList;

    public LipogramTail(List<Character> prohibitedCharacterList) {
        this.charList = prohibitedCharacterList;
    }

    @Override
    public boolean canHandle(CurtailType curtailType) {
        return curtailType == CurtailType.LIPOGRAM;
    }

    @Override
    public boolean isTextValid(String string) {
        return charList.stream()
                .noneMatch(character -> string.toLowerCase().indexOf(character) >= 0);
    }
}
