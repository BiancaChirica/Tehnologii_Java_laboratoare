package persistence;

import models.LocationBean;
import modelsPersistence.LocationModelPersistence;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@ManagedBean(name = "mappingRepo")
@SessionScoped
public class LocationRepoPersistence {
    @PersistenceContext(unitName = "MyLab6PU")
    EntityManager em;

    public LocationRepoPersistence() {
    }

    public void addLocationToDatabase(LocationBean locationBean) {
        LocationModelPersistence locationModelPersistence =
                new LocationModelPersistence(locationBean.getLocationName());
        if (em == null) {
            System.err.println("EntityManager null");
        }
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.persist(locationModelPersistence);
        trans.commit();
    }

    public void updateLocation(LocationBean locationBean) {
        em.getTransaction().begin();
        Query q = em.createQuery("UPDATE Locations l SET locationName=:name WHERE l.locationID=:locId");
        q.setParameter("locId", locationBean.getLocationID());
        q.setParameter("name", locationBean.getLocationName());
        q.executeUpdate();
        em.getTransaction().commit();
    }

    public void deleteLocation(String locationId) {
        em.getTransaction().begin();
        Query q = em.createQuery("DELETE FROM Locations l WHERE l.locationID=:lid");
        q.setParameter("lid", locationId);
        q.executeUpdate();
        em.getTransaction().commit();
    }
}
