package com.javers.books.backend.model.dao;

import org.springframework.data.repository.CrudRepository;
import com.javers.books.backend.model.Libro;

public interface ILibroDao extends CrudRepository<Libro, Long> {

}
