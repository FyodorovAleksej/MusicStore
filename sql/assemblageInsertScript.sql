-- ASSEMBLAGE INSERT
INSERT INTO `musicstore`.`assemblages` (`name`, `genre`, `price`, `date`, `users_userId`)
VALUES
('old',             -- name
'electro,classic',  -- genre
'20',               -- price
'2018-02-10',       -- date
(SELECT userId FROM musicstore.users WHERE nickName = 'root'));       -- users_userId

INSERT INTO `musicstore`.`assemblages` (`name`, `genre`, `price`, `date`, `users_userId`)
VALUES
('new',             -- name
'electro,pop',      -- genre
'20',               -- price
'2018-02-15',       -- date
(SELECT userId FROM musicstore.users WHERE nickName = 'root'));       -- users_userId

INSERT INTO `musicstore`.`assemblages` (`name`, `genre`, `price`, `date`, `users_userId`)
VALUES
('electro',         -- name
'electro',          -- genre
'20',               -- price
'2018-02-17',       -- date
(SELECT userId FROM musicstore.users WHERE nickName = 'kaloken'));    -- users_userId

INSERT INTO `musicstore`.`assemblages` (`name`, `genre`, `price`, `date`, `users_userId`)
VALUES
('monstercat',      -- name
'electro',          -- genre
'20',               -- price
'2018-03-18',       -- date
(SELECT userId FROM musicstore.users WHERE nickName = 'Katrine'));    -- users_userId
