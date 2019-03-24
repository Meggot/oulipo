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
);

-- INSERT INTO Passwords(pk_pwd_id, hash_value) VALUES (1, 'TestPassword')
-- INSERT INTO Accounts(pk_user_id, fk_pwd_id, account_type, username, email, status) VALUES (1, 1, 1, 'Meggot', 'bradleywilliamsgb@gmail.com', 'ACTIVE');