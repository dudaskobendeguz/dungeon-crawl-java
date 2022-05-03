

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
    max_hp              integer         NOT NULL,
    fireball_timer      integer         NOT NULL,
    damage              integer         NOT NULL,
    direction_type      integer         NOT NULL,
    cell_type           integer         NOT NULL,
    weapon_type         integer         NOT NULL,
    items               integer ARRAY   NOT NULL,
    x                   integer         NOT NULL,
    y                   integer         NOT NULL
);

DROP TABLE IF EXISTS cell;
CREATE TABLE cell (
    id                  serial          NOT NULL,
    game_state_id       integer         NOT NULL,
    type_id             integer         NOT NULL,
    x                   integer         NOT NULL,
    y                   integer         NOT NULL
);

ALTER TABLE ONLY cell
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES game_state(id);

ALTER TABLE ONLY player
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES game_state(id);