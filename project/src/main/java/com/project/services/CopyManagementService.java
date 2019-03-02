package com.project.services;

import com.project.dao.entites.Copy;
import com.project.dao.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CopyManagementService {

    @Autowired
    CopyRepository copyRepository;

    public void addValueToCopy(Copy copy, String partValue) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(copy.getValue());
        stringBuilder.append(" ");
        stringBuilder.append(partValue);
        copy.setValue(stringBuilder.toString());
        copyRepository.save(copy);
    }
}
