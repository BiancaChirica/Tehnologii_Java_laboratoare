package models;

import database.MappingRepository;
import database.MeetingRepository;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "mappingBean")
@SessionScoped
public class MappingBean implements Serializable {
    private String meetingID;
    private String personID;

    private List<MeetingBean> personMeetings = new ArrayList<>();

    private final MappingRepository mappingRepository = new MappingRepository();
    private final MeetingRepository meetingRepository = new MeetingRepository();

    public MappingBean() {
    }

    public MappingBean(String meetingID, String personID) {
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

    public void save() {
        if (meetingID == null || personID == null)
            return;
        mappingRepository.addMapping(meetingID, personID);
        addMessage("Person " + personID + " added to the meeting " + meetingID);
    }

    public void onClose()
    {
        this.meetingID = null;
        this.personID = null;
        addMessage("Cancel pressed");
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<MeetingBean> getPersonMeetings() {
        if (personID == null)
            return null;
        personMeetings.clear();
        List<String> meetingIdList = mappingRepository.getAllMeetingsForAPerson(personID);
        if(!meetingIdList.isEmpty())
        personMeetings.addAll(meetingRepository.getMeetingsById(meetingIdList));
        else
            System.out.println("Meeting id list with strings is empty ");
        if(personMeetings.isEmpty())
            System.out.println("Meeting list is empty");
        System.out.println(personMeetings.size() + "" );
        return personMeetings;
    }

    public void setPersonMeetings(List<MeetingBean> personMeetings) {
        this.personMeetings = personMeetings;
    }
}
