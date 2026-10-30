CREATE TABLE IF NOT EXISTS caso (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    clase TEXT NOT NULL,
    apoderado_judicial TEXT NOT NULL,
    demandante TEXT NOT NULL,
    demandado TEXT NOT NULL,
    cuantia TEXT NOT NULL,
    despacho_judicial TEXT NOT NULL,
    radicado TEXT UNIQUE NOT NULL,
    actuaciones_seguimiento TEXT NOT NULL,
    etapa_procesal TEXT NOT NULL,
    actuaciones_a_realizar TEXT NOT NULL,
    url_drive TEXT NOT NULL
);