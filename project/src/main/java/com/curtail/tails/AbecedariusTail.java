package com.curtail.tails;

import com.common.models.enums.CurtailType;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;

import java.util.List;

@Slf4j
public class AbecedariusTail implements AbstractCurtail {

    String alphabet = "abcdefghijklmnopqrstuvwxyz";

    @Override
    public boolean canHandle(CurtailType curtailType) {
        return curtailType == CurtailType.ABECEDARIUS;
    }

    @Override
    public boolean isTextValid(String string) {
        List<String> words = Lists.newArrayList(string.split(" "));
        int currentAlphabetPos = 0;
        for (String currentWord : words) {
            if (currentAlphabetPos == 26) {
                currentAlphabetPos = 0;
                //If we reach the end of the alphabet, we reset back to A
            }
            char alphabetChar = alphabet.charAt(currentAlphabetPos);
            if (currentWord.toLowerCase().charAt(0) != alphabetChar){
                return false;
            }
            currentAlphabetPos++;
        }
        return true;
    }
}
