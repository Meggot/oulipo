package com.curtail.tails;

import com.common.models.enums.CurtailType;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class ReverseLipogramTailTest {

     List<Character> characterList = Lists.charactersOf("hjkl");


    @Test
    public void canHandle() {
        ReverseLipogramTail underTest = new ReverseLipogramTail(characterList);
        assertThat(underTest.canHandle(CurtailType.REVERSE_LIPOGRAM)).isTrue();
        assertThat(underTest.canHandle(CurtailType.LIPOGRAM)).isFalse();
    }

    @Test
    public void isTextValid() {
        ReverseLipogramTail underTest = new ReverseLipogramTail(characterList);
        assertThat(underTest.isTextValid("Harry Jules kept laughing")).isTrue();
        assertThat(underTest.isTextValid("Keep on trucking my dear soldier")).isFalse();
        assertThat(underTest.isTextValid("Inline, hold joyless lines. Keep close hearts loving soldiers.")).isTrue();
        assertThat(underTest.isTextValid("Oh jubilence. Have you kept laughing like clowns in heat?")).isFalse();
        assertThat(underTest.isTextValid("Holy has - (Joyfully long had hope)")).isTrue();
    }
}