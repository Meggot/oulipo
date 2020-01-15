package com.curtail.tails;

import com.common.models.enums.CurtailType;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class LipogramTailTest {

    @SuppressWarnings("UnstableApiUsage")
    private List<Character> hjklList = Lists.charactersOf("hjkl");

    @Test
    public void canHandle() {
        LipogramTail underTest = new LipogramTail(hjklList);
        assertThat(underTest.canHandle(CurtailType.LIPOGRAM)).isTrue();
        assertThat(underTest.canHandle(CurtailType.ABECEDARIUS)).isFalse();
    }

    @Test
    public void isTextValid() {
        LipogramTail underTest = new LipogramTail(hjklList);
        String textContainingH = "Hai";
        String textContainingJ = "Joy";
        String textContainingK = "Ketosis";
        String textContainingL = "Loped";
        String notContainingAny = "Ayo";
        String testScriptNotWorking = "Hello, there is some natural text here";
        String testScriptWorking = "Good Morning, view my tasty test";

        assertThat(underTest.isTextValid(textContainingH)).isFalse();
        assertThat(underTest.isTextValid(textContainingJ)).isFalse();
        assertThat(underTest.isTextValid(textContainingK)).isFalse();
        assertThat(underTest.isTextValid(textContainingL)).isFalse();

        assertThat(underTest.isTextValid(notContainingAny)).isTrue();
        assertThat(underTest.isTextValid(testScriptNotWorking)).isFalse();
        assertThat(underTest.isTextValid(testScriptWorking)).isTrue();
    }

}