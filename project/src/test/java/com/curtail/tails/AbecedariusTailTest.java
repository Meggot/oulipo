package com.curtail.tails;

import com.common.models.enums.CurtailType;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class AbecedariusTailTest {

    AbecedariusTail underTest = new AbecedariusTail();

    @Test
    public void canHandle() {
        assertThat(underTest.canHandle(CurtailType.ABECEDARIUS)).isTrue();
    }

    @Test
    public void isTextValid() {
        String textValid = "A big controlling dog";
        assertThat(underTest.isTextValid(textValid)).isTrue();

        String textInvalid = "A big cat eats";
        assertThat(underTest.isTextValid(textInvalid)).isFalse();

        String resetAlphabet = "a b c d e f g h i j k l m n o p q r s t u v w x y z a b c d e f g";
        assertThat(underTest.isTextValid(resetAlphabet)).isTrue();
    }
}