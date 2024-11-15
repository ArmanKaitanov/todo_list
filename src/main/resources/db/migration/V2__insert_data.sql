-- Insert into users table
INSERT INTO users (id, first_name, last_name, email, password, is_active)
VALUES ('1e7b6bd4-8a74-4b27-ae6e-1093252e5b2d', 'John', 'Doe', 'john.doe@example.com', 'password1', true),
       ('2b1db30c-9a2a-4b0c-84f2-a08e3e00127b', 'Jane', 'Doe', 'jane.doe@example.com', 'password2', true);

-- Insert into todo_items table
INSERT INTO todo_items (id, title, description, user_id)
VALUES ('3d8a8f50-2c7d-4c4a-bc77-6147d582f3ad', 'Buy groceries', 'Milk, Bread, Eggs',
        '1e7b6bd4-8a74-4b27-ae6e-1093252e5b2d'),
       ('4f6c789c-1c25-49de-94b9-e2c8ad7d1f91', 'Read a book', 'Start reading ''War and Peace''',
        '2b1db30c-9a2a-4b0c-84f2-a08e3e00127b');
