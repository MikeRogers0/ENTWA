/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entwa.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MikeRogers
 */
@Entity
@Table(catalog = "", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "People.findAll", query = "SELECT p FROM People p"),
    @NamedQuery(name = "People.findById", query = "SELECT p FROM People p WHERE p.id = :id"),
    @NamedQuery(name = "People.findByName", query = "SELECT p FROM People p WHERE p.name = :name"),
    @NamedQuery(name = "People.findByEmail", query = "SELECT p FROM People p WHERE p.email = :email"),
    @NamedQuery(name = "People.findByType", query = "SELECT p FROM People p WHERE p.type = :type"),
    @NamedQuery(name = "People.findByOrgName", query = "SELECT p FROM People p WHERE p.orgName = :orgName"),
    @NamedQuery(name = "People.findByOrgPhoneNumber", query = "SELECT p FROM People p WHERE p.orgPhoneNumber = :orgPhoneNumber"),
    @NamedQuery(name = "People.findByPassword", query = "SELECT p FROM People p WHERE p.password = :password")})
public class People implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String type;
    @Size(max = 255)
    @Column(name = "ORG_NAME", length = 255)
    private String orgName;
    @Lob
    @Column(name = "ORG_DESCRIPTION")
    private String orgDescription;
    @Size(max = 255)
    @Column(name = "ORG_PHONE_NUMBER", length = 255)
    private String orgPhoneNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String password;
    @OneToMany(mappedBy = "moderator")
    private Collection<Ideas> ideasCollection;
    @OneToMany(mappedBy = "submitter")
    private Collection<Ideas> ideasCollection1;
    @OneToMany(mappedBy = "student")
    private Collection<Ideas> ideasCollection2;

    public People() {
    }

    public People(Integer id) {
        this.id = id;
    }

    public People(Integer id, String name, String email, String type, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public String getOrgPhoneNumber() {
        return orgPhoneNumber;
    }

    public void setOrgPhoneNumber(String orgPhoneNumber) {
        this.orgPhoneNumber = orgPhoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public Collection<Ideas> getIdeasCollection() {
        return ideasCollection;
    }

    public void setIdeasCollection(Collection<Ideas> ideasCollection) {
        this.ideasCollection = ideasCollection;
    }

    @XmlTransient
    public Collection<Ideas> getIdeasCollection1() {
        return ideasCollection1;
    }

    public void setIdeasCollection1(Collection<Ideas> ideasCollection1) {
        this.ideasCollection1 = ideasCollection1;
    }

    @XmlTransient
    public Collection<Ideas> getIdeasCollection2() {
        return ideasCollection2;
    }

    public void setIdeasCollection2(Collection<Ideas> ideasCollection2) {
        this.ideasCollection2 = ideasCollection2;
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
        if (!(object instanceof People)) {
            return false;
        }
        People other = (People) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name +" ("+this.type+")";
        //return "entwa.entities.People[ id=" + id + " ]";
    }
    
}
