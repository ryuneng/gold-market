INSERT INTO product
    (id, name, type, price, created_at, updated_at, is_deleted)
VALUES
    (1, 'GOLD_99_9', 'PURCHASE', '159229', '2024-09-10 12:00:00', '2024-09-10 12:00:00', false),
    (2, 'GOLD_99_9', 'SALE', '59229', '2024-09-10 12:00:00', '2024-09-10 12:00:00', false),
    (3, 'GOLD_99_99', 'PURCHASE', '359229', '2024-09-10 12:00:00', '2024-09-10 12:00:00', false),
    (4, 'GOLD_99_99', 'SALE', '259229', '2024-09-10 12:00:00', '2024-09-10 12:00:00', false);

-- 주문 ID 시작번호 지정
ALTER TABLE orders AUTO_INCREMENT = 100000;

INSERT INTO orders
    (id, order_number, user_id, product_id, quantity, total_price, status, delivery_address, created_at, updated_at, is_deleted)
VALUES
    (100000, 'PURCHASE-20240909-100000', 1, 1, 3.75, 597108, 'PAID', '서울 서초구 방배로10길 10-20', '2024-09-09 12:00:00', '2024-09-09 12:00:00', false),
    (100001, 'SALE-20240909-100001', 2, 2, 3.75, 222108, 'RECEIVED', '서울 서초구 방배로10길 10-21', '2024-09-09 12:00:00', '2024-09-09 12:00:00', false),
    (100002, 'PURCHASE-20240910-100002', 3, 3, 3.75, 1347108, 'ORDER_PLACED', '경기도 성남시 분당구 금곡로 302', '2024-09-10 12:00:00', '2024-09-10 12:00:00', false),
    (100003, 'SALE-20240910-100003', 4, 4, 3.75, 972108, 'ORDER_PLACED',  '경기도 성남시 분당구 금곡로 303','2024-09-10 12:00:00', '2024-09-10 12:00:00', false);
