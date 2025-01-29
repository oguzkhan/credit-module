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

INSERT INTO customer (id, name, surname, credit_limit, used_credit_limit)
VALUES ('d0f8bbf3-d6e4-4b30-acc6-8c7c6f69f77e', 'Ahmet', 'Yılmaz', 10000.0, 0.0 );

INSERT INTO customer (id, name, surname, credit_limit, used_credit_limit)
VALUES ('52816736-8043-4404-95c6-d53ba6d66529', 'Mehmet', 'Demir', 20000.0, 0.0 );

