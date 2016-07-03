package timetablepuzzle.eclipselink.DAO.interfaces;

import java.io.Serializable;

public interface DAO<E,K extends Serializable> {
    void persist(E entity);
    void remove(E entity);
    E findById(K externalId);
}
