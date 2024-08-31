DROP DATABASE EcoMove
create database EcoMove

\c EcoMove

use EvoMove


DROP TABLE Partenaire
create table Partenaire ( 

    id int PRIMARY key auto_increment,
    nom_compagnie VARCHAR(255) UNIQUE,
    contact_commercial VARCHAR(255),
    type_trasport enum ('Avion' , 'train' , 'Bus'),
    zone_geographique VARCHAR(255),
    conditions_speciales TEXT,
    status_partenaire enum('Actif' , 'Inactif' , 'Suspendu');
    date_creation DATE
)


DROP TABLE Contrats
CREATE TABLE Contrats  ( 
    id int Primary key auto_increment,
    Partenaire_id int,
    foreign key (Partenaire_id) references Partenaire (id),
    date_debut DATE,
    date_fin DATE,
    status_contrats enum ('en cours' , 'termine' , 'suspendu'),
    tarif_special varchar(50),
    conditions_accord TEXT,
    renouvelable boolean
)

DROP table Billets
CREATE TABLE Billet ( 
    id int Primary Key auto_increment,
    prix_achat varchar(255),
    prix_vente varcahr(255),
    date_vente DATETIME
)

DROP TABLE Offres (
    id int primary key auto_increment,
    nom_offre VARCHAR(255) ,
    description TEXT,
    date_debut DATE,
    date_fin DATE,
    valeur_reduction VARCHAR(20),
    condition TEXT
)

