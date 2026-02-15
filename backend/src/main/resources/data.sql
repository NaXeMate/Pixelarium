-- ===== USERS =====
INSERT INTO users (id, user_name, password, real_name, surname, email, register_time)
VALUES
  (1, 'mqt',       'password123', 'Mateo',  'Quintela', 'mateo@pixelarium.dev',   '2024-01-10'),
  (2, 'zelda_fan', 'hyrule2024',  'Link',   'Hyrule',   'link@pixelarium.dev',    '2024-02-15'),
  (3, 'samus',     'metroid42',   'Samus',  'Aran',     'samus@pixelarium.dev',   '2024-03-20'),
  (4, 'mario_bro', 'itsameMario',  'Mario', 'Bros',     'mario@pixelarium.dev',   '2024-04-05'),
  (5, 'pc_gamer',  'rgb4life',     'Alex',  'Turner',   'alex@pixelarium.dev',    '2024-05-12'),
  (6, 'apple_fan', 'thinkDiff',    'Steve', 'Johnson',  'steve@pixelarium.dev',   '2024-06-18'),
  (7, 'retro_fan', 'classic80s',   'Ana',   'García',   'ana@pixelarium.dev',     '2024-07-22'),
  (8, 'switch_pro','nintendo_life', 'Luis', 'Martínez', 'luis@pixelarium.dev',    '2024-08-30');

-- ===== PRODUCT =====

INSERT INTO product (id, name, description, price, sale_price, image_path, stock, category)
VALUES
  -- NINTENDO SWITCH
  (1, 'The Legend of Zelda: Tears of the Kingdom',
     'Adventure game for Nintendo Switch.',
     69.99, 59.99, '/images/zelda_totk.jpg', 50, 'NINTENDO_SWITCH'),

  (2, 'Metroid Prime Remastered',
     'First-person adventure for Nintendo Switch.',
     39.99, NULL, '/images/metroid_prime.jpg', 30, 'NINTENDO_SWITCH'),

  (6, 'Super Mario Odyssey',
     'Platform adventure game for Nintendo Switch.',
     59.99, 49.99, '/images/mario_odyssey.jpg', 45, 'NINTENDO_SWITCH'),

  (7, 'Animal Crossing: New Horizons',
     'Life simulation game for Nintendo Switch.',
     59.99, NULL, '/images/animal_crossing.jpg', 60, 'NINTENDO_SWITCH'),

  (8, 'Splatoon 3',
     'Third-person shooter for Nintendo Switch.',
     59.99, 54.99, '/images/splatoon3.jpg', 35, 'NINTENDO_SWITCH'),

  (9, 'Pokémon Scarlet',
     'RPG adventure for Nintendo Switch.',
     59.99, NULL, '/images/pokemon_scarlet.jpg', 40, 'NINTENDO_SWITCH'),

  -- NINTENDO SWITCH 2
  (10, 'Mario Kart World',
      'Racing game for Nintendo Switch 2.',
      69.99, NULL, '/images/mario_kart_9.jpg', 100, 'NINTENDO_SWITCH_2'),

  (11, 'Metroid Prime 4 Nintendo Switch 2 Edition',
      'First-person adventure for Nintendo Switch 2.',
      69.99, 64.99, '/images/metroid_prime_4.jpg', 80, 'NINTENDO_SWITCH_2'),

  -- ACCESSORIES
  (3, 'Nintendo Switch Pro Controller',
     'Wireless controller for Nintendo Switch.',
     69.99, 54.99, '/images/pro_controller.jpg', 40, 'ACCESSORIES'),

  (12, 'Nintendo Switch Carrying Case',
      'Protective carrying case for Nintendo Switch.',
      24.99, 19.99, '/images/switch_case.jpg', 75, 'ACCESSORIES'),

  (13, 'USB-C Charging Cable 3m',
      'Long USB-C cable for gaming devices.',
      14.99, NULL, '/images/usbc_cable.jpg', 120, 'ACCESSORIES'),

  (14, 'Gaming Headset RGB',
      'Surround sound gaming headset with RGB lighting.',
      89.99, 74.99, '/images/gaming_headset.jpg', 55, 'ACCESSORIES'),

  -- APPLE
  (4, 'Apple AirPods Pro (2nd gen)',
     'Noise-cancelling wireless earbuds.',
     279.00, 249.00, '/images/airpods_pro_2.jpg', 25, 'APPLE'),

  (15, 'Apple Magic Keyboard',
      'Wireless keyboard for Mac and iPad.',
      129.00, NULL, '/images/magic_keyboard.jpg', 30, 'APPLE'),

  (16, 'Apple Magic Mouse',
      'Wireless rechargeable mouse.',
      89.00, 79.00, '/images/magic_mouse.jpg', 40, 'APPLE'),

  (17, 'Apple AirTag 4 Pack',
      'Item tracker for finding your belongings.',
      99.00, NULL, '/images/airtag_4pack.jpg', 50, 'APPLE'),

  -- PC
  (5, 'RTX 3060 12GB',
     'NVIDIA RTX 3060 12GB graphics card.',
     349.00, 329.00, '/images/rtx3060_12gb.jpg', 15, 'PC'),

  (18, 'RTX 4070 Ti',
      'NVIDIA RTX 4070 Ti graphics card.',
      799.00, NULL, '/images/rtx4070ti.jpg', 10, 'PC'),

  (19, 'AMD Ryzen 7 7800X3D',
      'High-performance gaming processor.',
      449.00, 429.00, '/images/ryzen_7800x3d.jpg', 20, 'PC'),

  (20, 'Corsair Vengeance DDR5 32GB',
      '32GB DDR5 RAM kit for gaming PCs.',
      149.00, 139.00, '/images/corsair_ddr5.jpg', 35, 'PC');

-- ===== ORDERS =====
-- order_date: format 'YYYY-MM-DDTHH:MM:SS' for LocalDateTime
-- status: stores the internal enum name StatusType (DRAFT, PENDING, SENT, DELIVERED)

INSERT INTO orders (id, user_id, order_date, total_price, status)
VALUES
  (1, 1, '2024-04-01T10:30:00', 119.98, 'PENDING'),   -- mqt buys 2 games
  (2, 1, '2024-05-05T18:45:00', 249.00, 'SENT'),      -- mqt buys AirPods
  (3, 2, '2024-06-10T16:20:00', 124.98, 'DELIVERED'), -- zelda_fan buys game + controller
  (4, 4, '2024-07-15T14:20:00', 49.99,  'DELIVERED'), -- mario_bro buys Mario Odyssey
  (5, 5, '2024-08-20T09:15:00', 897.00, 'SENT'),      -- pc_gamer buys PC components
  (6, 6, '2024-09-05T11:45:00', 328.00, 'PENDING'),   -- apple_fan buys Apple accessories
  (7, 7, '2024-09-18T16:30:00', 94.98,  'DELIVERED'), -- retro_fan buys accessories
  (8, 8, '2024-10-01T13:00:00', 189.97, 'PENDING'),   -- switch_pro buys Switch games
  (9, 3, '2024-10-10T10:10:00', 64.99,  'SENT'),      -- samus buys Metroid Prime 4
  (10, 1, '2024-10-25T17:30:00', 139.98, 'DRAFT');    -- mqt draft order

-- ===== ORDER_ITEMS =====
-- unit_price: snapshot of the price at the time of the order

-- Order 1 (id = 1) - user 1 (mqt)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (1, 1, 1, 1, 59.99),  -- 1x Zelda TOTK on sale
  (2, 1, 2, 1, 59.99);  -- 1x Metroid (hypothetical special price)

-- Order 2 (id = 2) - user 1 (mqt)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (3, 2, 4, 1, 249.00); -- 1x AirPods Pro

-- Order 3 (id = 3) - user 2 (zelda_fan)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (4, 3, 1, 1, 69.99),  -- 1x Zelda TOTK regular price
  (5, 3, 3, 1, 54.99);  -- 1x Pro Controller on sale

-- Order 4 (id = 4) - user 4 (mario_bro)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (6, 4, 6, 1, 49.99);  -- 1x Super Mario Odyssey on sale

-- Order 5 (id = 5) - user 5 (pc_gamer)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (7, 5, 19, 1, 429.00), -- 1x AMD Ryzen 7 7800X3D on sale
  (8, 5, 20, 2, 139.00), -- 2x Corsair DDR5 32GB on sale
  (9, 5, 14, 1, 74.99);  -- 1x Gaming Headset on sale

-- Order 6 (id = 6) - user 6 (apple_fan)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (10, 6, 4, 1, 249.00), -- 1x AirPods Pro on sale
  (11, 6, 16, 1, 79.00); -- 1x Magic Mouse on sale

-- Order 7 (id = 7) - user 7 (retro_fan)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (12, 7, 12, 1, 19.99), -- 1x Switch Case on sale
  (13, 7, 14, 1, 74.99); -- 1x Gaming Headset on sale

-- Order 8 (id = 8) - user 8 (switch_pro)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (14, 8, 7, 1, 59.99),  -- 1x Animal Crossing regular price
  (15, 8, 8, 1, 54.99),  -- 1x Splatoon 3 on sale
  (16, 8, 14, 1, 74.99); -- 1x Gaming Headset on sale

-- Order 9 (id = 9) - user 3 (samus)
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (17, 9, 11, 1, 64.99); -- 1x Metroid Prime 4 on sale

-- Order 10 (id = 10) - user 1 (mqt) - DRAFT
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price)
VALUES
  (18, 10, 10, 1, 69.99), -- 1x Mario Kart World regular price
  (19, 10, 13, 1, 14.99), -- 1x USB-C Cable regular price
  (20, 10, 17, 1, 99.00); -- 1x AirTag 4 Pack regular price