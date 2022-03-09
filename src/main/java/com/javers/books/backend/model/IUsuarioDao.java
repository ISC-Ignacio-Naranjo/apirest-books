package com.javers.books.backend.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
	
	public Usuario findByNombreUsuario(String nombreUsuario);
	
	@Query("select u from Usuario u where u.nombreUsuario=?1")
	public Usuario finByIdNombreUsuarioV2(String nombreUsuario);

}
