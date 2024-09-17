INSERT INTO categories (name, description, created_at, updated_at)
VALUES
    ('Ficción', 'Libros de narrativa ficticia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('No Ficción', 'Libros de contenido real y factual', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Ciencia', 'Contenido relacionado con ciencia y tecnología', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Historia', 'Contenido sobre eventos históricos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;


INSERT INTO account (name, email, password, biography, created_at, updated_at, role)
VALUES
    ('Juan Pérez', 'juan.perez@example.com', 'password123', 'Narrador y autor de ficción', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'CREATOR'),
    ('María García', 'maria.garcia@example.com', 'password456', 'Especialista en marketing digital', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'READER'),
    ('Carlos López', 'carlos.lopez@example.com', 'password789', 'Ingeniero y divulgador científico', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'CREATOR')
ON CONFLICT DO NOTHING;



INSERT INTO blog (title, content, cover_path, file_path, category_id, user_id)
VALUES
    ('El arte de contar historias', 'Cómo la narrativa impulsa las redes sociales.', '/covers/contar-historias.jpg', '/files/contar-historias.pdf', 66, 11),
    ('El auge de la ciencia', 'Descubrimientos recientes que están cambiando el mundo.', '/covers/auge-ciencia.jpg', '/files/auge-ciencia.pdf', 68, 13),
    ('Vida de un innovador', 'Biografía del creador detrás de la revolución tecnológica.', '/covers/vida-innovador.jpg', '/files/vida-innovador.pdf', 69, 11)
ON CONFLICT DO NOTHING;


INSERT INTO playlists (name, created_at)
VALUES
    ('Playlist de Ficción', CURRENT_TIMESTAMP),
    ('Playlist de Ciencia', CURRENT_TIMESTAMP),
    ('Playlist de Historia', CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;


INSERT INTO video (title, url, duration, cover_path, file_path, created_at, updated_at, category_id, user_id, playlist_id)
VALUES
    ('Cómo crear contenido viral', 'https://videos.contenidoviral.com', 12.45, '/covers/contenido-viral.jpg', '/files/contenido-viral.mp4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 66, 11, 2),
    ('La ciencia detrás del marketing', 'https://videos.marketingciencia.com', 15.30, '/covers/marketing-ciencia.jpg', '/files/marketing-ciencia.mp4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 68, 12, 3),
    ('Historia de los influencers', 'https://videos.influencers.com', 18.00, '/covers/influencers-historia.jpg', '/files/influencers-historia.mp4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 69, 13, 4)
ON CONFLICT DO NOTHING;

