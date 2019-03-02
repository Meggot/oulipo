CREATE TABLE Author(
    pk_author_id INTEGER PRIMARY KEY IDENTITY,
    fk_user_id INTEGER NOT NULL,
    username VARCHAR(255) NOT NULL,
    creation_date DATE NOT NULL,
    modified_date DATE NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)


CREATE TABLE Copy (

    pk_copy_id INTEGER PRIMARY KEY IDENTITY,
    project_id INTEGER NOT NULL,
    value VARCHAR(255) NOT NULL,
    creation_date DATE NOT NULL,
    modified_date DATE NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE TABLE ProjectPart (
  pk_project_part_id INTEGER PRIMARY KEY IDENTITY,
  sequence INTEGER NOT NULL,
  fk_holding_author_id INTEGER NOT NULL,
  status VARCHAR(255) NOT NULL,
  value VARCHAR(255) NOT NULL,
  fk_project_id INTEGER NOT NULL,
  creation_date DATE NOT NULL,
  modified_date DATE NOT NULL,
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
    creation_date DATE NOT NULL,
    modified_date DATE NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
);

INSERT INTO Author(pk_author_id, fk_user_id, username, creation_date, modified_date, deleted, oca)
VALUES (1, 1, 'TestUser', '2019-01-01', '2019-01-01', 0, 1);