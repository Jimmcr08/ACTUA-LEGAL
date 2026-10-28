CREATE  TABLE IF NOT EXISTS caso (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    apoderado_judicial TEXT NOT NULL,
    demandante TEXT NOT NULL,
    demandado TEXT NOT NULL,
    cuantia REAL NOT NULL,
    despacho_judicial TEXT NOT NULL,
    radicado TEXT UNIQUE,
    actuaciones_seguimiento TEXT NOT NULL,
    etapa_procesal TEXT NOT NULL, 
    actuaciones_a_realizar TEXT NOT NULL
);