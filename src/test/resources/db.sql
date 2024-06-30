CREATE TABLE doctors
(
    doctor_id      INT PRIMARY KEY AUTO_INCREMENT ,
    first_name     VARCHAR(50),
    last_name      VARCHAR(50),
    specialization VARCHAR(50)
);

CREATE TABLE diagnoses
(
    diagnosis_id   INT PRIMARY KEY AUTO_INCREMENT ,
    diagnosis_name varchar(255) not null
);

CREATE TABLE patients
(
    patient_id   INT PRIMARY KEY AUTO_INCREMENT ,
    first_name   varchar(50) not null,
    last_name    varchar(50) not null,
    gender       varchar(5)  not null,
    doctor_id    int,
    diagnosis_id int,
    constraint patients_ibfk_1
        foreign key (doctor_id) references doctors (doctor_id),
    constraint patients_ibfk_2
        foreign key (diagnosis_id) references diagnoses (diagnosis_id)
);

create table treatments
(
    treatment_id  int
        primary key AUTO_INCREMENT,
    treatment_name varchar(50) not null
);

create table diagnoses_treatments
(
    diagnosis_id int not null,
    treatment_id int not null,
    primary key (diagnosis_id, treatment_id),
    constraint diagnoses_treatments_ibfk_1
        foreign key (diagnosis_id) references diagnoses (diagnosis_id),
    constraint diagnoses_treatments_ibfk_2
        foreign key (treatment_id) references treatments (treatment_id)
);





