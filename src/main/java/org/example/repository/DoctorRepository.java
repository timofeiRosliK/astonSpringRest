package org.example.repository;

import org.example.model.Doctor;
import org.springframework.data.repository.CrudRepository;


public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
}
