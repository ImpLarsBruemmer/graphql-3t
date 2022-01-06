CREATE TABLE FLIGHT (
    id int not null auto_increment,
    start varchar(256) not null,
    destination varchar(256) not null,
    arrival timestamp,
    departure timestamp,
    status enum('LANDED', 'READY_FOR_TAKE_OFF', 'BOARDING', 'CANCELLED', 'DELAYED', 'IN_TIME', 'ARRIVING')
);

INSERT INTO FLIGHT (id, start, destination, arrival, departure, status) VALUES
    (1, 'Tegel', 'Tempelhof', '2022-01-01 10:00:00', '2022-01-01 12:00:00', 'IN_TIME'),
    (2, 'Tegel', 'Teneriffa', '2022-01-01 10:15:00', '2022-01-01 17:00:00', 'READY_FOR_TAKE_OFF');
