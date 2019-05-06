CREATE Table Notification(
    pk_notification_id INTEGER PRIMARY KEY IDENTITY,
    entity_id INTEGER NOT NULL,
    type VARCHAR(255) NOT NULL,
    message VARCHAR(255) NOT NULL,
    creation_date DATE NOT NULL,
    modified_date DATE NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE Table Subscription(
    pk_subscription_id INTEGER PRIMARY KEY IDENTITY,
    notification_type VARCHAR(255) NOT NULL,
    entity_id VARCHAR(255) NOT NULL,
    fk_postbox_id INTEGER,
    creation_date DATE,
    modified_date DATE,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE TABLE NotificationMail(
    pk_notification_mail_id INTEGER PRIMARY KEY IDENTITY,
    fk_notification_id INTEGER,
    fk_postbox_id INTEGER,
    creation_date DATE,
    modified_date DATE,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
)

CREATE Table Postbox(
    pk_postbox_id INTEGER PRIMARY KEY IDENTITY,
    user_id INTEGER NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    mail_flag_status VARCHAR(255) NOT NULL,
    online BOOLEAN DEFAULT FALSE,
    creation_date DATE NOT NULL,
    modified_date DATE NOT NULL,
    deleted INTEGER DEFAULT 0,
    oca INTEGER DEFAULT 1
);