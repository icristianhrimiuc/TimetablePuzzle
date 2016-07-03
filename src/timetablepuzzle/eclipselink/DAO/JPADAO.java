package timetablepuzzle.eclipselink.DAO;

import java.lang.reflect.Type;
import javax.persistence.*;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.eclipselink.entities.E;

public abstract class JPADAO implements DAO {
	@SuppressWarnings("rawtypes")
	protected Class entityClass;	
	protected String persistenceUnitName;
	protected EntityManagerFactory emFactory;
	protected EntityManager entityManager;

	@SuppressWarnings("rawtypes")
	public JPADAO() {
		this.persistenceUnitName = "TimetablePuzzle";
		this.emFactory = Persistence.createEntityManagerFactory(this.persistenceUnitName);
		this.entityManager = emFactory.createEntityManager();
		Type genericSuperclass = (Type) getClass().getGenericSuperclass();
		this.entityClass = (Class) genericSuperclass.getClass();
	}

	public void persist(E entity) {
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
		}

	public void remove(E entity) {
		entityManager.remove(entity);
		}

	@SuppressWarnings("unchecked")
	public E findById(int id) {
		return (E)entityManager.find(entityClass, id); 
		}
}
