package timetablepuzzle.eclipselink.DAO;

import java.lang.reflect.ParameterizedType;
import javax.persistence.*;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.eclipselink.entities.E;

public abstract class JPADAO implements DAO {
	@SuppressWarnings("rawtypes")
	protected Class entityClass;

	// @PersistenceContext annotation causes the 
	// eclipse link container to inject the entity manager
	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("rawtypes")
	public JPADAO() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class) genericSuperclass.getActualTypeArguments()[1];
	}

	public void persist(E entity) { entityManager.persist(entity); }

	public void remove(E entity) { entityManager.remove(entity); }

	@SuppressWarnings("unchecked")
	public E findById(int id) { return (E)entityManager.find(entityClass, id); }
}
