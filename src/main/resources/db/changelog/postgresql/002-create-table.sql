CREATE TYPE "user_role" AS ENUM (
    'USER',
    'EDITOR',
    'ADMIN');

-- liquibase formatted sql

-- changeset misha:1717945229118-1
CREATE TABLE smida_schema.company
(
    id                  UUID         NOT NULL,
    name                VARCHAR(255) NOT NULL,
    registration_number VARCHAR(255) NOT NULL,
    address             VARCHAR(255) NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_company PRIMARY KEY (id)
);

-- changeset misha:1717945229118-2
CREATE TABLE smida_schema.report
(
    id            UUID    NOT NULL,
    report_date   TIMESTAMP WITHOUT TIME ZONE,
    total_revenue DECIMAL NOT NULL,
    net_profit    DECIMAL NOT NULL,
    company_id    UUID    NOT NULL,
    CONSTRAINT pk_report PRIMARY KEY (id)
);

-- changeset misha:1717945229118-3
CREATE TABLE smida_schema.user
(
    id       UUID         NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    role     user_role    NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

-- changeset misha:1717945229118-4
ALTER TABLE smida_schema.user
    ADD CONSTRAINT uc_user_username UNIQUE (username);

-- changeset misha:1717945229118-5
ALTER TABLE smida_schema.report
    ADD CONSTRAINT FK_REPORT_ON_COMPANY FOREIGN KEY (company_id) REFERENCES smida_schema.company (id);

