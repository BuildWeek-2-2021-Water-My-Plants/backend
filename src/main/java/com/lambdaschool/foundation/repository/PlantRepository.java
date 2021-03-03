package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Plant;
import org.springframework.data.repository.CrudRepository;

public interface PlantRepository extends CrudRepository<Plant, Long> {
}
