package com.project.services;

import com.common.models.enums.ProjectType;
import com.common.models.enums.SourcingType;
import com.common.models.enums.TagType;
import com.common.models.requests.SearchSortType;
import com.project.dao.entites.QProject;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExplorerService {

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
