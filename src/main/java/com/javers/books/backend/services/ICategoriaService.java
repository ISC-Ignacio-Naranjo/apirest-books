package com.javers.books.backend.services;

import org.springframework.http.ResponseEntity;

import com.javers.books.backend.model.Categoria;
import com.javers.books.backend.response.CategoriaResponseRest;

public interface ICategoriaService {
	
	public ResponseEntity<CategoriaResponseRest>  buscarCategorias();

	public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id);

	public ResponseEntity<CategoriaResponseRest> crear(Categoria request);
	
	public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id);
	
	public ResponseEntity<CategoriaResponseRest> eliminar(Long id);

}
