package models;

import database.MeetingRepository;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@ManagedBean(name = "meetingBean")
@SessionScoped
public class MeetingBean implements Serializable {
    private String meetingID;
    @NotNull
    private String topic;
    @NotNull
    private Date startDate;
    @NotNull
    private int duration = 0;
    private String locationId;

    private List<MeetingBean> allMeetings = new ArrayList<>();

    private final MeetingRepository meetingRepository = new MeetingRepository();

    public MeetingBean() {
    }

    public MeetingBean(String id, String topic, Date startingTime, int duration, String locationId) {
        this.meetingID = id;
        this.topic = topic;
        this.startDate = startingTime;
        this.duration = duration;
        this.locationId = locationId;
    }

    public List<MeetingBean> getAllMeetings() {
        allMeetings.clear();
        allMeetings.addAll(meetingRepository.getAllMeetings());
        return allMeetings;
    }

    public void validateDuration(FacesContext context,
                                 UIComponent component, Object value) {
        int duration = (int) value;
        if (duration < 1) {
            ((UIInput) component).setValid(false);
            context.addMessage(component.getClientId(context),
                    new FacesMessage("Duration can't be negative or 0."));
        }
    }

    public void onClose() {

        this.topic = null;
        this.startDate = null;
        this.duration = 0;
        this.locationId = null;
        addMessage("Cancel was pressed");
    }

    public void save() {
        if (this.topic == null || this.startDate == null || this.duration == 0
                || this.locationId == null || this.locationId.equals("")) {
            return;
        }
        // to do : increment id
        Random random = new Random();
        this.meetingID = String.valueOf(random.nextInt(10000000));

        meetingRepository.addMeeting(this);
        addMessage("Meeting " + topic + " added to the database.");
        this.topic = null;
        this.startDate = null;
        this.duration = 0;
        this.locationId = null;

        System.out.println("Meeting added.");
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String id) {
        this.meetingID = id;
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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}