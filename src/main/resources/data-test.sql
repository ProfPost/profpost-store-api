-- Insertar datos de prueba en la tabla categories
INSERT INTO categories (name, description)
VALUES
    ('Ficción', 'Libros de narrativa ficticia'),
    ('No Ficción', 'Libros de contenido real y factual'),
    ('Ciencia', 'Contenido relacionado con ciencia y tecnología'),
    ('Historia', 'Contenido sobre eventos históricos'),
    ('Biografía', 'Relatos sobre la vida de personas'),
    ('Infantil', 'Contenido dirigido a niños')
ON CONFLICT DO NOTHING;

-- Insertar datos de prueba en la tabla blogs
INSERT INTO blog (title, content, cover_path, file_path, category_id)
VALUES
    ('El arte de contar historias', 'Cómo la narrativa impulsa las redes sociales.', '/covers/contar-historias.jpg', '/files/contar-historias.pdf',  1),
    ('El auge de la ciencia', 'Descubrimientos recientes que están cambiando el mundo.', '/covers/auge-ciencia.jpg', '/files/auge-ciencia.pdf',  3),
    ('Vida de un innovador', 'Biografía del creador detrás de la revolución tecnológica.', '/covers/vida-innovador.jpg', '/files/vida-innovador.pdf', 5)
ON CONFLICT DO NOTHING;

-- Insertar datos de prueba en la tabla videos
INSERT INTO video (title, url, duration, cover_path, file_path, created_at, updated_at, category_id) VALUES
    ('Cómo crear contenido viral', 'https://videos.contenidoviral.com', 12.45, '/covers/contenido-viral.jpg', '/files/contenido-viral.mp4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('La ciencia detrás del marketing', 'https://videos.marketingciencia.com', 15.30, '/covers/marketing-ciencia.jpg', '/files/marketing-ciencia.mp4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3),
    ('Historia de los influencers', 'https://videos.influencers.com', 18.00, '/covers/influencers-historia.jpg', '/files/influencers-historia.mp4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4)
ON CONFLICT DO NOTHING;
