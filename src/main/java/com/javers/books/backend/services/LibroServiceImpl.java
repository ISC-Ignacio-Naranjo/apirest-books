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
import com.javers.books.backend.model.Libro;
import com.javers.books.backend.model.dao.ILibroDao;
import com.javers.books.backend.response.CategoriaResponseRest;
import com.javers.books.backend.response.LibroResponseRest;

@Service
public class LibroServiceImpl implements ILibroService {

	private static final Logger log = LoggerFactory.getLogger(LibroServiceImpl.class);

	@Autowired
	private ILibroDao libroDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarLibros() {
		log.info("Inicio metodo BurcarLibros");
		LibroResponseRest response = new LibroResponseRest();
		try {
			List<Libro> libro = (List<Libro>) libroDao.findAll();
			response.getLibroResponse().setLibro(libro);
			response.setMetadata("Respuesta OK", "200", "Respuesta Exitosa");
		} catch (Exception e) {
			response.setMetadata("Respuesta not OK", "-1", "Error al consultar Libros");
			log.error("Error al consultar libros: ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarPorId(Long id) {
		log.info("Inicio metodo buscarPorId");
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> libroList = new ArrayList<>();
		try {
			Optional<Libro> libro = libroDao.findById(id);
			if (libro.isPresent()) {
				libroList.add(libro.get());
				response.getLibroResponse().setLibro(libroList);
			} else {
				log.error("Error en consultar Libro");
				response.setMetadata("Respuesta Not Ok", "-1", "Libro no encontrado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.error("Error en consultar Libro");
			response.setMetadata("Respuesta Not Ok", "-1", "Error Al consultar Libro");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);

	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> crear(Libro libro) {
		log.info("Inicio metodo crear");
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> libroList = new ArrayList<>();
		try {
			Libro libroGuardada = libroDao.save(libro);
			if (libroGuardada != null) {
				libroList.add(libroGuardada);
				response.getLibroResponse().setLibro(libroList);
			} else {
				log.error("Error al Crear libro");
				response.setMetadata("Respuesta Not Ok", "-1", "Libro no creado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);

			}

		} catch (Exception e) {
			log.error("Error en crear libro");
			response.setMetadata("Respuesta Not Ok", "-1", "Error Al crear Libro");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setMetadata("Respuesta ok", "200", "Categoria Creada");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<LibroResponseRest> actualizar(Libro libro, Long id) {
		log.info("Inicio metodo Actualizar");
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> librosList = new ArrayList<>();

		try {
			Optional<Libro> libroBuscado = libroDao.findById(id);
			if (libroBuscado.isPresent()) {
				libroBuscado.get().setNombre(libro.getNombre());
				libroBuscado.get().setDescripcion(libro.getDescripcion());
				libroBuscado.get().setCategoria(libro.getCategoria());

				Libro libroActualizar = libroDao.save(libroBuscado.get());// Actualizando
				if (libroActualizar != null) {
					response.setMetadata("Respuesta ok", "200", "Libro Actualizado");
					librosList.add(libroActualizar);
					response.getLibroResponse().setLibro(librosList);
				} else {
					log.error("Error en actualizar Libro");
					response.setMetadata("Respuesta not Ok", "-1", "Libro no Actualizado");
					return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
			} else {
				log.error("Error en actualizar Libro");
				response.setMetadata("Respuesta not Ok", "-1", "Libro no Actualizado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("Error en actualizar Libro", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta not Ok", "-1", "Libro no Actualizado");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

	@Override
	
	public ResponseEntity<LibroResponseRest> eliminar(Long id) {
		log.info("Inicio metodo Eliminar Libro");
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			Optional<Libro> libroBuscada = libroDao.findById(id);
			if (libroBuscada.isPresent()) {
			//Eliminamos el registro
			libroDao.deleteById(id);
			response.setMetadata("Respuesta ok", "200", "Libro Eliminado");
			}else {

				log.error("Error en Eliminar Libro");
				response.setMetadata("Respuesta not Ok", "-1", "Libro no encontrado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			
			}
			

		} catch (Exception e) {
			log.error("Error en actualizar libro", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta not Ok", "-1", "Libro no Eliminado");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
 		} 
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}
}
