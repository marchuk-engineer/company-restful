-- liquibase formatted sql

-- changeset misha:1717685970627-1
CREATE TABLE smida_schema.company
(
    id                  UUID                        NOT NULL,
    name                VARCHAR(255)                NOT NULL,
    registration_number VARCHAR(255)                NOT NULL,
    address             VARCHAR(255)                NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_company PRIMARY KEY (id)
);

-- changeset misha:1717685970627-2
CREATE TABLE smida_schema.company_report
(
    company_id UUID NOT NULL,
    report_id  UUID NOT NULL
);

-- changeset misha:1717685970627-3
CREATE TABLE smida_schema.report
(
    id            UUID                        NOT NULL,
    report_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    total_revenue DECIMAL                     NOT NULL,
    net_profit    DECIMAL                     NOT NULL,
    company_id    UUID                        NOT NULL,
    CONSTRAINT pk_report PRIMARY KEY (id)
);

-- changeset misha:1717685970627-4
CREATE TABLE smida_schema.user
(
    id       UUID         NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

-- changeset misha:1717685970627-5
ALTER TABLE smida_schema.company_report
    ADD CONSTRAINT uc_company_report_report UNIQUE (report_id);

-- changeset misha:1717685970627-6
ALTER TABLE smida_schema.user
    ADD CONSTRAINT uc_user_username UNIQUE (username);

-- changeset misha:1717685970627-7
ALTER TABLE smida_schema.report
    ADD CONSTRAINT FK_REPORT_ON_COMPANY FOREIGN KEY (company_id) REFERENCES company (id);

-- changeset misha:1717685970627-8
ALTER TABLE smida_schema.company_report
    ADD CONSTRAINT fk_comrep_on_company FOREIGN KEY (company_id) REFERENCES company (id);

-- changeset misha:1717685970627-9
ALTER TABLE smida_schema.company_report
    ADD CONSTRAINT fk_comrep_on_report FOREIGN KEY (report_id) REFERENCES report (id);

