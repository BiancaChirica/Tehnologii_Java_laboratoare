package modelsPersistence;

import javax.persistence.*;

@Entity
@Table(name = "Mapping")
public class MappingModelPersistence {
    @Basic
    @Column(name = "meetingID", nullable = false)
    private String meetingID;
    @Basic
    @Column(name = "personID", nullable = false)
    private String personID;

    @Id
    @SequenceGenerator(name = "sequence",
            sequenceName = "mapping_id_seq")
    @GeneratedValue(generator = "sequence")
    private String id;

    public MappingModelPersistence() {
    }

    public MappingModelPersistence(String meetingID, String personID, String id) {
        this.meetingID = meetingID;
        this.personID = personID;
        this.id = id;
    }

    public MappingModelPersistence(String meetingID, String personID) {
        this.meetingID = meetingID;
        this.personID = personID;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
