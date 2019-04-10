CREATE TABLE Metric(
    pk_metric_id INTEGER PRIMARY KEY IDENTITY,
    metric_type varchar(255) NOT NULL,
    entity_id INTEGER NOT NULL,
    entity_type varchar(255) NOT NULL,
    user_id INTEGER NOT NULL,
    creation_date VARCHAR(255) NOT NULL,
    modified_date VARCHAR(255) NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE TABLE Comment(
  pk_comment_id INTEGER PRIMARY KEY IDENTITY,
  entity_id INTEGER NOT NULL,
  type VARCHAR(255) NOT NULL,
  user_id INTEGER NOT NULL,
  value varchar(255) NOT NULL,
  creation_date VARCHAR(255) NOT NULL,
  modified_date VARCHAR(255) NOT NULL,
  deleted INTEGER DEFAULT 0,
  oca INTEGER DEFAULT 1
)

CREATE Table Audits(
    pk_audit_id INTEGER PRIMARY KEY IDENTITY,
    entity_id INTEGER NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    origin_user_id INTEGER NOT NULL,
    value VARCHAR(255) NOT NULL,
    creation_date DATE NOT NULL,
    modified_date DATE NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE Table Notification(
    pk_audit_id INTEGER PRIMARY KEY IDENTITY,
    entity_id INTEGER NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    origin_user_id INTEGER NOT NULL,
    value VARCHAR(255) NOT NULL,
    creation_date DATE NOT NULL,
    modified_date DATE NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

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
  fk_author_id INTEGER NOT NULL,
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
)

CREATE TABLE Passwords(
    pk_pwd_id INTEGER PRIMARY KEY IDENTITY,
    hash_value varchar(100) NOT NULL,
    creation_date VARCHAR(255) NOT NULL,
    modified_date VARCHAR(255) NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE TABLE Accounts(
   pk_user_id INTEGER PRIMARY KEY IDENTITY,
   fk_pwd_id INTEGER FOREIGN KEY REFERENCES Passwords(pk_pwd_id),
   account_type INTEGER DEFAULT 1,
   username varchar(25) NOT NULL,
   email varchar(50) NOT NULL,
   status varchar(50) NOT NULL,
   dob DATE DEFAULT NULL,
   creation_date VARCHAR(255) NOT NULL,
   modified_date VARCHAR(255) NOT NULL,
   deleted INTEGER DEFAULT 0,
   oca INTEGER DEFAULT 1
)

CREATE TABLE AccountLogin (
   pk_account_login_id INTEGER PRIMARY KEY IDENTITY,
   fk_account_id INTEGER NOT NULL,
   ip_address VARCHAR(255) NOT NULL,
   creation_date VARCHAR(255) NOT NULL,
   modified_date VARCHAR(255) NOT NULL,
   deleted INTEGER DEFAULT 0,
   oca INTEGER DEFAULT 1
)

CREATE TABLE AccountRelationship (
  pk_account_relationship_id INTEGER PRIMARY KEY IDENTITY,
  fk_added_by_account_id INTEGER NOT NULL,
  fk_added_account_id INTEGER NOT NULL,
  status VARCHAR(255) NOT NULL,
  relationship_type VARCHAR(255) NOT NULL,
  creation_date VARCHAR(255) NOT NULL,
  modified_date VARCHAR(255) NOT NULL,
  deleted INTEGER DEFAULT 0,
  oca INTEGER DEFAULT 1
)

CREATE TABLE AccountTag (
  pk_tag_id INTEGER PRIMARY KEY IDENTITY,
  fk_account_id INTEGER NOT NULL,
  tag_type VARCHAR(255) NOT NULL,
  tag_category VARCHAR(255) NOT NULL,
  tag_value VARCHAR(255) NOT NULL,
  creation_date VARCHAR(255) NOT NULL,
  modified_date VARCHAR(255) NOT NULL,
  deleted INTEGER DEFAULT 0,
  oca INTEGER DEFAULT 1
);



