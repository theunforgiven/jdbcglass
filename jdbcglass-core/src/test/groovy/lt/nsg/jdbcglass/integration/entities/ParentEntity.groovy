package lt.nsg.jdbcglass.integration.entities

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@EqualsAndHashCode
@ToString(includeFields = true)
@Table(name = "people")
public class ParentEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private int id;

    private String name;

    @OneToMany(cascade = [CascadeType.ALL])
    private List<ChildEntity> children = []

    ParentEntity() {
    }

    ParentEntity(String name) {
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

    void addChild(ChildEntity entity) {
        this.children.add(entity);
    }

    boolean removeChild(ChildEntity entity) {
        this.children.remove(entity)
    }

    public List<ChildEntity> getChildren() {
        this.children.asImmutable()
    }
}
