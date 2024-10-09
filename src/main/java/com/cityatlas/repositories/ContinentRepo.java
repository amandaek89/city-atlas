package com.cityatlas.repositories;

import com.cityatlas.models.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContinentRepo extends JpaRepository<Continent, Long> {
}
