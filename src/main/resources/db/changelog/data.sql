insert into authorities (authority)
values ('ROLE_USER');
-- insert user for tests
--      username = 89eeb9bc-ddaa-452c-9ea3-6ebf60d05e4b
--      password = 2cae2efd-70fb-4086-99a4-4b59841604cb
INSERT INTO public.users (id, username, password, authority_id)
VALUES (1, '89eeb9bc-ddaa-452c-9ea3-6ebf60d05e4b', '$2a$10$8oxBsHIPu8uY4H3Lhfa8mukpjLMz3qrYwrj7A.FQUqWnnzIPLr7PO', 1);