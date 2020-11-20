package models;

import database.PersonRepository;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ManagedBean(name = "personBean")
@SessionScoped
public class PersonBean implements Serializable {
    private String name;
    private String personID;

    private List<PersonBean> allPersons = new ArrayList<>();

    private final PersonRepository personRepository = new PersonRepository();

    public PersonBean() {
    }

    public PersonBean(String id, String name) {
        this.name = name;
        this.personID = id;
    }

    public List<PersonBean> getAllPersons() {
        allPersons.clear();
        allPersons.addAll(personRepository.getAllPersons());
        return allPersons;
    }

    public void setAllPersons(List<PersonBean> allPersons) {
        this.allPersons = allPersons;
    }

    public void onClose()
    {
        this.name = null;
        this.personID = null;
        addMessage("Cancel pressed");
    }

    public void save() {
        if (this.name == null) {
            return;
        }
        // to do : increment id
        Random random = new Random();
        this.personID = String.valueOf(random.nextInt(10000000));

        personRepository.addPerson(this);
        addMessage("Person " + name + " added to the database.");
        this.name = null;
        this.personID = null;
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String id) {
        this.personID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

