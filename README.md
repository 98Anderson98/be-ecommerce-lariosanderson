# be-ecommerce-lariosanderson

Backend API REST (Spring Boot 4 + MySQL + JWT) — Larios Tito Anderson Fabrizzio

## 1.2 Base de datos en Docker

```bash
docker compose up -d
```

Crea el contenedor `mysql_ecommerce_lariosanderson` (root / root123) y ejecuta `db_ecommerce_lariosanderson.sql`.

Sin docker compose:

```bash
docker run -d --name mysql_ecommerce_lariosanderson -e MYSQL_ROOT_PASSWORD=root123 -p 3306:3306 mysql:8.0
docker exec -i mysql_ecommerce_lariosanderson mysql -uroot -proot123 < db_ecommerce_lariosanderson.sql
```

## Usuarios

| Usuario  | Contraseña | Rol                   |
|----------|------------|-----------------------|
| admin    | admin123   | ROLE_ADMIN, ROLE_USER |
| anderson | user123    | ROLE_USER             |

## Endpoints

| Método | Ruta | Rol |
|---|---|---|
| POST | /api/auth/login | público |
| GET | /api/productos, /api/productos/{id} | USER/ADMIN |
| GET | /api/productos/categoria/{c}, /marca/{m}, /codigo/{cb} (NamedQuery) | USER/ADMIN |
| GET | /api/productos/precio?min=&max=, /buscar?nombre= (JPQL) | USER/ADMIN |
| GET | /api/productos/buscar-avanzado?keyword=&stockMin= (EntityManager) | USER/ADMIN |
| POST/PUT/DELETE | /api/productos, /api/productos/{id} | ADMIN |
| POST | /api/pedidos | USER/ADMIN |
| GET | /api/pedidos, /{id}, /cliente/{c}, /estado/{e} | USER/ADMIN |
| PATCH | /api/pedidos/{id}/estado | ADMIN |

## 1.9 Consultar pedido y detalle dentro del contenedor

```bash
docker exec -it mysql_ecommerce_lariosanderson bash
mysql -uroot -proot123 db_ecommerce_lariosanderson
```

```sql
SELECT * FROM pedidos;
SELECT * FROM detalle_pedido;
SELECT p.id, p.cliente, p.fecha_compra, p.estado_pedido, p.metodo_pago, pr.nombre, d.cantidad, d.precio_unitario, d.subtotal, p.monto_total
FROM pedidos p JOIN detalle_pedido d ON d.pedido_id = p.id JOIN productos pr ON pr.id = d.producto_id;
```
