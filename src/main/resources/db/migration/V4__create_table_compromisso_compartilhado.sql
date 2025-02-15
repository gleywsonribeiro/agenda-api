CREATE TABLE compromisso_compartilhado
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    compromisso_id BIGINT NOT NULL,
    usuario_id     INT NOT NULL,
    permissao      ENUM('VISUALIZAR', 'EDITAR') NOT NULL DEFAULT 'VISUALIZAR',
    FOREIGN KEY (compromisso_id) REFERENCES compromissos (id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES users (id) ON DELETE CASCADE
);
