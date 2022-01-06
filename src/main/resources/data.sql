CREATE TABLE FLIGHT (
    id int not null auto_increment primary key,
    start varchar(256) not null,
    destination varchar(256) not null,
    arrival timestamp,
    departure timestamp,
    status enum('LANDED', 'READY_FOR_TAKE_OFF', 'BOARDING', 'CANCELLED', 'DELAYED', 'IN_TIME', 'ARRIVING')
);

INSERT INTO FLIGHT (id, start, destination, arrival, departure, status) VALUES
    (1, 'Tegel', 'Tempelhof', '2022-01-01 10:00:00', '2022-01-01 12:00:00', 'IN_TIME'),
    (2, 'Tegel', 'Teneriffa', '2022-01-01 10:15:00', '2022-01-01 17:00:00', 'READY_FOR_TAKE_OFF');

CREATE TABLE PASSENGER (
    id int not null auto_increment primary key,
    name varchar(256) not null
);

CREATE TABLE XREF_FLIGHT_PASSENGER (
    flight_id int not null,
    passenger_id int not null
);

ALTER TABLE XREF_FLIGHT_PASSENGER ADD FOREIGN KEY (flight_id) references FLIGHT(id);
ALTER TABLE XREF_FLIGHT_PASSENGER ADD FOREIGN KEY (passenger_id) references PASSENGER(id);

INSERT INTO PASSENGER (name) VALUES
    ( 'Hans Peter' ),
    ( 'Hans Peter2' ),
    ( 'Hans Peter3' ),
    ( 'Hans Peter4' );

INSERT INTO XREF_FLIGHT_PASSENGER VALUES (1, 1), (1, 2), (2, 3), (2, 4);
