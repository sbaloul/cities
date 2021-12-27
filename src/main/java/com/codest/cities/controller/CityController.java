package com.codest.cities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codest.cities.data.model.City;
import com.codest.cities.data.repository.CityRepository;

@RestController
@RequestMapping("/api")
public class CityController {

	@Autowired
	private CityRepository repository;

	@GetMapping("/cities")
	public ResponseEntity<Page<City>> getAllCities(@RequestParam(required = false) String name,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		// Since size of page is dynamic we need to prevent to have a very large page size
		if (size > 20) {
			size = 20;
		}
		
		PageRequest pageable = PageRequest.of(page, size);
		
		Page<City> result;
		
		if(name == null || name.isEmpty()) {
			result = repository.findAll(pageable);
		} else {
			result = repository.findByNameStartingWithIgnoreCase(name, pageable);
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	

	@PreAuthorize("hasRole('ROLE_ALLOW_EDIT')")
	@PutMapping("/cities/{id}")
	public ResponseEntity<City> updateCity(@PathVariable long id, @RequestParam String name,
			@RequestParam String photo) {
		
		City city = repository.findById(id).orElse(null);

		if (city == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		city.setName(name);
		city.setPhoto(photo);

		city = repository.save(city);
		
		return new ResponseEntity<>(city, HttpStatus.OK);
	}
}
