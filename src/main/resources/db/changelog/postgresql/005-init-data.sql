SET search_path TO smida_schema, public;
-- changeset misha:1717945235118-3
INSERT INTO smida_schema.company (id, name, registration_number, address, created_at)
VALUES (uuid_generate_v4(), 'Company Name 1', 'REG123456789', 'Kyiv, Ukraine', '2023-06-01T12:00:00Z'),
       (uuid_generate_v4(), 'Company Name 2', 'REG123456790', 'Kharkiv, Ukraine', '2023-06-02T12:00:00Z'),
       (uuid_generate_v4(), 'Company Name 3', 'REG123456791', 'Lviv, Ukraine', '2023-06-03T12:00:00Z'),
       (uuid_generate_v4(), 'Company Name 4', 'REG123456792', 'Odessa, Ukraine', '2023-06-04T12:00:00Z'),
       (uuid_generate_v4(), 'Company Name 5', 'REG123456793', 'Dnipro, Ukraine', '2023-06-05T12:00:00Z'),
       (uuid_generate_v4(), 'Company Name 6', 'REG123456794', 'Zaporizhzhia, Ukraine', '2023-06-06T12:00:00Z'),
       (uuid_generate_v4(), 'Company Name 7', 'REG123456795', 'Khmelnytskyi, Ukraine', '2023-06-07T12:00:00Z'),
       (uuid_generate_v4(), 'Company Name 8', 'REG123456796', 'Vinnytsia, Ukraine', '2023-06-08T12:00:00Z'),
       (uuid_generate_v4(), 'Company Name 9', 'REG123456797', 'Poltava, Ukraine', '2023-06-09T12:00:00Z'),
       (uuid_generate_v4(), 'Company Name 10', 'REG123456798', 'Chernihiv, Ukraine', '2023-06-10T12:00:00Z');

-- changeset misha:17442565229118-3
INSERT INTO smida_schema.report (id, report_date, total_revenue, net_profit, company_id)
SELECT uuid_generate_v4(),
       TIMESTAMP '2023-05-01' + (random() * (TIMESTAMP '2023-05-31' - TIMESTAMP '2023-05-01')),
       random() * 1000000,
       random() * 500000,
       id
FROM (SELECT id FROM smida_schema.company LIMIT 10) AS subquery
         CROSS JOIN
     generate_series(1, 2) AS s;

-- changeset misha:1793523546718-10
INSERT INTO smida_schema.users (id, username, password, role)
VALUES (uuid_generate_v4(),
        'smida-admin',
        '$2a$12$rZc3TdgJXPavzb5lMkda3u4tRZEu9EqkOW0wqm3m.5/vB5vXddbd.',
        'ADMIN'),
       -----
       (uuid_generate_v4(),
        'smida-user',
        '$2a$12$RDYhgtT6E6RZECBpr/TwXOoqKbU6zDVvgUxFU77mZeGgQm.KVzCwi',
        'USER'),
       -----
       (uuid_generate_v4(),
        'smida-editor',
        '$2a$12$zytzutcYyVnw7HyqGyVXguhNNfwRURGCndJ05ph3ucDUm7CcVvLyO',
        'EDITOR');

