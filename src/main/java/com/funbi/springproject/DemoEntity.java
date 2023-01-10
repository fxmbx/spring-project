package com.funbi.springproject;

import static jakarta.persistence.GenerationType.SEQUENCE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity(name="demo")
@Table(
    name = "demo",
    uniqueConstraints = {
        @UniqueConstraint(name="demo_email_unique", columnNames = "email")
    }
)
public class DemoEntity {
    @Id
    @SequenceGenerator(
        name = "demo_sequence",
        sequenceName = "demo_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = SEQUENCE,
        generator = "demo_sequence"
    )
    @Column( name="id", updatable = false)
    private Long id;
    
    @Column(
        name="first_name",
        nullable = false,
        columnDefinition = "TEXT"

    )
    private String firstName;
    
    @Column(
        name="last_name",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
        name="email",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String email;

    @Column(
        name="age",
        nullable = false
    )
    private Integer age;

    public DemoEntity() {
    }

    public DemoEntity( String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "demo [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", age=" + age + "]";
    }

    
    

}
