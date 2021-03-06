CREATE TABLE Author(
    pk_author_id INTEGER PRIMARY KEY IDENTITY,
    fk_user_id INTEGER NOT NULL,
    username VARCHAR(255) NOT NULL,
    creation_date VARCHAR(255) NOT NULL,
    modified_date VARCHAR(255) NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)


CREATE TABLE Copy (
    pk_copy_id INTEGER PRIMARY KEY IDENTITY,
    project_id INTEGER NOT NULL,
    value CLOB NOT NULL,
    creation_date VARCHAR(255) NOT NULL,
    modified_date VARCHAR(255) NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE TABLE ProjectPart (
  pk_project_part_id INTEGER PRIMARY KEY IDENTITY,
  sequence INTEGER NOT NULL,
  fk_holding_author_id INTEGER NOT NULL,
  status VARCHAR(255) NOT NULL,
  value CLOB NOT NULL,
  fk_project_id INTEGER NOT NULL,
  creation_date VARCHAR(255) NOT NULL,
  modified_date VARCHAR(255) NOT NULL,
  deleted INTEGER DEFAULT 0,
  oca INTEGER DEFAULT 1
)

CREATE TABLE Project (
    pk_project_id INTEGER PRIMARY KEY IDENTITY,
    title VARCHAR(255) NOT NULL,
    synopsis VARCHAR(255) NOT NULL,
    project_type VARCHAR(255) NOT NULL,
    visibility_type VARCHAR(255) NOT NULL,
    sourcing_type VARCHAR(255) NOT NULL,
    fk_author_id INTEGER NOT NULL,
    creation_date VARCHAR(255) NOT NULL,
    modified_date VARCHAR(255) NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE TABLE AuthorProjectRole (
  pk_author_project_role_id INTEGER PRIMARY KEY,
  fk_author_id INTEGER NOT NULL,
  fk_project_id INTEGER NOT NULL,
  role varchar(255) NOT NULL,
  creation_date VARCHAR(255) NOT NULL,
  modified_date VARCHAR(255) NOT NULL,
  deleted INTEGER DEFAULT 0,
  oca INTEGER DEFAULT 1
)

CREATE TABLE ProjectTag (
  pk_tag_id INTEGER PRIMARY KEY,
  fk_project_id INTEGER NOT NULL,
  type VARCHAR(255) NOT NULL,
  fk_author_id INTEGER,
  value VARCHAR(255) NOT NULL,
  creation_date VARCHAR(255) NOT NULL,
  modified_date VARCHAR(255) NOT NULL,
  deleted INTEGER DEFAULT 0,
  oca INTEGER DEFAULT 1
)

CREATE TABLE CopyEdit (
  pk_copy_edit_id INTEGER PRIMARY KEY,
  fk_copy_id INTEGER NOT NULL,
  delta CLOB NOT NULL,
  fk_author_id INTEGER NOT NULL,
  status VARCHAR(255) NOT NULL,
  creation_date VARCHAR(255) NOT NULL,
  modified_date VARCHAR(255) NOT NULL,
  deleted INTEGER DEFAULT 0,
  oca INTEGER DEFAULT 1
);

-- INSERT INTO Author(pk_author_id, fk_user_id, username, creation_date, modified_date, deleted, oca)
-- VALUES (1, 1, 'TestUser', '2019-01-01', '2019-01-01', 0, 1);