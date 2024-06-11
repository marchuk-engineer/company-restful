-- changeset misha:17179455452418-1
CREATE TYPE "user_role" AS ENUM (
    'USER',
    'EDITOR',
    'ADMIN');

-- changeset misha:1717945229118-1
CREATE TABLE if not exists smida_schema.company
(
    id                  UUID         NOT NULL,
    name                VARCHAR(255) NOT NULL,
    registration_number VARCHAR(255) NOT NULL,
    address             VARCHAR(255) NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_company PRIMARY KEY (id)
);

-- changeset misha:1717945229118-2
CREATE TABLE IF NOT EXISTS smida_schema.report
(
    id            UUID    NOT NULL,
    report_date   TIMESTAMP WITHOUT TIME ZONE,
    total_revenue DECIMAL NOT NULL,
    net_profit    DECIMAL NOT NULL,
    company_id    UUID    NOT NULL,
    CONSTRAINT pk_report PRIMARY KEY (id),
    CONSTRAINT fk_report_on_company FOREIGN KEY (company_id) REFERENCES smida_schema.company (id)
);

-- changeset misha:1717945229118-3
CREATE TABLE if not exists smida_schema.users
(
    id       UUID         NOT NULL,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     user_role    NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
