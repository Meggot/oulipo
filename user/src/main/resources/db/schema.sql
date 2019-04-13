CREATE TABLE Passwords(
    pk_pwd_id INTEGER PRIMARY KEY IDENTITY,
    hash_value varchar(100) NOT NULL,
    creation_date VARCHAR(255) NOT NULL,
    modified_date VARCHAR(255) NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE TABLE Accounts(
   pk_account_id INTEGER PRIMARY KEY IDENTITY,
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
)

CREATE TABLE Message (
   pk_message_id INTEGER PRIMARY KEY IDENTITY,
   fk_sender_id INTEGER NOT NULL,
   fk_recipient_id INTEGER NOT NULL,
   message_value CLOB NOT NULL,
   creation_date VARCHAR(255) NOT NULL,
   modified_date VARCHAR(255) NOT NULL,
   deleted INTEGER DEFAULT 0,
   oca INTEGER DEFAULT 1
)

CREATE TABLE Groups (
    pk_group_id INTEGER PRIMARY KEY IDENTITY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    creation_date VARCHAR(255) NOT NULL,
    modified_date VARCHAR(255) NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE TABLE AccountGroupMembership (
   pk_account_membership_id INTEGER PRIMARY KEY IDENTITY,
   fk_account_id INTEGER NOT NULL,
   fk_added_by INTEGER,
   fk_group_id INTEGER NOT NULL,
   role VARCHAR(255) NOT NULL,
    creation_date VARCHAR(255) NOT NULL,
    modified_date VARCHAR(255) NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE TABLE ProjectGroupMembership(
   pk_project_membership_id INTEGER PRIMARY KEY IDENTITY,
   project_id INTEGER NOT NULL,
   project_name VARCHAR(255) NOT NULL,
   fk_group_id INTEGER NOT NULL,
   fk_added_by_id INTEGER NOT NULL,
   creation_date VARCHAR(255) NOT NULL,
   modified_date VARCHAR(255) NOT NULL,
   deleted INTEGER DEFAULT 0,
   oca INTEGER DEFAULT 1
);

-- INSERT INTO Passwords(pk_pwd_id, hash_value) VALUES (1, 'TestPassword')
-- INSERT INTO Accounts(pk_user_id, fk_pwd_id, account_type, username, email, status) VALUES (1, 1, 1, 'Meggot', 'bradleywilliamsgb@gmail.com', 'ACTIVE');