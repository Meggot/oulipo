package com.curtail.tails;

import com.common.models.enums.CurtailType;
import org.assertj.core.util.Lists;

import java.util.List;

public class ReverseLipogramTail implements AbstractCurtail {
    private List<Character> charList;

    public ReverseLipogramTail(List<Character> allowedCharacterList) {
        this.charList = allowedCharacterList;
    }

    @Override
    public boolean canHandle(CurtailType curtailType) {
        return curtailType == CurtailType.REVERSE_LIPOGRAM;
    }

    @Override
    public boolean isTextValid(String text) {
        //Here we must make sure that every word in this block of text contains atleast one of the letters.
        //TODO:: STRIP HYPHENS AND PUNCTUATION..
        return sanitizeAndSplitString(text).stream()
                .allMatch(word -> charList.stream()
                        .anyMatch(neededChar -> word.toLowerCase().indexOf(neededChar) >= 0));
    }

    /**
     *  Sanitize string by stripping punctuation and then splitting on space.
     *
     *  - edge cases if a word has a hyphen without a space.
     *  - also if a brackets are used.
     * @param unclean
     * @return
     */
    private List<String> sanitizeAndSplitString(String unclean) {
        return Lists.newArrayList(unclean
                .replace(" - ", " ")
                .replace("[^a-zA-Z ]", "")
                .toLowerCase()
                .split(" "));
    }
}
