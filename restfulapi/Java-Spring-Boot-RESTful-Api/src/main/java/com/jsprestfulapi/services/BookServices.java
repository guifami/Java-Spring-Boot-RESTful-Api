package com.jsprestfulapi.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsprestfulapi.controllers.BookController;
import com.jsprestfulapi.data.vo.v1.BookVO;
import com.jsprestfulapi.exceptions.RequiredObjectIsNullException;
import com.jsprestfulapi.exceptions.ResourceNotFoundException;
import com.jsprestfulapi.mapper.DozerMapper;
import com.jsprestfulapi.model.Book;
import com.jsprestfulapi.repositories.BookRepository;

@Service
public class BookServices {

	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	private BookRepository _repository;
	
	public List<BookVO> findAll() {
		
		logger.info("Finding all books!");
		
		var books = DozerMapper.parseListObjects(_repository.findAll(), BookVO.class);
		books.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		
		return books;
		
	}

	public BookVO findById(Long id) {
		
		logger.info("Finding one BookVO!");
		
		var entity = _repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
		var vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public BookVO create(BookVO book) {
		
		if(book == null) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one BookVO!");
		
		var entity = DozerMapper.parseObject(book, Book.class);
		var vo = DozerMapper.parseObject(_repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public BookVO update(BookVO book) {
		
		if(book == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating one BookVO!");
		
		var entity = _repository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var vo = DozerMapper.parseObject(_repository.save(entity), BookVO.class);
		
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
		
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one BookVO!");
		
		var entity = _repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		_repository.delete(entity);
		
	}
	
}
