/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author daltonream
 */
@Entity
@Table(name = "CALORIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calories.findAll", query = "SELECT c FROM Calories c")
    , @NamedQuery(name = "Calories.findByCalories", query = "SELECT c FROM Calories c WHERE c.calories = :calories")
    , @NamedQuery(name = "Calories.findByEmail", query = "SELECT c FROM Calories c WHERE c.email = :email")})
public class Calories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "CALORIES")
    private Integer calories;
    @Id
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;

    public Calories() {
    }
    
    public Calories(Integer calories, String email) {
        this.calories = calories;
        this.email = email;
    }

    public Calories(String email) {
        this.email = email;
    }

    public Integer getCalories() {
        return calories;
    }


    public void setCalories(Integer calories) { //can be used to update calories
        this.calories = calories;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calories)) {
            return false;
        }
        Calories other = (Calories) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return calories + " " + email;
    }
    
}
