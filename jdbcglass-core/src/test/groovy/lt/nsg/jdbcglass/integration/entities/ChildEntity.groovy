package lt.nsg.jdbcglass.integration.entities

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@EqualsAndHashCode
@ToString(includeFields = true)
@Table(name = "children")
public class ChildEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String name;

    ChildEntity() {}

    ChildEntity(String name) {
        this.name = name
    }

    int getId() {
        return id
    }

    void setId(int id) {
        this.id = id
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }
}
