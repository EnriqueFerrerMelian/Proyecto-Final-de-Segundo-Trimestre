CREATE DATABASE IF NOT EXISTS bar;
USE bar;
CREATE TABLE `factura` (
  `id` varchar(80) NOT NULL,
  `mesa` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `producto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  `precio` float DEFAULT NULL,
  PRIMARY KEY (`id`)
);

insert into producto(nombre, precio) values
("Cocamola", 2),
("Fantástica", 1.65),
("Spirite", 1.95),
("BlueBull", 2.4),
("Pepsu", 0.95),
("Dr. Dr", 1.30);

CREATE TABLE `pedidos` (
  `mesa` int DEFAULT NULL,
  `factura` varchar(80) DEFAULT NULL,
  `producto` int DEFAULT NULL,
  `cantidad` int DEFAULT NULL,
  `total` double DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  KEY `factura` (`factura`),
  KEY `producto` (`producto`),
  CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`factura`) REFERENCES `factura` (`id`) ON DELETE CASCADE,
  CONSTRAINT `pedidos_ibfk_2` FOREIGN KEY (`producto`) REFERENCES `producto` (`id`) ON DELETE CASCADE
);
