-- ===== USERS =====
INSERT INTO users (id, userame, password, real_name, surname, email, register_time)
VALUES
  (1, 'mqt',       'password123', 'Mateo', 'Quintela', 'mateo@pixelarium.dev', '2024-01-10'),
  (2, 'zelda_fan', 'hyrule2024',  'Link',  'Hyrule',   'link@pixelarium.dev',  '2024-02-15'),
  (3, 'samus',     'metroid42',   'Samus', 'Aran',     'samus@pixelarium.dev', '2024-03-20');

-- ===== PRODUCT =====

INSERT INTO product (id, name, description, price, sale_price, image_path, stock, category)
VALUES
  (1, 'The Legend of Zelda: Tears of the Kingdom',
     'Adventure game for Nintendo Switch.',
     69.99, 59.99, '/images/zelda_totk.jpg', 50, 'NINTENDO_SWITCH'),

  (2, 'Metroid Prime Remastered',
     'First-person adventure for Nintendo Switch.',
     39.99, NULL, '/images/metroid_prime.jpg', 30, 'NINTENDO_SWITCH'),

  (3, 'Nintendo Switch Pro Controller',
     'Wireless controller for Nintendo Switch.',
     69.99, 54.99, '/images/pro_controller.jpg', 40, 'ACCESSORIES'),

  (4, 'Apple AirPods Pro (2nd gen)',
     'Noise-cancelling wireless earbuds.',
     279.00, 249.00, '/images/airpods_pro_2.jpg', 25, 'APPLE'),

  (5, 'RTX 3060 12GB',
     'NVIDIA RTX 3060 12GB graphics card.',
     349.00, 329.00, '/images/rtx3060_12gb.jpg', 15, 'PC');

-- ===== ORDERS =====
-- order_date: formato 'YYYY-MM-DDTHH:MM:SS' para LocalDateTime
-- status: se guarda el nombre del enum interno StatusType (DRAFT, PENDING, SENT, DELIVERED)

INSERT INTO orders (id, user_id, order_date, total_price, status)
VALUES
  (1, 1, '2024-04-01T10:30:00', 119.98, 'PENDING'),   -- mqt compra 2 juegos
  (2, 1, '2024-05-05T18:45:00', 249.00, 'SENT'),      -- mqt compra AirPods
  (3, 2, '2024-06-10T16:20:00', 124.98, 'DELIVERED'); -- zelda_fan compra juego + mando

-- ===== ORDER_ITEMS =====
-- unit_price: snapshot del precio en el momento del pedido

-- Pedido 1 (id = 1) - usuario 1 (mqt)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (1, 1, 1, 1, 59.99),  -- 1x Zelda TOTK en oferta
  (2, 1, 2, 1, 59.99);  -- 1x Metroid (precio especial hipotético)

-- Pedido 2 (id = 2) - usuario 1 (mqt)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (3, 2, 4, 1, 249.00); -- 1x AirPods Pro

-- Pedido 3 (id = 3) - usuario 2 (zelda_fan)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (4, 3, 1, 1, 69.99),  -- 1x Zelda TOTK a precio normal
  (5, 3, 3, 1, 54.99);  -- 1x Pro Controller en oferta