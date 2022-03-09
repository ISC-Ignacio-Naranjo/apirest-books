package com.javers.books.backend.services;

import org.springframework.http.ResponseEntity;

import com.javers.books.backend.model.Libro;
import com.javers.books.backend.response.LibroResponseRest;

public interface ILibroService{
	
	public ResponseEntity<LibroResponseRest> buscarLibros();

	public ResponseEntity<LibroResponseRest> buscarPorId(Long id);

	ResponseEntity<LibroResponseRest> crear(Libro libro);

	public ResponseEntity<LibroResponseRest> actualizar(Libro libro, Long id);

	public ResponseEntity<LibroResponseRest> eliminar(Long id);

}
