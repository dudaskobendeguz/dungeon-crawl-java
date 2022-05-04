ALTER TABLE IF EXISTS player DROP CONSTRAINT IF EXISTS fk_game_state_id;
ALTER TABLE IF EXISTS cell DROP CONSTRAINT IF EXISTS fk_game_state_id;
ALTER TABLE IF EXISTS monster DROP CONSTRAINT IF EXISTS fk_game_state_id;
ALTER TABLE IF EXISTS item DROP CONSTRAINT IF EXISTS fk_game_state_id;
ALTER TABLE IF EXISTS player DROP CONSTRAINT IF EXISTS player_pkey;
ALTER TABLE IF EXISTS cell DROP CONSTRAINT IF EXISTS cell_pkey;
ALTER TABLE IF EXISTS game_state DROP CONSTRAINT IF EXISTS game_state_pkey;

DROP TABLE IF EXISTS game_state;
CREATE TABLE game_state (
    id                  serial          NOT NULL PRIMARY KEY,
    level_id            integer         NOT NULL,
    saved_at            timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS player;
CREATE TABLE player (
    id                  serial          NOT NULL PRIMARY KEY,
    game_state_id       integer         NOT NULL,
    player_name         text            NOT NULL,
    hp                  integer         NOT NULL,
    fireball_timer      integer         NOT NULL,
    direction_type      integer         NOT NULL,
    cell_type           integer         NOT NULL,
    weapon_type         integer         NOT NULL,
    items               integer ARRAY   NOT NULL,
    x                   integer         NOT NULL,
    y                   integer         NOT NULL
);

DROP TABLE IF EXISTS cell;
CREATE TABLE cell (
    id                  serial          NOT NULL PRIMARY KEY,
    game_state_id       integer         NOT NULL,
    tile_id             integer         NOT NULL,
    x                   integer         NOT NULL,
    y                   integer         NOT NULL
);

DROP TABLE IF EXISTS monster;
CREATE TABLE monster (
    id                  serial          NOT NULL PRIMARY KEY,
    game_state_id       integer         NOT NULL,
    type_id             integer         NOT NULL,
    x                   integer         NOT NULL,
    y                   integer         NOT NULL,
    hp                  integer         NOT NULL,
    timer               integer         NULL,
    direction           integer         NULL
);

DROP TABLE IF EXISTS item;
CREATE TABLE item (
    id                  serial          NOT NULL PRIMARY KEY,
    game_state_id       integer         NOT NULL,
    type_id             integer         NOT NULL,
    x                   integer         NOT NULL,
    y                   integer         NOT NULL
);

ALTER TABLE ONLY item
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES game_state(id);

ALTER TABLE ONLY monster
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES game_state(id);

ALTER TABLE ONLY cell
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES game_state(id);

ALTER TABLE ONLY player
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES game_state(id);