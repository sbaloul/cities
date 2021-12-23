package com.codest.cities.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.codest.cities.data.model.City;

public interface CityRepository extends PagingAndSortingRepository<City, Long> {
	
	Page<City> findByNameStartingWithIgnoreCase(String name, Pageable pageable);
}
