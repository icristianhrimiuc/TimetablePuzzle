package timetablepuzzle.eclipselink.DAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.*;

import timetablepuzzle.eclipselink.DAO.interfaces.DAO;
import timetablepuzzle.swing.windows.cards.other.BuildingsCard;

public class JPADAO<E,K extends Serializable> implements DAO<E,K>{
	protected final static Logger LOGGER = Logger.getLogger(BuildingsCard.class.getName());
	
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
		LOGGER.log(Level.FINE, "A new entity type {0} has been created. ", new Object[]{this.entityClass});
		}

	@Override
	public void merge(E entity) {
		entityManager.getTransaction().begin();
		entityManager.merge(entity);
		entityManager.getTransaction().commit();
		LOGGER.log(Level.FINE, "A new entity type {0} has been merged. ", new Object[]{this.entityClass});
		}

	@Override
	public void remove(E entity) {
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
		LOGGER.log(Level.FINE, "A entity type {0} has been removed. ", new Object[]{this.entityClass});
		}

	@Override
	public E findById(K id) {  
		entityManager.getTransaction().begin();
		E entity = (E)entityManager.find(entityClass, id);
		entityManager.getTransaction().commit();
		
		return entity;
		}
}
