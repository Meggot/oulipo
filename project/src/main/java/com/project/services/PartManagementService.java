package com.project.services;

import com.common.models.dtos.PartStatus;
import com.common.models.exceptions.UnauthorizedException;
import com.github.tomakehurst.wiremock.security.NotAuthorisedException;
import com.project.dao.entites.Author;
import com.project.dao.entites.Project;
import com.project.dao.entites.ProjectPart;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.PartRepository;
import com.project.dao.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PartManagementService {

    @Autowired
    PartRepository partRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    ProjectRepository projectRepository;

    public ProjectPart requestPartOnProject(Project project, String userId){
        ProjectPart newPart = new ProjectPart();
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId)).get();
        Optional<ProjectPart> lastCreatedPartOnProject = project.getLastAddedPart();
        if (lastCreatedPartOnProject.isPresent()) {
            newPart.setSequence(new Integer(lastCreatedPartOnProject.get().getSequence()+1));
        } else {
            newPart.setSequence(new Integer(1));
        }
        newPart.setStatus(PartStatus.RESERVED);
        newPart.setValue("sda");
        author.addCreatedPart(newPart);
        project.addPart(newPart);
        newPart = partRepository.save(newPart);
        projectRepository.save(project);
        return newPart;
    }

    public ProjectPart postValueOnPart(ProjectPart part, String userId, String partValue) {
        if (!part.getCurrentlyHoldingAuthor().getUserId().equals(Integer.parseInt(userId))) {
            throw new UnauthorizedException("You are not the current holder of that part");
        }
        part.setValue(partValue);
        part.setStatus(PartStatus.UNDER_REVIEW);
        return part;
    }
}
