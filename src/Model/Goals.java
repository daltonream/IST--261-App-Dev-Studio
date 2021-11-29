/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javafx.scene.Node;
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
@Table(name = "GOALS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Goals.findAll", query = "SELECT g FROM Goals g")
    , @NamedQuery(name = "Goals.findById", query = "SELECT g FROM Goals g WHERE g.id = :id")
    , @NamedQuery(name = "Goals.findByDate", query = "SELECT g FROM Goals g WHERE g.date = :date")
    , @NamedQuery(name = "Goals.findByContents", query = "SELECT g FROM Goals g WHERE g.contents = :contents")
    , @NamedQuery(name = "Goals.countEntries",  query = "SELECT COUNT(goals) FROM Goals goals")})
public class Goals implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "DATE")
    private String date;
    @Basic(optional = false)
    @Column(name = "CONTENTS")
    private String contents;
    
    
    public Goals() {
    }


    public Goals(Integer id, String date, String contents) {
        this.id = id;
        this.date = date;
        this.contents = contents;
        
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    
    public boolean decideToInsert(Goals goal){
        boolean decision = false;
        if (goal.id < this.id) {
             decision = true;
         }       
            
        return decision;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Goals)) {
            return false;
        }
        Goals other = (Goals) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User Id " + this.id + " Date " + this.date + " Contents " + this.contents;
    }
    
}
