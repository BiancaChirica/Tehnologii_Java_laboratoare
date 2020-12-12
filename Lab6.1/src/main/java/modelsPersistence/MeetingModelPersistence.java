package modelsPersistence;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Meetings")
public class MeetingModelPersistence implements Serializable {

    @Id
    @SequenceGenerator(name = "sequence",
            sequenceName = "meeting_id_seq")
    @GeneratedValue(generator = "sequence")
    private String meetingID;

    @Basic
    @Column(name = "topic", nullable = false)
    private String topic;

    @Basic
    @Column(name = "startingTime", nullable = false)
    private Date startDate;

    @Basic
    @Column(name = "duration", nullable = false)
    private int duration = 0;

    @Basic
    @Column(name = "locationId")
    private String locationId;

    public MeetingModelPersistence() {
    }

    public MeetingModelPersistence(String topic, Date startDate, int duration, String locationId) {
        this.topic = topic;
        this.startDate = startDate;
        this.duration = duration;
        this.locationId = locationId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}

