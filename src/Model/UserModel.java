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
@Table(name = "USERMODEL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserModel.findAll", query = "SELECT u FROM UserModel u")
    , @NamedQuery(name = "UserModel.findByFirstname", query = "SELECT u FROM UserModel u WHERE u.firstName = :firstname")
    , @NamedQuery(name = "UserModel.findByLastname", query = "SELECT u FROM UserModel u WHERE u.lastName = :lastName")
    , @NamedQuery(name = "UserModel.findByEmail", query = "SELECT u FROM UserModel u WHERE u.email = :email")
    , @NamedQuery(name = "UserModel.findByHeight", query = "SELECT u FROM UserModel u WHERE u.height = :height")
    , @NamedQuery(name = "UserModel.findByWeight", query = "SELECT u FROM UserModel u WHERE u.weight = :weight")
    , @NamedQuery(name = "UserModel.findByAge", query = "SELECT u FROM UserModel u WHERE u.age = :age")})
public class UserModel implements Serializable, Printable {

    @Basic(optional = false)
    @Column(name = "WEIGHT")
    private int weight;
    @Column(name = "AGE")
    private Integer age;

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "FIRSTNAME")
     String firstName;
    @Basic(optional = false)
    @Column(name = "LASTNAME")
     String lastName;
    @Id
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @Column(name = "HEIGHT")
     String height;


    public UserModel() {  
    }

    public UserModel(String email) {
        this.email = email;
    }

    public UserModel(String firstName, String lastName, String email, String height, int weight, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.age = age;
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
    
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public void viewAccount() { 
        System.out.println(email + " " + firstName + " " + lastName + " " + height + " " + weight + " " + age); // this would write to the controller and not be a print statement
    }
   public String caluclateBMI() {
        int userFeet;
        int userInches;

        if (height.length() == 3) { //4

            String feet = height.substring(0, 1);
            String inches = height.substring(2, 3);

            userFeet = Integer.parseInt(feet);
            userInches = Integer.parseInt(inches);

            int heightInInches = (userFeet * 12) + userInches;
            Double tempBmi = (weight * 703.0) / (heightInInches * heightInInches);
            String stringBmi = String.format("%.2f", tempBmi);

            return stringBmi;
            
        } else if (height.length() == 4) { //5

            String feet = height.substring(0, 1);
            String inches = height.substring(2, 4);
            userFeet = Integer.parseInt(feet);
            userInches = Integer.parseInt(inches);

            int heightInInches = (userFeet * 12) + userInches;
            System.out.println(heightInInches);
            System.out.println(weight);
            Double tempBmi = (weight * 703.0) / (heightInInches * heightInInches);
            String stringBmi = String.format("%.2f", tempBmi);

            return stringBmi;
        }
        return null;
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
        if (!(object instanceof UserModel)) {
            return false;
        }
        UserModel other = (UserModel) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return email + " " + firstName + " " + lastName + " " + height + " " + weight + " " + age ;
    }

   @Override
    public void setUpPrint() {
         String info = "User Attributes \n---------------\nUser Name: " + this.firstName + this.lastName +  "\nHeight: " + this.height + "\nWeight: " + this.weight + "\nAge: " + this.age;
         print(info);
    }

    @Override
    public void print(String info) { 
        System.out.println(info);
    }
    
}
