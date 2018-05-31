-- USERS INSERT
INSERT INTO `musicstore`.`users` (`nickName`,`email`,`role`,`cash`,`bonus`,`discount`,`password`)
VALUES
('admin',                       -- nickName
'root.root@gmail.com',          -- email
'admin',                        -- role
200,                            -- cash
'free track',                   -- bonus
10,                             -- discount
SHA('root'));                   -- password

INSERT INTO `musicstore`.`users` (`nickName`,`email`,`role`,`cash`,`bonus`,`discount`,`password`)
VALUES
('root',                        -- nickName
'root@gmail.com',               -- email
'admin',                        -- role
100,                            -- cash
'free track,free album',        -- bonus
0,                              -- discount
SHA('root'));                   -- password

INSERT INTO `musicstore`.`users` (`nickName`,`email`,`role`,`cash`,`bonus`,`discount`,`password`)
VALUES
('Moon',                        -- nickName
'Mooner@gmail.com',             -- email
'user',                         -- role
0,                              -- cash
NULL,                           -- bonus
0,                              -- discount
SHA('qwerty'));                 -- password

INSERT INTO `musicstore`.`users` (`nickName`,`email`,`role`,`cash`,`bonus`,`discount`,`password`)
VALUES
('Katrine',                                         -- nickName
'Ekaterina.fyodorova@gmail.com',                    -- email
'admin',                                            -- role
420,                                                -- cash
'free track,free album,free assemblage',            -- bonus
30,                                                 -- discount
SHA('qwerty'));                                     -- password


INSERT INTO `musicstore`.`users` (`nickName`,`email`,`role`,`cash`,`bonus`,`discount`,`password`)
VALUES
('kaloken',                   -- nickName
'Streltsov.gleb@gmail.com',   -- email
'user',                       -- role
150,                          -- cash
'free track',                 -- bonus
0,                            -- discount
SHA('qwerty'));               -- password


INSERT INTO `musicstore`.`users` (`nickName`,`email`,`role`,`cash`,`bonus`,`discount`,`password`)
VALUES
('Ciclop',                      -- nickName
'Makhunov.alexey@gmail.com',    -- email
'user',                         -- role
0,                              -- cash
NULL,                           -- bonus
10,                             -- discount
SHA('qwerty'));                 -- password
