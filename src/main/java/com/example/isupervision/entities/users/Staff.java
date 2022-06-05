package com.example.isupervision.entities.users;

import com.example.isupervision.entities.roles.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table
public class Staff extends User{

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_email", referencedColumnName = "email"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Staff() {

    }

    public Staff(
            String firstname,
            String lastName,
            String email,
            String password,
            List<Role> roles
    ){
//        setId(UUID.randomUUID());
        setFirstName(firstname);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "roles=" + roles +
                '}';
    }
}
