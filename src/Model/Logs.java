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
@Table(name = "LOGS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logs.findAll", query = "SELECT l FROM Logs l")
    , @NamedQuery(name = "Logs.findByDate", query = "SELECT l FROM Logs l WHERE l.date = :date")
    , @NamedQuery(name = "Logs.findByContents", query = "SELECT l FROM Logs l WHERE l.contents = :contents")
    , @NamedQuery(name = "Logs.findByEmail", query = "SELECT l FROM Logs l WHERE l.email = :email")})
public class Logs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "DATE")
    private String date;
    @Basic(optional = false)
    @Column(name = "CONTENTS")
    private String contents;
    @Id
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;

    public Logs() {
    }

    public Logs(String email) {
        this.email = email;
    }

    public Logs(String email, String date, String contents) {
        this.email = email;
        this.date = date;
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getEmail() { //primary key for table
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void updateLog(String date, String contents) {
        this.date = date;
        this.contents = contents;
    }
    
    public void viewLog() {
        System.out.println(date + " "  + contents);
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
        if (!(object instanceof Logs)) {
            return false;
        }
        Logs other = (Logs) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return date + " " + contents + " " + email;
    }
    
}
