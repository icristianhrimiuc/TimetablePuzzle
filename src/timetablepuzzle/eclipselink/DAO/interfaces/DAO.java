package timetablepuzzle.eclipselink.DAO.interfaces;

import timetablepuzzle.eclipselink.entities.E;

public interface DAO {
    void persist(E entity);
    void remove(E entity);
    E findById(int externalId);
}
