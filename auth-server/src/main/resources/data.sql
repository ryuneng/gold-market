INSERT INTO user
    (id, email, password, nickname, phone_number, created_at, updated_at, is_deleted)
VALUES
    (1, 'admin@test.com', 'test1234', '토끼', '01011223344', '2024-09-08 12:00:00', '2024-09-08 12:00:00', false),
    (2, 'user@test.com', 'test1234', '거북이', '01055667788', '2024-09-08 12:00:00', '2024-09-08 12:00:00', false);

INSERT INTO user_role_list
    (role_list, user_id)
VALUES
    ('ADMIN', 1),
    ('USER', 2);
