-- liquibase formatted sql

-- changeset misha:1717835009477-1
CREATE TABLE company
(
    id                  UUID                        NOT NULL,
    name                VARCHAR(255)                NOT NULL,
    registration_number VARCHAR(255)                NOT NULL,
    address             VARCHAR(255)                NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_company PRIMARY KEY (id)
);

-- changeset misha:1717835009477-2
CREATE TABLE company_reports
(
    company_id UUID NOT NULL,
    reports_id UUID NOT NULL
);

-- changeset misha:1717835009477-3
CREATE TABLE report
(
    id            UUID                        NOT NULL,
    report_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    total_revenue DECIMAL                     NOT NULL,
    net_profit    DECIMAL                     NOT NULL,
    company_id    UUID                        NOT NULL,
    CONSTRAINT pk_report PRIMARY KEY (id)
);

-- changeset misha:1717835009477-4
CREATE TABLE "user"
(
    id       UUID         NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

-- changeset misha:1717835009477-5
ALTER TABLE company_reports
    ADD CONSTRAINT uc_company_reports_reports UNIQUE (reports_id);

-- changeset misha:1717835009477-6
ALTER TABLE "user"
    ADD CONSTRAINT uc_user_username UNIQUE (username);

-- changeset misha:1717835009477-7
ALTER TABLE report
    ADD CONSTRAINT FK_REPORT_ON_COMPANY FOREIGN KEY (company_id) REFERENCES company (id);

-- changeset misha:1717835009477-8
ALTER TABLE company_reports
    ADD CONSTRAINT fk_comrep_on_company FOREIGN KEY (company_id) REFERENCES company (id);

-- changeset misha:1717835009477-9
ALTER TABLE company_reports
    ADD CONSTRAINT fk_comrep_on_report FOREIGN KEY (reports_id) REFERENCES report (id);

