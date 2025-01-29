-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE customer
(
    id                      UUID PRIMARY KEY,
    name                    VARCHAR(255),
    surname                 VARCHAR(255),
    credit_limit            DECIMAL(18, 4),
    used_credit_limit       DECIMAL(18, 4)
);

CREATE TABLE loan
(
    id                      UUID PRIMARY KEY,
    customer_id             UUID NOT NULL REFERENCES customer (id),
    loan_amount             DECIMAL(18, 4),
    interest_rate           DOUBLE PRECISION,
    number_of_installments  INTEGER,
    create_date             timestamp,
    is_paid                 BOOLEAN
);

CREATE TABLE loan_installment
(
    id                      UUID PRIMARY KEY,
    loan_id                 UUID NOT NULL REFERENCES loan (id),
    amount                  DECIMAL(18, 4),
    paid_amount             DECIMAL(18, 4),
    due_date                timestamp,
    payment_date            timestamp,
    is_paid                 BOOLEAN
);