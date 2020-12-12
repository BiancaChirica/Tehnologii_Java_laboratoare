package modelsPersistence;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Persons")
public class PersonsModelPersistence implements Serializable {

    @Id
    @SequenceGenerator(name = "sequence",
            sequenceName = "persons_id_seq")
    @GeneratedValue(generator = "sequence")
    private String personID;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;


    public PersonsModelPersistence() {
    }

    public PersonsModelPersistence(String name) {
        this.name = name;
    }

    public void setPersonID(String id) {
        this.personID = id;
    }

    public String getPersonID() {
        return this.personID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
