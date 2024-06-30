INSERT INTO doctors (doctor_id, first_name, last_name, specialization)
VALUES (1, 'John', 'Doe', 'oncologist'),
       (2, 'Jane', 'Smith', 'GP'),
       (3, 'Valentina', 'Ivanova', 'surgeon');


INSERT INTO diagnoses (diagnosis_id, diagnosis_name)
VALUES (1, 'Cancer'),
       (2, 'Flu'),
       (3, 'Diabetes');

INSERT INTO treatments (treatment_id, treatment_name)
VALUES (1, 'Chemotherapy'),
       (2, 'Antiviral'),
       (3, 'Insulin');

INSERT INTO patients (patient_id,first_name, last_name, gender, doctor_id, diagnosis_id)
VALUES (1, 'Alice', 'Johnson', 'WOMAN', 1, 1),
       (2,'Bob', 'Brown', 'MAN', 2, 2),
       (3, 'Charlie', 'Davis', 'MAN', 2, 3);

INSERT INTO diagnoses_treatments (diagnosis_id, treatment_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);
;