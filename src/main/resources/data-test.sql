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

-- Insertar datos de prueba en la tabla suscription
INSERT INTO suscription (created_at, updated_at, subscription_state)
VALUES
    ('2024-01-01 10:00:00', '2024-01-01 10:00:00', 'SUBSCRIBE'),
    ('2024-01-02 11:00:00', '2024-01-02 11:00:00', 'NON_SUBSCRIBE'),
    ('2024-01-03 12:00:00', '2024-01-03 12:00:00', 'SUBSCRIBE'),
    ('2024-01-04 13:00:00', '2024-01-04 13:00:00', 'NON_SUBSCRIBE');


-- Insertar datos de prueba en la tabla plan
INSERT INTO plan (name_plan, price, description, suscription_id)
VALUES
    ('Plan Mensual', 9.99, 'Acceso a contenido básico', 1),
    ('Plan Mensual', 19.99, 'Acceso a contenido básico', 2),
    ('Plan Mensual', 29.99, 'Acceso a contenido básico', 3),
    ('Plan Anual', 4.99, 'Acceso a contenido premium y exclusivo', 1),
    ('Plan Anual', 49.99, 'Acceso a contenido premium y exclusivo', 4);