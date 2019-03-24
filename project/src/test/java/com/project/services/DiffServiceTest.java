package com.project.services;


import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.junit.Test;

import java.util.LinkedList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class DiffServiceTest {

    private String firstText = "asdas Then there was a brief flash of light, " +
            "nd the thousand horsemen rode up the everglade. Sword in hands and " +
            "fire in their eyes - their king was dead, their people slaughtered, " +
            "their homes aflame - nothing left for them to do but do what they were " +
            "trained to do. Kill, destroy, burn - all for their country and their honor. " +
            "A deep silence, a gasp before the plunge into the terrifying melee.";
    private String secondText = "Then there was a brief flash of light, " +
            "nd the thousand horsemen rode up the everglade. Sword in hands and " +
            "fire in their eyes - their saint was dead, their people slaughtered, " +
            "their homes aflame - nothing left for them to do but do what they were " +
            "trained to do. Kill, destroy, burn - all for their country and their honor. " +
            "A deep silence, a gasp before the plunge into a terrifying melee. Lest they" +
            "dare to break the silence or their mortal coil be trampled.";
    @Test
    public void testDiff() {
        DiffMatchPatch diffMatchPatch = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Diff> diffs = diffMatchPatch.diffMain(firstText, secondText);
        String deltaString = diffMatchPatch.diffToDelta(diffs);
        LinkedList<DiffMatchPatch.Diff> diffsFromDelta = diffMatchPatch.diffFromDelta(firstText, deltaString);

        String patchedDiff = diffMatchPatch.diffText2(diffsFromDelta);
        assertThat(secondText).isEqualTo(patchedDiff);
        String unPatchedDiff = diffMatchPatch.diffText1(diffsFromDelta);
        assertThat(firstText).isEqualTo(unPatchedDiff);
    }

}
