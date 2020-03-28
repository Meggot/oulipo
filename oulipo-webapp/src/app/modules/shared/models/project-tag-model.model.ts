// private Integer idField;
//
// private String projectTitle;
//
// private TagType type;
//
// private Integer userIdAdded;
//
// private String value;

export class ProjectTagModel {
  constructor(public id: number,
              public type: string,
              public value: string) {}
}
