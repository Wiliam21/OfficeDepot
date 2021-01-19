create database OfficeDepot;

use OfficeDepot;

CREATE TABLE Categoria (
  IdCategoria INT NOT NULL AUTO_INCREMENT,
  Nombre VARCHAR(255) NOT NULL,
  PRIMARY KEY (IdCategoria)
);

create table Producto(
	IdProducto int NOT NULL auto_increment,
    Nombre varchar(255) not null,
    Precio double not null,
    Stock int not null,
    IdCategoria int not null,
    primary key (IdProducto),
    foreign key (IdCategoria) references Categoria(IdCategoria)
);

create table Cliente (
	IdCliente int not null auto_increment,
    Nombre varchar(50) not null,
    Apellido varchar (50) not null,
    Direccion varchar (255),
    FechaRegistro datetime default current_timestamp,
    primary key (IdCliente)
);

create table Oferta(
	IdOferta int not null auto_increment,
    FechaInicio date,
    FechaFin date,
    Porcentaje double,
    IdCategoria int,
    primary key (IdOferta),
    foreign key (IdCategoria) references Categoria(IdCategoria)
);

create table Venta(
	IdVenta int not null auto_increment,
    IdCliente int not null,
    Fecha datetime default current_timestamp,
    Descuento double,
    Total double,
    Envio bool,
    primary key (IdVenta),
    foreign key (IdCliente) references Cliente(IdCliente)
);

create table DetalleVenta(
	IdDetalleVenta int not null auto_increment,
    IdVenta int,
    NombreProducto varchar(30),
    Precio double,
    Cantidad int,
    Descuento double,
    Total double,
    primary key (IdDetalleVenta),
    foreign key (IdVenta) references Venta(IdVenta)
);