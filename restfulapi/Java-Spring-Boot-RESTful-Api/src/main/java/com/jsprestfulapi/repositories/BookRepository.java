package com.jsprestfulapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsprestfulapi.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{}
