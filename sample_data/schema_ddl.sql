DROP TABLE IF EXISTS public.game_state;
CREATE TABLE public.game_state (
    id serial NOT NULL PRIMARY KEY,
    current_map text NOT NULL,
    saved_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    player_id integer NOT NULL
);

DROP TABLE IF EXISTS public.player;
CREATE TABLE public.player (
    id                  serial          NOT NULL PRIMARY KEY,
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

DROP TABLE IF EXISTS public.cell;
CREATE TABLE public.cell (
    id serial NOT NULL,
    type_id integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL
);

ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);