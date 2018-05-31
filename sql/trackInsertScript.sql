-- TRACK INSERT
INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('new game',            -- name
'electro',              -- genre
'23',                   -- price
'2015-03-21',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'NitroFun'));         -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('final boss',          -- name
'electro',              -- genre
'20',                   -- price
'2015-03-10',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'NitroFun'));         -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('turbo penguin',       -- name
'electro',              -- genre
'14',                   -- price
'2016-02-22',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'NitroFun'));         -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('time bomb',           -- name
'electro,classic',      -- genre
'25',                   -- price
'2016-08-15',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Feint'));            -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('words',               -- name
'electro,classic',      -- genre
'15',                   -- price
'2015-08-20',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Feint'));            -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('outbreak',            -- name
'electro,rock',         -- genre
'25',                   -- price
'2017-11-12',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Feint'));            -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('emoji',               -- name
'electro',              -- genre
'21',                   -- price
'2016-11-04',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Pegboard Nerds'));   -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('hero',                -- name
'electro,pop',          -- genre
'20',                   -- price
'2015-05-20',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Pegboard Nerds'));   -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('get crazy',               -- name
'electro',              -- genre
'10',                   -- price
'2015-01-09',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Muzzy'));            -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('spectrum',            -- name
'electro',              -- genre
'20',                   -- price
'2016-12-24',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Muzzy'));            -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('crescendo',           -- name
'electro',              -- genre
'18',                   -- price
'2015-05-21',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Muzzy'));            -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('promises',            -- name
'rock,electro',         -- genre
'21',                   -- price
'2014-02-03',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Nero'));             -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('dreams',              -- name
'rock,electro',         -- genre
'10',                   -- price
'2017-10-23',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Nero'));             -- performers_id


INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('chokehold',           -- name
'electro',              -- genre
'17',                   -- price
'2016-02-12',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Stonebank'));        -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('stronger',            -- name
'electro',              -- genre
'22',                   -- price
'2014-01-25',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Stonebank'));        -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('all night',           -- name
'electro',              -- genre
'27',                   -- price
'2017-02-21',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Stonebank'));        -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('part of the swarm',   -- name
'electro',              -- genre
'20',                   -- price
'2018-01-20',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'SayMaxWell'));       -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('error code',          -- name
'electro',              -- genre
'13',                   -- price
'2015-02-25',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Case&Point'));       -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('fugitive',            -- name
'electro',              -- genre
'14',                   -- price
'2016-11-27',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Case&Point'));       -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('savage',              -- name
'electro',              -- genre
'13',                   -- price
'2016-12-26',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Case&Point'));       -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('paradigm',            -- name
'electro',              -- genre
'22',                   -- price
'2016-07-11',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Case&Point'));       -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('prism',               -- name
'electro',              -- genre
'24',                   -- price
'2016-10-14',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Case&Point'));       -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('ave maria',           -- name
'classic',              -- genre
'20',                   -- price
'2015-09-20',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Franz Schubert'));   -- performers_id

INSERT INTO `musicstore`.`tracks` (`name`, `genre`, `price`, `date`, `performers_id`)
VALUES
('andante in C major',  -- name
'electro',              -- genre
'35',                   -- price
'2015-04-26',           -- date
(SELECT performerId FROM musicstore.performers WHERE performerName =  'Franz Schubert'));   -- performers_id
