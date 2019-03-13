CREATE TABLE Metric(
    pk_metric_id INTEGER PRIMARY KEY IDENTITY,
    metric_type varchar(255) NOT NULL,
    entity_id INTEGER NOT NULL,
    entity_type varchar(255) NOT NULL,
    user_id INTEGER NOT NULL,
    creation_date DATE NOT NULL,
    modified_date DATE NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE TABLE Comment(
  pk_comment_id INTEGER PRIMARY KEY IDENTITY,
  entity_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL,
  value varchar(255) NOT NULL,
  creation_date DATE NOT NULL,
  modified_date DATE NOT NULL,
  deleted INTEGER DEFAULT 0,
  oca INTEGER DEFAULT 1
);