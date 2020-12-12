package persistence;


import models.PersonBean;
import modelsPersistence.PersonsModelPersistence;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@ManagedBean(name = "personRepo")
@SessionScoped
public class PersonsRepoPersistence {
    @PersistenceContext(unitName = "MyLab6PU")
    EntityManager em;

    public PersonsRepoPersistence() {
    }

    /**
     * Adds a person to the database
     *
     * @param personBean : the person to be added
     */
    public void addPersonToDatabase(PersonBean personBean) {
        PersonsModelPersistence pm = new PersonsModelPersistence(personBean.getName());
        if (personBean.getName() == null) {
            System.out.println("name null");
            return;
        }
        if (em == null) {
            System.err.println("EntityManager null");
        }
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.persist(pm);
        trans.commit();
    }

    public void updatePerson(PersonBean personBean) {
        em.getTransaction().begin();
        Query q = em.createQuery("UPDATE Persons p SET p.name=:nm WHERE p.personID=:pid");
        q.setParameter("nm", personBean.getName());
        q.setParameter("pid", personBean.getPersonID());
        q.executeUpdate();
        em.getTransaction().commit();
    }

    public void deletePerson(String personId) {
        em.getTransaction().begin();
        Query q = em.createQuery("DELETE FROM Persons p WHERE p.personID=:pid");
        q.setParameter("pid", personId);
        q.executeUpdate();
        em.getTransaction().commit();
    }
}
