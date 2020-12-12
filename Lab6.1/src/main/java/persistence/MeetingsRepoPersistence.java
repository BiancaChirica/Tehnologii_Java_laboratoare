package persistence;

import models.MeetingBean;
import modelsPersistence.MeetingModelPersistence;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@ManagedBean(name = "meetingRepo")
@SessionScoped
public class MeetingsRepoPersistence {

    @PersistenceContext(unitName = "MyLab6PU")
    EntityManager em;

    public MeetingsRepoPersistence() {
    }

    public void addMeetingToDatabase(MeetingBean meetingBean) {
        MeetingModelPersistence meetingModelPersistence = new MeetingModelPersistence(meetingBean.getTopic(), meetingBean.getStartDate(),
                meetingBean.getDuration(), meetingBean.getLocationId());
        if (em == null) {
            System.err.println("EntityManager null");
        }
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.persist(meetingModelPersistence);
        trans.commit();
    }

    public void updateMeeting(MeetingBean meetingBean) {
        em.getTransaction().begin();
        Query q = em.createQuery("UPDATE Meetings m SET m.topic=:top, m.duration=:dur," +
                " m.locationId=:locId WHERE m.meetingID=:mid");
        q.setParameter("top", meetingBean.getTopic());
        q.setParameter("dur", meetingBean.getDuration());
        q.setParameter("locId", meetingBean.getLocationId());
        q.setParameter("mid", meetingBean.getMeetingID());
        q.executeUpdate();
        em.getTransaction().commit();
    }

    public void deleteMeeting(String meetingId) {
        em.getTransaction().begin();
        Query q = em.createQuery("DELETE FROM Meetings m WHERE m.meetingID=:id");
        q.setParameter("id", meetingId);
        q.executeUpdate();
        em.getTransaction().commit();
    }
}
