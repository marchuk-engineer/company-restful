SET search_path TO smida_schema, public;

-- changeset misha:1717425667229118-2
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
        'smida- editor',
        '$2a$12$zytzutcYyVnw7HyqGyVXguhNNfwRURGCndJ05ph3ucDUm7CcVvLyO',
        'EDITOR');