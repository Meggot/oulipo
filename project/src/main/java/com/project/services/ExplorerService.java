package com.project.services;

import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.dtos.ProjectType;
import com.common.models.dtos.SourcingType;
import com.common.models.dtos.TagType;
import com.common.models.requests.SearchSortType;
import com.google.common.collect.Lists;
import com.project.dao.entites.Project;
import com.project.dao.entites.QAuthor;
import com.project.dao.entites.QProject;
import com.project.dao.repository.ProjectRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ExplorerService {

    private static List<AuthorProjectRoleType> rolesInWhichYouMayEdit = Lists.newArrayList(AuthorProjectRoleType.CONTRIBUTOR, AuthorProjectRoleType.MODERATOR, AuthorProjectRoleType.CREATOR);

    public BooleanExpression findByPartialTitle(String title) {
        return title != null ? QProject.project.title.containsIgnoreCase(title) : null;
    }

    public BooleanExpression findWhereUserTagsExist(List<String> tagValues) {
        if (tagValues != null && !tagValues.isEmpty()) {
            Predicate tagMatch = QProject.project.tags.any().value.in(tagValues);
            return QProject.project.tags.any().type.eq(TagType.USER_ADDED).and(tagMatch);
        }
        return null;
    }

    public BooleanExpression findWhereHasAuthor(String authorName) {
        return authorName != null ? QProject.project.authorProjectRoles.any().author.username.containsIgnoreCase(authorName) : null;
    }

    public BooleanExpression findWhereType(ProjectType type) {
        return type != null ? QProject.project.type.eq(type) : null;
    }

    public BooleanExpression findWhereSystemTagExist(SearchSortType sortType) {
        if (sortType == null){
            return null;
        }
        Predicate systemTag = QProject.project.tags.any().type.eq(TagType.SYSTEM_ADDED);
        return QProject.project.tags.any().value.equalsIgnoreCase(sortType.toString()).and(systemTag);
    }

    public BooleanExpression findWhereUserIdCanEdit(Integer userId) {
        return QProject.project.sourcingType.eq(SourcingType.OPEN);
    }
}
