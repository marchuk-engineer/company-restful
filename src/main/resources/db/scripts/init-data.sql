INSERT INTO smida_schema.company (id, name, registration_number, address, created_at)
VALUES ('123e4567-e89b-12d3-a456-426614174000', 'Example Company 1', 'REG12345', '123 Example Street',
        '2023-06-07T12:00:00Z'),
       ('123e4567-e89b-12d3-a456-426614174001', 'Example Company 2', 'REG12346', '124 Example Avenue',
        '2023-06-08T12:00:00Z');
INSERT INTO smida_schema.report (id, report_date, total_revenue, net_profit, company_id)
VALUES ('223e4567-e89b-12d3-a456-426614174001', '2023-05-01T00:00:00Z', 100000.00, 50000.00,
        '123e4567-e89b-12d3-a456-426614174000'),
       ('323e4567-e89b-12d3-a456-426614174002', '2023-04-01T00:00:00Z', 150000.00, 75000.00,
        '123e4567-e89b-12d3-a456-426614174000'),
       ('423e4567-e89b-12d3-a456-426614174003', '2023-03-01T00:00:00Z', 200000.00, 100000.00,
        '123e4567-e89b-12d3-a456-426614174001');