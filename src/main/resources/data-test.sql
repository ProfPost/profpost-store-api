-- Insertar datos de prueba en la tabla categories
INSERT INTO categories (name, description, created_at, updated_at)
VALUES
    ('Ficción', 'Libros de narrativa ficticia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('No Ficción', 'Libros de contenido real y factual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Ciencia', 'Contenido relacionado con ciencia y tecnología', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Historia', 'Contenido sobre eventos históricos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Biografía', 'Relatos sobre la vida de personas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Infantil', 'Contenido dirigido a niños', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

-- Insertar datos de prueba en la tabla blogs
INSERT INTO blogs (title, content, cover_path, file_path, created_at, updated_at, category_id)
VALUES
    ('El arte de contar historias', 'Cómo la narrativa impulsa las redes sociales.', '/covers/contar-historias.jpg', '/files/contar-historias.pdf', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('El auge de la ciencia', 'Descubrimientos recientes que están cambiando el mundo.', '/covers/auge-ciencia.jpg', '/files/auge-ciencia.pdf', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3),
    ('Vida de un innovador', 'Biografía del creador detrás de la revolución tecnológica.', '/covers/vida-innovador.jpg', '/files/vida-innovador.pdf', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5)
ON CONFLICT DO NOTHING;

-- Insertar datos de prueba en la tabla videos
INSERT INTO videos (title, url, durantion, cover_path, file_path, created_at, updated_at, category_id) VALUES
                                                                                                           ('Cómo crear contenido viral', 'https://videos.contenidoviral.com', 12.45, '/covers/contenido-viral.jpg', '/files/contenido-viral.mp4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
                                                                                                           ('La ciencia detrás del marketing', 'https://videos.marketingciencia.com', 15.30, '/covers/marketing-ciencia.jpg', '/files/marketing-ciencia.mp4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3),
                                                                                                           ('Historia de los influencers', 'https://videos.influencers.com', 18.00, '/covers/influencers-historia.jpg', '/files/influencers-historia.mp4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4)
ON CONFLICT DO NOTHING;
