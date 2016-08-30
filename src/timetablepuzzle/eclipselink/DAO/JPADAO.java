package timetablepuzzle.eclipselink.DAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.*;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;

public class JPADAO<E,K extends Serializable> implements DAO<E,K>{
	
	protected Class<? extends E> entityClass;	
	protected String persistenceUnitName;
	protected EntityManagerFactory emFactory;
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public JPADAO() {
		this.persistenceUnitName = "TimetablePuzzle";
		this.emFactory = Persistence.createEntityManagerFactory(this.persistenceUnitName);
		this.entityManager = emFactory.createEntityManager();
		this.entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
	}

	@Override
	public void persist(E entity) {
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
		}

	@Override
	public void remove(E entity) {
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
		}

	@Override
	public E findById(K id) {  
		entityManager.getTransaction().begin();
		E entity = (E)entityManager.find(entityClass, id);
		entityManager.getTransaction().commit();
		
		return entity;
		}
}
