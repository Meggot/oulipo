CREATE TABLE Passwords(
    pk_pwd_id INTEGER PRIMARY KEY IDENTITY,
    hash_value varchar(100) NOT NULL,
    creation_date DATE NOT NULL,
    modified_date DATE NOT NULL,
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
   creation_date DATE NOT NULL,
   modified_date DATE NOT NULL,
   deleted INTEGER DEFAULT 0,
   oca INTEGER DEFAULT 1
);