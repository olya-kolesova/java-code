Практическая задча - анализ и исправление query запроса - 1
Представьте, что у вас есть веб-приложение для онлайн-магазина, где пользователи могут просматривать товары, добавлять их в корзину и оформлять заказы. Ваша база данных содержит таблицы products (товары), orders (заказы), users (пользователи) и cart_items (товары в корзине).

Задача: Улучшите производительность запроса, который выявляет суммарную стоимость всех товаров, находящихся в корзине для конкретного пользователя.

Исходный медленный запрос:

SELECT SUM(products.price * cart_items.quantity) AS total_cost
FROM products
JOIN cart_items ON products.id = cart_items.product_id
JOIN orders ON orders.user_id = cart_items.user_id
WHERE orders.status = 'active'
AND orders.user_id = <user_id>;
Определите проблемы, справьте их и предоставьте пруфы ускорения работы запроса.

Дамп файл базы данных PG 16.4

https://drive.google.com/file/d/11_IusmSDJOExnvkTJ8VaRXhbPNKeSMi8/view?usp=sharing

Дополнительно потренируетесь в работе с дамп файлом.