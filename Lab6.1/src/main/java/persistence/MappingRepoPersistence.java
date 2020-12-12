package persistence;


import models.MappingBean;
import modelsPersistence.MappingModelPersistence;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@ManagedBean(name = "mappingRepo")
@SessionScoped
public class MappingRepoPersistence {
    @PersistenceContext(unitName = "MyLab6PU")
    EntityManager em;

    public MappingRepoPersistence() {
    }

    public void addMappingToDatabase(MappingBean mappingBean) {
        MappingModelPersistence mappingModelPersistence =
                new MappingModelPersistence(mappingBean.getMeetingID(),
                        mappingBean.getPersonID());
        if (em == null) {
            System.err.println("EntityManager null");
        }
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.persist(mappingModelPersistence);
        trans.commit();
    }

    public void updateMapping(MappingBean mappingBean) {
        em.getTransaction().begin();
        Query q = em.createQuery("UPDATE Mapping m SET m.meetingID=:mid, m.personID=:pid" +
                " WHERE m.id=:mapId");
        q.setParameter("mid", mappingBean.getMeetingID());
        q.setParameter("pid", mappingBean.getPersonID());
        q.setParameter("mapId", mappingBean.getId());
        q.executeUpdate();
        em.getTransaction().commit();
    }

    public void deleteMapping(String mappingId) {
        em.getTransaction().begin();
        Query q = em.createQuery("DELETE FROM Mapping m WHERE m.id=:mid");
        q.setParameter("mid", mappingId);
        q.executeUpdate();
        em.getTransaction().commit();
    }
}
