CREATE TABLE users(
    id TEXT PRIMARY KEY NOT NULL,
    username TEXT NOT NULL,
    avatar TEXT,
    password TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);