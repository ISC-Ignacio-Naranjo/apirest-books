package com.javers.books.backend.response;

import java.util.List;

import com.javers.books.backend.model.Categoria;

public class CategoriaResponse {
	
	private List<Categoria> categoria;

	public List<Categoria> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}
	
	

}
