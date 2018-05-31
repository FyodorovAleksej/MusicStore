-- COMMENT INSERT
INSERT INTO `musicstore`.`comments` (`text`, `date`, `users_userId`, `tracks_trackId`, `tracks_performers_id`)
SELECT
'good track',                                             -- text
'2017-03-14',                                             -- date
users.userId,                                             -- users_userId
tracks.trackId,                                           -- tracks_trackId
tracks.performers_id                                      -- tracks_performers_id
FROM users, tracks WHERE users.nickName = 'root'          -- user nickName
AND tracks.name = 'outbreak';                             -- track name

INSERT INTO `musicstore`.`comments` (`text`, `date`, `users_userId`, `tracks_trackId`, `tracks_performers_id`)
SELECT
'not bad',                                                -- text
'2017-06-21',                                             -- date
users.userId,                                             -- users_userId
tracks.trackId,                                           -- tracks_trackId
tracks.performers_id                                      -- tracks_performers_id
FROM users, tracks WHERE users.nickName = 'Katrine'       -- user nickName
AND tracks.name = 'outbreak';                             -- track name

INSERT INTO `musicstore`.`comments` (`text`, `date`, `users_userId`, `tracks_trackId`, `tracks_performers_id`)
SELECT
'─────────────────────────▄▀▄
 ─────────────────────────█─█
 ─────────────────────────█─█
 ─────────────────────────█─█
 ─────────────────────────█─█
 ─────────────────────────█─█
 ─────────────────────────█─▀█▀█▄
 ─────────────────────────█──█──█
 ─────────────────────────█▄▄█──▀█
 ────────────────────────▄█──▄█▄─▀█
 ────────────────────────█─▄█─█─█─█
 ────────────────────────█──█─█─█─█
 ────────────────────────█──█─█─█─█
 ────▄█▄──▄█▄────────────█──▀▀█─█─█
 ──▄█████████────────────▀█───█─█▄▀
 ─▄███████████────────────██──▀▀─█
 ▄█████████████────────────█─────█
 ██████████───▀▀█▄─────────▀█────█
 ████████───▀▀▀──█──────────█────█
 ██████───────██─▀█─────────█────█
 ████──▄──────────▀█────────█────█ Look son, a good song!
 ███─▀─██──────█────▀█──────█────█
 ███─────────────────▀█─────█────█
 ███──────────────────█─────█────█
 ███─────────────▄▀───█─────█────█
 ████─────────▄▄██────█▄────█────█
 ████────────██████────█────█────█
 █████────█──███████▀──█───▄█▄▄▄▄█
 ██▀▀██────▀─██▄──▄█───█───█─────█
 ██▄──────────██████───█───█─────█
 ─██▄────────────▄▄────█───█─────█
 ─███████─────────────▄█───█─────█
 ──██████─────────────█───█▀─────█
 ──▄███████▄─────────▄█──█▀──────█
 ─▄█─────▄▀▀▀█───────█───█───────█
 ▄█────────█──█────▄███▀▀▀▀──────█
 █──▄▀▀────────█──▄▀──█──────────█
 █────█─────────█─────█──────────█
 █────────▀█────█─────█─────────██
 █───────────────█──▄█▀─────────█
 █──────────██───█▀▀▀───────────█
 █───────────────█──────────────█
 █▄─────────────██──────────────█
 ─█▄────────────█───────────────█
 ──██▄────────▄███▀▀▀▀▀▄────────█
 ─█▀─▀█▄────────▀█──────▀▄──────█
 ─█────▀▀▀▀▄─────█────────▀─────█﻿',                       -- text
'2017-06-13',                                             -- date
users.userId,                                             -- users_userId
tracks.trackId,                                           -- tracks_trackId
tracks.performers_id                                      -- tracks_performers_id
FROM users, tracks WHERE users.nickName = 'kaloken'       -- user nickName
AND tracks.name = 'words';                                -- track name
