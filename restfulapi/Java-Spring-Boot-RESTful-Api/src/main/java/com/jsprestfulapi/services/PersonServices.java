package com.jsprestfulapi.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.jsprestfulapi.controllers.PersonController;
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
		
		var persons = DozerMapper.parseListObjects(_repository.findAll(), PersonVO.class);
		persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		
		return persons;
		
	}

	public PersonVO findById(Long id) {
		
		logger.info("Finding one PersonVO!");
		
		var entity = _repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public PersonVO create(PersonVO person) {
		
		logger.info("Creating one PersonVO!");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(_repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		
		logger.info("Updating one PersonVO!");
		
		var entity = _repository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(_repository.save(entity), PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
		
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one PersonVO!");
		
		var entity = _repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		_repository.delete(entity);
		
	}
	
}
