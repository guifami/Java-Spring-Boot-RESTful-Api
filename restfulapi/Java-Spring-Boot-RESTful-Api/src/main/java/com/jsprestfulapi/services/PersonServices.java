package com.jsprestfulapi.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsprestfulapi.data.vo.v1.PersonVO;
import com.jsprestfulapi.exceptions.ResourceNotFoundException;
import com.jsprestfulapi.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	private PersonRepository _repository;
	
	public List<PersonVO> findAll() {
		
		logger.info("Finding all people!");
		return _repository.findAll();
		
	}

	public PersonVO findById(Long id) {
		
		logger.info("Finding one PersonVO!");
		return _repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
		
	}
	
	public PersonVO create(PersonVO person) {
		
		logger.info("Creating one PersonVO!");
		return _repository.save(person);
		
	}
	
	public PersonVO update(PersonVO person) {
		
		logger.info("Updating one PersonVO!");
		
		PersonVO entity = _repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		return _repository.save(person);
		
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one PersonVO!");
		
		PersonVO entity = _repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		_repository.delete(entity);
		
	}
	
}
