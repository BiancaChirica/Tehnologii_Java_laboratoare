package services;


import entities.UserJPA;

import javax.enterprise.inject.Default;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Default
public class UserService implements Serializable {

    @PersistenceContext(unitName = "lab8")
    EntityManager em;

    @Transactional
    public boolean createUser(String username, String password, String role) {

        try {
            UserJPA newUser = new UserJPA(username, password, role);
            em.persist(newUser);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkUser(String username, String password) {
        Query q = em.createQuery("SELECT us FROM UserJPA us where us.username LIKE :providedUsername and us.password LIKE :providedPassword");
        q.setParameter("providedUsername", username);
        q.setParameter("providedPassword", password);
        List<UserJPA> users = q.getResultList();
        if (users.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public UserJPA getByUsername(String username) {
        Query q = em.createQuery("SELECT us FROM UserJPA us where us.username Like :providedUsername");
        q.setParameter("providedUsername", username);
        List<UserJPA> users = q.getResultList();
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public String getRoleByUsername(String username) {
        Query q = em.createQuery("SELECT us FROM UserJPA us where us.username Like :providedUsername");
        q.setParameter("providedUsername", username);
        List<UserJPA> users = q.getResultList();
        if (users.size() == 1) {
            return users.get(0).getRole();
        } else {
            return null;
        }
    }

}
