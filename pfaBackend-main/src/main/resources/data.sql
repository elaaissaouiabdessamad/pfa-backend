INSERT INTO `roles` (`id`, `name`) VALUES
                                       (1, 'ROLE_USER'),
                                       (2, 'ROLE_MODERATOR'),
                                       (3, 'ROLE_ADMIN');

INSERT INTO `users` (`client_id`, `id`, `owner_id`, `waiter_id`, `username`, `email`, `password`) VALUES
                                                                                                      (NULL, 1, 1, NULL, 'akram', 'akram15191519@gmail.com', '$2a$10$PPegU3YRIfWzdAEcMJmoPetwkZjn9BXT6Z9s.D0i/mOsTvWSO0vkO'),
                                                                                                      (NULL, 2, 2, NULL, 'owner13', 'owner13@email.com', '$2a$10$96JHRm8lJxF39lGtf1K.Ke4a5xYs/Emi2lmwceKsDXqywcBjpJ1kq'),
                                                                                                      (1, 3, NULL, NULL, 'client1', 'client1@gmail.com', '$2a$10$3OAP8e5sad9VOSwKE/pIJOe5./HOuOldGzMkH0rO38gWyt0112Y8y'),
                                                                                                      (NULL, 4, NULL, 1, 'waiter', 'waiter1@gmail.com', '$2a$10$0FaTfVwllikHtVW.aXRuReKgvp8xqa9dslBSbxTTrh3liALyDDrN2'),
                                                                                                      (NULL, 5, NULL, 2, 'waiter2', 'waiter2@gmail.com', '$2a$10$ZljfbuIHPYqAkgEHPjA16OkaS2lLwARd5ofn2DQYNOjjo4.Jj/rQe'),
                                                                                                      (NULL, 6, 3, NULL, 'owner20', 'ahmed20@gmail.com', '$2a$10$Lrwwg1xUUtf9OceDgRVRieFaGsqTYUAN97NsWrk6XVOKXDGlS6WH.'),
                                                                                                      (NULL, 7, NULL, 3, 'waiter3', 'waiter3@gmail.com', '$2a$10$ZljfbuIHPYqAkgEHPjA16OkaS2lLwARd5ofn2DQYNOjjo4.Jj/rQe'),
                                                                                                      (NULL, 8, NULL, 4, 'waiter4', 'waiter4@gmail.com', '$2a$10$ZljfbuIHPYqAkgEHPjA16OkaS2lLwARd5ofn2DQYNOjjo4.Jj/rQe'),
                                                                                                      (NULL, 9, NULL, 5, 'waiter5', 'waiter5@gmail.com', '$2a$10$ZljfbuIHPYqAkgEHPjA16OkaS2lLwARd5ofn2DQYNOjjo4.Jj/rQe');

INSERT INTO `user_roles` (`role_id`, `user_id`) VALUES
                                                    (3, 1),
                                                    (3, 2),
                                                    (1, 3),
                                                    (2, 4),
                                                    (2, 5),
                                                    (3, 6),
                                                    (2, 7),
                                                    (2, 8),
                                                    (2, 9);




INSERT INTO `restaurants` (`latitude`, `likes`, `longitude`, `rating`, `id`, `cover_image_url`, `cuisine`, `description`, `email`, `instagram`, `logo_url`, `name`, `phone`, `price_range`) VALUES
                                                                                                                                                                                                (33.55992247898807, 0, -7.633936790771482, 0, 1, 'f764c957-1454-4bc3-9d64-bf43c39ab683.jpg', 'sqf', 'wxv', 'akram15191519@gmail.com', 'cwvw', '706b6b50-fdcd-420d-8692-1d50e9151fc5.webp', 'ACHIBANE ACHIBANE', '0706622008', '$$'),
                                                                                                                                                                                                (33.55827616478833, 0, -7.616872787475587, 0, 2, '5bbb79fb-12e5-4a20-b2af-1de44e572c39.webp', 'French food', 'French Restaurant !', 'frenchisso@gmail.com', 'FrenchissoRest', '6c4ffa7c-7a2c-4f22-96ac-7fa45308d16c.jpg', 'Frenchisso', '0615482316', '$$'),
                                                                                                                                                                                                (30.397919626786805, 0, -9.515587091445925, 0, 3, 'c2a71548-f894-4dba-9e32-e61117650426.jpg', 'Japanese', 'A cozy restaurant serving delicious food from around the world.', 'sushihaven@gmail.com', 'sushipalace', 'ed58c9a6-d54e-4ac1-8daf-f5ccd2db6c98.png', 'Sushi Haven', '632145686', '$$$$');


INSERT INTO `owners` (`id`, `restaurant_id`, `email`, `name`, `phone`) VALUES
                                                                           (1, 1, 'akram15191519@gmail.com', 'ACHIBANE ACHIBANE', '0706622008'),
                                                                           (2, 2, 'owner13@email.com', 'Ragragui', '0623551611'),
                                                                           (3, 3, 'ahmed20@gmail.com', 'Ahmed', '0612345678');

INSERT INTO `clients` (`id`, `email`, `name`, `phone`) VALUES
    (1, 'client1@gmail.com', 'clientName', '112233445566');

INSERT INTO `waiters` (`id`, `restaurant_id`, `cin`, `email`, `name`, `phone`) VALUES
                                                                                   (1, 1, NULL, 'waiter1@gmail.com', 'Hamza', '112233445566'),
                                                                                   (2, 1, NULL, 'waiter2@gmail.com', 'Ali', '115533445566'),
                                                                                   (3, 2, NULL, 'waiter3@gmail.com', 'Omar', '116633556633'),
                                                                                   (4, 2, NULL, 'waiter4@gmail.com', 'Houssam', '110033445566'),
                                                                                   (5, 2, NULL, 'waiter5@gmail.com', 'Moussa', '112255556688');

INSERT INTO `food_categories` (`is_activated`, `id`, `restaurant_id`, `name`) VALUES
                                                                                  (b'1', 1, 2, 'Coffee'),
                                                                                  (b'1', 2, 3, 'Starters');



INSERT INTO `products` (`bonus_points`, `is_activated`, `price`, `category_id`, `id`, `img`, `info`, `name`) VALUES
                                                                                                                 (15, b'1', 22, 1, 1, '01c75aaa-14f3-4bd6-9161-fbec206364d4.webp', 'Ristretto is a nice coffee to start the day !', 'Ristretto'),
                                                                                                                 (12, b'1', 32, 1, 2, 'beb0fa41-e298-473f-ac87-182b5c2f3ed3.webp', 'Nice coffee, you need to try it some day!', 'Latte'),
                                                                                                                 (35, b'1', 14, 1, 3, '685ac63e-9fe7-4b51-9a1e-4bc58412687c.webp', 'Nice Americano !', 'Americano'),
                                                                                                                 (12, b'1', 22, 1, 4, 'dae98dc7-088a-4699-b587-8d6589fd0c3d.webp', 'Nice Espresso!', 'Espresso'),
                                                                                                                 (14, b'1', 11, 1, 5, 'f1e77e44-5ca2-425f-86d1-57a27c8f86c0.webp', 'Nice Flat White !', 'Flat white'),
                                                                                                                 (28, b'1', 16, 1, 6, 'f8c7b813-566b-4c46-b8cd-f8bfd880dbcf.webp', 'Nice Cortado !', 'Cortado'),
                                                                                                                 (15, b'1', 5.99, 2, 7, 'c3050ed0-f617-4504-9108-c902ed6062a2.png', 'Toasted bread with garlic', 'Garlic bread'),
                                                                                                                 (15, b'1', 8.99, 2, 8, '700c2c5e-c7a9-4c74-b74c-8724580f4072.jpeg', 'Fresh salad with caesar dressing', 'Caesar salad'),
                                                                                                                 (25, b'1', 6.99, 2, 9, '6d85307c-14e9-4a71-8f22-ad0d6c3cedf9.jpeg', 'Toasted bread topped with tomatoes, basil and olive oil', 'Bruschetta'),
                                                                                                                 (21, b'1', 7.99, 2, 10, '4b3f5447-a279-4732-b0e3-5e5da45ed7bb.jpeg', 'Fried mozzarella cheese sticks with marinara sauce', 'Mozzarella sticks');

INSERT INTO `rewards` (`points_price`, `collection_id`, `id`, `product_id`) VALUES
                                                                                (20, NULL, 1, 2),
                                                                                (20, NULL, 2, 3),
                                                                                (20, NULL, 3, 4),
                                                                                (25, NULL, 4, 6),
                                                                                (22, NULL, 5, 1),
                                                                                (50, NULL, 6, 7),
                                                                                (70, NULL, 7, 8),
                                                                                (60, NULL, 8, 9);

INSERT INTO `feedback` (`rating`, `client_id`, `id`, `restaurant_id`, `description`) VALUES
                                                                                         (4, 1, 1, 1, 'Nice restaurant!'),
                                                                                         (4, 1, 2, 2, 'Nice restaurant!'),
                                                                                         (4, 1, 3, 2, 'Goood!'),
                                                                                         (4, 1, 4, 2, 'Perfect Service!'),
                                                                                         (4, 1, 5, 1, 'Awesome Service!');

INSERT INTO `discounts` (`percentage`, `collection_id`, `id`, `product_id`) VALUES
                                                                                (15, NULL, 1, 2),
                                                                                (18, NULL, 2, 1),
                                                                                (10, NULL, 3, 7),
                                                                                (15, NULL, 4, 8),
                                                                                (20, NULL, 5, 9);

INSERT INTO `orders` (`total_price`, `client_id`, `id`, `waiter_id`) VALUES
                                                                         (100, 1, 1, 3),
                                                                         (100, 1, 2, 3),
                                                                         (100, 1, 3, 3),
                                                                         (100, 1, 4, 4),
                                                                         (50, 1, 5, 4);

INSERT INTO `order_discounts` (`discount_id`, `order_id`) VALUES
                                                              (1, 1),
                                                              (1, 2),
                                                              (1, 3),
                                                              (1, 4),
                                                              (1, 5);

INSERT INTO `order_products` (`order_id`, `product_id`) VALUES
                                                            (1, 1),
                                                            (2, 1),
                                                            (3, 1),
                                                            (4, 1),
                                                            (5, 1);


INSERT INTO `order_rewards` (`order_id`, `reward_id`) VALUES
                                                          (1, 1),
                                                          (2, 1),
                                                          (3, 1),
                                                          (4, 1),
                                                          (5, 1);


