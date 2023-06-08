package com.jsprestfulapi.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsprestfulapi.data.vo.v1.PersonVO;
import com.jsprestfulapi.exceptions.ResourceNotFoundException;
import com.jsprestfulapi.mapper.DozerMapper;
import com.jsprestfulapi.model.Person;
import com.jsprestfulapi.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	private PersonRepository _repository;
	
	public List<PersonVO> findAll() {
		
		logger.info("Finding all people!");
		return DozerMapper.parseListObjects(_repository.findAll(), PersonVO.class);
		
	}

	public PersonVO findById(Long id) {
		
		logger.info("Finding one PersonVO!");
		
		var entity = _repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO create(PersonVO person) {
		
		logger.info("Creating one PersonVO!");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(_repository.save(entity), PersonVO.class);
		
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		
		logger.info("Updating one PersonVO!");
		
		var entity = _repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(_repository.save(entity), PersonVO.class);
		
		return vo;
		
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one PersonVO!");
		
		var entity = _repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		_repository.delete(entity);
		
	}
	
}
