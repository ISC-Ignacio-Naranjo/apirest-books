package com.javers.books.backend.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javers.books.backend.model.Categoria;
import com.javers.books.backend.model.dao.ICategoriaDao;
import com.javers.books.backend.response.CategoriaResponseRest;

@Service
public class CategoriaServiceImpl implements ICategoriaService{
	

	private static final Logger log =  LoggerFactory.getLogger(CategoriaServiceImpl.class);
	
	@Autowired
	private ICategoriaDao categoriaDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest>  buscarCategorias() {
		log.info("inicio metodo buscarCategorias()");
		CategoriaResponseRest response = new CategoriaResponseRest();
		try {
			List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();
			response.getCategoriaResponse().setCategoria(categoria);
			response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
		} catch (Exception e) {
			response.setMetadata("Respuesta not Ok", "-1", "Error al Consultar Categorias  ");
			log.error("Error al consultar categorias: ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id) {
		log.info("Inicio metodo buscarPorId");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> categoriaList = new ArrayList<>();
		try {
			Optional<Categoria> categoria = categoriaDao.findById(id);
			if (categoria.isPresent()) {
				categoriaList.add(categoria.get());
				response.getCategoriaResponse().setCategoria(categoriaList);
			}else {
				log.error("Error en consultar categoria");
				response.setMetadata("Respuesta Not Ok", "-1", "Categoria no encontrada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {

			log.error("Error en consultar categoria");
			response.setMetadata("Respuesta Not Ok", "-1", "Error Al consultar categoria");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			 		}
		response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);

	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> crear(Categoria categoria) {
		log.info("Inicio metodo crear");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> categoriaList = new ArrayList<>();
		try {
			Categoria categoriaGuardada = categoriaDao.save(categoria); 
			if (categoriaGuardada != null) {
				categoriaList.add(categoriaGuardada);
				response.getCategoriaResponse().setCategoria(categoriaList);
			}else {
				log.error("Error al Crear categoria");
				response.setMetadata("Respuesta Not Ok", "-1", "Categoria no Creada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
			
			}
			
		} catch (Exception e) {
			log.error("Error en crear categoria");
			response.setMetadata("Respuesta Not Ok", "-1", "Error Al crear categoria");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
 		}
		response.setMetadata("Respuesta ok", "200", "Categoria Creada");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id) {
		log.info("Inicio metodo Actualizar");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> categoriasList = new ArrayList<>();
		
		try {
			Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
			if (categoriaBuscada.isPresent()) {
				categoriaBuscada.get().setNombre(categoria.getNombre());
				categoriaBuscada.get().setDescripcion(categoria.getDescripcion());
				
				Categoria categoriaActualizar = categoriaDao.save(categoriaBuscada.get());//actualizando
				if (categoriaActualizar != null) {
					response.setMetadata("Respuesta ok", "200", "Categoria Actualizada");
					categoriasList.add(categoriaActualizar);
					response.getCategoriaResponse().setCategoria(categoriasList);
				}else {
					log.error("Error en actualizar categoria");
					response.setMetadata("Respuesta not Ok", "-1", "Categoria no Actualizada");
					return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
				}			
			}else {

				log.error("Error en actualizar categoria");
				response.setMetadata("Respuesta not Ok", "-1", "Categoria no Actualizada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			log.error("Error en actualizar categoria", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta not Ok", "-1", "Categoria no Actualizada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
 		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	
	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> eliminar(Long id) {
		log.info("Inicio metodo Eliminar categoiras");
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
			if (categoriaBuscada.isPresent()) {
			//Eliminamos el registro
			categoriaDao.deleteById(id);
			response.setMetadata("Respuesta ok", "200", "Categoria Eliminada");
			}else {

				log.error("Error en Eliminar categoria");
				response.setMetadata("Respuesta not Ok", "-1", "Categoria no encontrada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			
			}
			

		} catch (Exception e) {
			log.error("Error en actualizar categoria", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta not Ok", "-1", "Categoria no Eliminada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
 		} 
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

}
