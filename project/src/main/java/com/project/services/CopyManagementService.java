package com.project.services;

import com.project.dao.entites.Copy;
import com.project.dao.repository.CopyRepository;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class CopyManagementService {

    @Autowired
    private CopyRepository copyRepository;

    private DiffMatchPatch diffMatchPatch = new DiffMatchPatch();;

    public void addValueToCopy(Copy copy, String partValue) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(copy.getValue());
        stringBuilder.append(" ");
        stringBuilder.append(partValue);
        copy.setValue(stringBuilder.toString());
        copyRepository.save(copy);
    }

    public void applyDeltaToCopy(String delta, Copy copy) {
        LinkedList<DiffMatchPatch.Patch> diffsFromDelta = new LinkedList<>(diffMatchPatch.patchFromText(delta));
        copy.setValue((String) diffMatchPatch.patchApply(diffsFromDelta, copy.getValue())[0]);
        copyRepository.save(copy);
    }

    public void revertDeltaToCopy(String delta, Copy copy) {
        //TODO: IMPLEMENT ME!
        throw new UnsupportedOperationException("Cannot revert delta's at this point");
    }
}
