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
);