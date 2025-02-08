CREATE TABLE events
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    titulo     VARCHAR(255) NOT NULL,
    descricao  TEXT,
    data_hora  DATETIME     NOT NULL,
    duracao    INT          NOT NULL, -- minutos
    usuario_id INT          NOT NULL,
    tipo       ENUM('privado', 'publico') NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES users (id)
);
