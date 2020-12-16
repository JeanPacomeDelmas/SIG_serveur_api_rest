DROP TABLE IF EXISTS qrcode;
DROP TABLE IF EXISTS utilisateur;
CREATE TABLE IF NOT EXISTS public.qrcode (
    text character varying(254),
    id_etage integer,
    geom public.geometry(Point)
);

CREATE TABLE IF NOT EXISTS public.utilisateur (
    nom character varying(254),
    id_etage integer,
    date date,
    geom public.geometry(Point)
);

-- INSERT INTO qrcode(text, id_etage, geom) values ('blabla', 1, ST_GeomFromText('POINT (35 12)'));


