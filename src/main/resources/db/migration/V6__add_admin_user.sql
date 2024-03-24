BEGIN;

ALTER TABLE app_user DISABLE TRIGGER ALL;
ALTER TABLE client DISABLE TRIGGER ALL;
ALTER TABLE system_user_details DISABLE TRIGGER ALL;

insert into app_user("id", "email", "password", "first_name", "last_name", "age" , "gender", "user_details_id")
    values(1, 'admin@fitsynergy.com', '$2y$10$e8PyZjeYvxarDVyboyk3C.LX6rFi89H.T2/dqM8p/.soHu2wcgbe2',
           'admin', 'admin', '40', 'male', 1);

SELECT setval('user_id_seq', 1, true);

insert into client(client_id) values(1);

insert into "system_user_details"("id", "role", "is_expired", "is_locked", "is_credentials_expired", "is_enabled")
values(1, 'ADMIN', false, false, false, true);

SELECT setval('system_user_details_id_seq', 1, true);

ALTER TABLE app_user ENABLE TRIGGER ALL;
ALTER TABLE client ENABLE TRIGGER ALL;
ALTER TABLE system_user_details ENABLE TRIGGER ALL;

COMMIT;
