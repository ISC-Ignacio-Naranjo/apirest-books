package com.javers.books.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.javers.books.backend.model.Categoria;
import com.javers.books.backend.model.dao.ICategoriaDao;
import com.javers.books.backend.response.CategoriaResponseRest;
import com.javers.books.backend.services.CategoriaServiceImpl;

public class CategoriaServiceImplTest {
	
	@InjectMocks
	CategoriaServiceImpl service;
	
	@Mock
	ICategoriaDao categoriaDao;
	
	List<Categoria> listaCategorias = new ArrayList<Categoria>();
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		this.cargarCategorias();
	}
	
	@Test
	public void buscarCategoriaTest() {
		when(categoriaDao.findAll()).thenReturn(listaCategorias);
		ResponseEntity<CategoriaResponseRest> response = service.buscarCategorias();
		assertEquals(4, response.getBody().getCategoriaResponse().getCategoria().size());
		verify(categoriaDao, times(1)).findAll();
	}
	
	public void cargarCategorias() {
		Categoria catUno = new Categoria(Long.valueOf(1), "Abarrotes", "Distintos abarrotes");
		Categoria catDos = new Categoria(Long.valueOf(1), "Lacteos", "Variedad de Lacteos");
		Categoria catTres = new Categoria(Long.valueOf(1), "Bebidas", "Bebidas sin azucar");
		Categoria catCuatro = new Categoria(Long.valueOf(1), "Carnes Blacas", "Distintas Carnes");
		
		listaCategorias.add(catUno);
		listaCategorias.add(catDos);
		listaCategorias.add(catTres);
		listaCategorias.add(catCuatro);


	}
	
	
	
	
	
	

}
