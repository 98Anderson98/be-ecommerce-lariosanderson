CREATE DATABASE IF NOT EXISTS db_ecommerce_lariosanderson
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE db_ecommerce_lariosanderson;

DROP TABLE IF EXISTS detalle_pedido;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS usuarios_roles;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS roles;

CREATE TABLE productos (
  id             BIGINT         NOT NULL AUTO_INCREMENT,
  nombre         VARCHAR(100)   NOT NULL,
  categoria      VARCHAR(50)    NOT NULL,
  precio         DECIMAL(10,2)  NOT NULL,
  stock          INT            NOT NULL DEFAULT 0,
  marca          VARCHAR(50)    NOT NULL,
  codigo_barras  VARCHAR(20)    NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uk_productos_codigo_barras UNIQUE (codigo_barras),
  CONSTRAINT ck_productos_precio CHECK (precio > 0),
  CONSTRAINT ck_productos_stock  CHECK (stock >= 0)
) ENGINE=InnoDB;

CREATE TABLE pedidos (
  id             BIGINT         NOT NULL AUTO_INCREMENT,
  cliente        VARCHAR(100)   NOT NULL,
  fecha_compra   DATETIME(6)    NOT NULL,
  monto_total    DECIMAL(12,2)  NOT NULL,
  estado_pedido  VARCHAR(20)    NOT NULL DEFAULT 'REGISTRADO',
  metodo_pago    VARCHAR(20)    NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE detalle_pedido (
  id               BIGINT         NOT NULL AUTO_INCREMENT,
  pedido_id        BIGINT         NOT NULL,
  producto_id      BIGINT         NOT NULL,
  cantidad         INT            NOT NULL,
  precio_unitario  DECIMAL(10,2)  NOT NULL,
  subtotal         DECIMAL(12,2)  NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_detalle_pedido   FOREIGN KEY (pedido_id)   REFERENCES pedidos(id)   ON DELETE CASCADE,
  CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_id) REFERENCES productos(id)
) ENGINE=InnoDB;

CREATE TABLE roles (
  id      BIGINT       NOT NULL AUTO_INCREMENT,
  nombre  VARCHAR(30)  NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uk_roles_nombre UNIQUE (nombre)
) ENGINE=InnoDB;

CREATE TABLE usuarios (
  id        BIGINT        NOT NULL AUTO_INCREMENT,
  username  VARCHAR(50)   NOT NULL,
  password  VARCHAR(100)  NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uk_usuarios_username UNIQUE (username)
) ENGINE=InnoDB;

CREATE TABLE usuarios_roles (
  usuario_id  BIGINT NOT NULL,
  rol_id      BIGINT NOT NULL,
  PRIMARY KEY (usuario_id, rol_id),
  CONSTRAINT fk_ur_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
  CONSTRAINT fk_ur_rol     FOREIGN KEY (rol_id)     REFERENCES roles(id)
) ENGINE=InnoDB;

INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO usuarios (username, password) VALUES
 ('admin',    '$2a$10$pPyYxgaCnkDcBjup7SmgQu8Qafa5VERYtKLGoHcdDemCWusDkfhsa'),
 ('anderson', '$2a$10$h3raKyMawrh9UW7ZcEpxUeuQCg6o6zrVY/9UqBNKPkl1IyppHHRU6');

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (1, 1), (1, 2), (2, 2);

INSERT INTO productos (nombre, categoria, precio, stock, marca, codigo_barras) VALUES
 ('Laptop IdeaPad 3',        'Computo',    2499.90, 15, 'Lenovo',  '7750000000011'),
 ('Mouse Inalambrico M185',  'Accesorios',   59.90, 80, 'Logitech','7750000000028'),
 ('Monitor 24 pulgadas',     'Computo',     649.00, 20, 'LG',      '7750000000035'),
 ('Audifonos WH-CH520',      'Audio',       199.00, 40, 'Sony',    '7750000000042'),
 ('Smartphone Galaxy A55',   'Celulares',  1599.00, 25, 'Samsung', '7750000000059');
