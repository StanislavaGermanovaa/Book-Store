-- user roles
INSERT INTO roles (id, role)
VALUES (1, 'ADMIN');
INSERT INTO roles (id, role)
VALUES (2, 'USER');

--users

INSERT INTO users(id, age, email, full_name, password, username)
VALUES (1, 35, 'admin@admin.com', 'Admin Adminov','$2a$10$w2a27lam.XD6oykAT0TaquZYhqDEAmVbROpmACvL/cD2OJi1qrvaW','ADMIN');
INSERT INTO users(id, age, email, full_name, password, username)
VALUES (2, 20, 'user@user.com', 'User Userov','$2a$10$8Lj.33RVpwyHqiIKWUZsIu33U27ZgjLfcIWd9k7.1QTzbXD.jF.JO','USER');

-- user roles
-- admin
INSERT INTO users_roles (`users_id`, `roles_id`)
VALUES (1, 1);
INSERT INTO users_roles (`users_id`, `roles_id`)
VALUES (1, 2);

-- user
INSERT INTO users_roles (`users_id`, `roles_id`)
VALUES (2, 2);

