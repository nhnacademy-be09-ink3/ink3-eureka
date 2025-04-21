insert into users (user_id, user_name, user_password, user_birth, user_auth, user_point, create_at, last_login_at)
values ('1', 'nhn1', '1', '20250421', 'ROLE_USER', 10, null, null);


insert into product_status (status_type)
values ('판매중');

insert into product (product_id, product_name, product_price, product_details, product_amount, product_preview_image, product_create_at, product_status_id)
values (1, 'product', 100, 'good', 10, null, CURRENT_TIMESTAMP, 1);