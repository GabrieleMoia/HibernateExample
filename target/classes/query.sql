drop database logistica;

create DATABASE logistica;

use logistica;

select * from prodotto;

select * from fornitore;

select * from fornitura;

select * from lavoratore;

select * from permesso;

select * from lavoratore_permesso;

delete from corriere where id_corriere=2;

update corriere set id_corriere=5 where id_corriere=3;


select * from corriere;

select * from ordine;

INSERT INTO `logistica`.`ordine`
(`Id_corriere`,
`Id_trasporto`)
VALUES
(3,
19);


CREATE TABLE prodotto(
	id_prodotto  int(11) NOT NULL PRIMARY KEY auto_increment
);


CREATE TABLE corriere(
	id_corriere int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nome varchar(100),
	via varchar(100),
	n_civico varchar(10),
	citta varchar(100),
	cap varchar(6)
);

CREATE TABLE ordine(
	id_ordine int(11) PRIMARY KEY AUTO_INCREMENT,
	Id_corriere int(11) ,
	Id_trasporto int(11),
    CONSTRAINT corriereOrderFK FOREIGN KEY (id_corriere)
    REFERENCES corriere(id_corriere) On update cascade on delete cascade
    );

CREATE TABLE produttore(
	id_produttore int(11) PRIMARY KEY  NOT NULL AUTO_INCREMENT,
	nome varchar(100),
	via varchar(100),
	n_civico varchar(10),
	citta varchar(100),
	cap varchar(6)
);

CREATE TABLE Produttore_prodotto(
	id_produttore int(11) REFERENCES produttore(id_produttore) ON UPDATE CASCADE ON DELETE CASCADE ,
	id_prodotto int(11) REFERENCES prodotto(id_prodotto) ON UPDATE CASCADE ON DELETE CASCADE,
    primary key(id_produttore,id_prodotto)
);

CREATE TABLE fornitore(
	id_fornitore int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	nome varchar(100),
	via varchar(100),
	n_civico varchar(10),
	citta varchar(100),
	cap varchar(6)
);

CREATE TABLE fornitura(
	id_fornitura int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	data DATE,
	quantita int(11),
	id_fornitore int(11)  REFERENCES fornitore(id_fornitore) ON UPDATE CASCADE ON DELETE CASCADE,
	id_prodotto int(11)  REFERENCES prodotto(id_prodotto) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE magazzino(
	id_magazzino int(11) PRIMARY KEY AUTO_INCREMENT,
	nome varchar(100),
	via varchar(100),
	n_civico varchar(10),
	citta varchar(100),
	cap varchar(6),
	metratura float(11),
	altezza float(11),
	capacità float(11)
);

CREATE TABLE disponibilita(
	id_magazzino int(11) REFERENCES magazzino(id_magazzino) ON UPDATE CASCADE ON DELETE CASCADE,
	id_prodotto int(11)  REFERENCES prodotto(id_prodotto) ON UPDATE CASCADE ON DELETE CASCADE,
	quantita int(11),
	sezione_magazzino varchar(1),
    primary key(id_magazzino,id_prodotto)
);

CREATE TABLE lavoratore(
	codice_fiscale varchar(16) PRIMARY KEY,
	nome varchar(100),
	via varchar(100),
	n_civico varchar(10),
	citta varchar(100),
	cap varchar(6),
	id_magazzino int(11)  REFERENCES magazzino(id_magazzino) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE permesso(
	Id_permesso int(11) PRIMARY KEY AUTO_INCREMENT,
	tipo varchar(100)
);

CREATE TABLE lavoratore_permesso(
	codice_fiscale varchar(16)  REFERENCES lavoratore(codice_fiscale) ON UPDATE CASCADE ON DELETE CASCADE,
	id_permesso int(11) REFERENCES permesso(id_permesso) ON UPDATE CASCADE ON DELETE CASCADE,
    primary key(codice_fiscale,id_permesso)
);



-- Corriere

INSERT INTO `logistica`.`corriere`
(`nome`,
`via`,
`n_civico`,
`citta`,
`cap`)
VALUES
('UPS',
'Via Fara',
'28',
'Monza',
'20860');

INSERT INTO `logistica`.`corriere`
(`nome`,
`via`,
`n_civico`,
`citta`,
`cap`)
VALUES
('TNT',
'Via Borromeo',
'29',
'Magenta',
'21098');

INSERT INTO `logistica`.`corriere`
(`nome`,
`via`,
`n_civico`,
`citta`,
`cap`)
VALUES
('BRT',
'Via Dante',
'12',
'Sondrio',
'29012');


-- Fornitore

INSERT INTO `logistica`.`fornitore`
(`nome`,
`via`,
`n_civico`,
`citta`,
`cap`)
VALUES
('Barilla',
'Viale Olona',
'25',
'Rovellasca',
'20987');

INSERT INTO `logistica`.`fornitore`
(`nome`,
`via`,
`n_civico`,
`citta`,
`cap`)
VALUES
('HP',
'Via Leopardi',
'39',
'San Vittore Olona',
'20098');

INSERT INTO `logistica`.`fornitore`
(`nome`,
`via`,
`n_civico`,
`citta`,
`cap`)
VALUES
('Tesla',
'Via Baudo',
'19',
'Milano',
'20180');

-- Magazzino

INSERT INTO `logistica`.`magazzino`
(`nome`,
`via`,
`n_civico`,
`citta`,
`cap`,
`metratura`,
`altezza`,
`capacità`)
VALUES
('MN01',
'Via Verdi',
'1A',
'Milano',
'20098',
100.5,
100.5,
10000);

INSERT INTO `logistica`.`magazzino`
(`nome`,
`via`,
`n_civico`,
`citta`,
`cap`,
`metratura`,
`altezza`,
`capacità`)
VALUES
('MN02',
'Via Rossi',
'2A',
'Parma',
'20198',
50.5,
50.5,
3000);

-- Lavoratore

INSERT INTO `logistica`.`lavoratore`
(`codice_fiscale`,
`nome`,
`via`,
`n_civico`,
`citta`,
`cap`,
`id_magazzino`)
VALUES
('BRGSMN96C167MF0',
'Simone Bergomi',
'Via Natta',
'15',
'QT8',
'20151',
1);

-- Permesso

INSERT INTO `logistica`.`permesso`
(`tipo`)
VALUES
('Write');

INSERT INTO `logistica`.`permesso`
(`tipo`)
VALUES
('Read');

-- Inserts Lavoratore_Permesso

INSERT INTO `logistica`.`lavoratore_permesso`
(`codice_fiscale`,
`id_permesso`)
VALUES
('BRGSMN96C167MF0',
2);

-- Fornitura

INSERT INTO `logistica`.`fornitura`
(`data`,
`quantita`,
`id_fornitore`,
`id_prodotto`)
VALUES
(now(),
10,
1,
1);

-- Produttore

INSERT INTO `logistica`.`produttore`
(`nome`,
`via`,
`n_civico`,
`citta`,
`cap`)
VALUES
('Esselunga',
'Viale Calvino',
'22A',
'Settimo Milanese',
'21210');


-- Produttore - Prodotto

INSERT INTO `logistica`.`produttore_prodotto`
(`id_produttore`,
`id_prodotto`)
VALUES
(1,
1);

