package org.example.repository;

import org.example.model.Treatment;
import org.springframework.data.repository.CrudRepository;

public interface TreatmentRepository extends CrudRepository<Treatment, Integer> {
}
