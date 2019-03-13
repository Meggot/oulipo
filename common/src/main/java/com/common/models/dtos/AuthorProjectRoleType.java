package com.common.models.dtos;

public enum AuthorProjectRoleType {
    /**
     * CREATOR: the author that created the project originally.
     * CONTRIBUTOR: any author that has contributed any parts or any edits to the project - or any contributor that is invited to.
     * MODERATOR: any author that the CREATOR has decided can moderate a project.
     * BARRED: this author is banned from contributing, used for OPEN projects.
     */
    CREATOR,
    CONTRIBUTOR,
    MODERATOR,
    BARRED;
}
