/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rogersm.entwa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MikeRogers
 */
@Entity
@Table(catalog = "", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ideas.findAll", query = "SELECT i FROM Ideas i"),
    @NamedQuery(name = "Ideas.findById", query = "SELECT i FROM Ideas i WHERE i.id = :id"),
    @NamedQuery(name = "Ideas.findByPerson", query = "SELECT i FROM Ideas i WHERE i.submitter.id = :submitter"),
    @NamedQuery(name = "Ideas.findByTitle", query = "SELECT i FROM Ideas i WHERE i.title = :title"),
    @NamedQuery(name = "Ideas.searchByTitle", query = "SELECT i FROM Ideas i WHERE i.title = :title"),
    @NamedQuery(name = "Ideas.findByStatus", query = "SELECT i FROM Ideas i WHERE i.status = :status"),
    @NamedQuery(name = "Ideas.findByDateSubmitted", query = "SELECT i FROM Ideas i WHERE i.dateSubmitted = :dateSubmitted")})
public class Ideas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String title;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private String description;
    @Lob
    @Column(name = "AIMS_AND_OBJECTIVES")
    private String aimsAndObjectives;
    @Lob
    @Column(name = "ACADEMIC_QUESTION")
    private String academicQuestion;
    @Lob
    @Column(name = "ANTICIPATED_DELIVERABLES")
    private String anticipatedDeliverables;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String status;
    @Column(name = "DATE_SUBMITTED")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateSubmitted;
    @JoinColumn(name = "MODERATOR", referencedColumnName = "ID")
    @ManyToOne
    private People moderator;
    @JoinColumn(name = "SUBMITTER", referencedColumnName = "ID")
    @ManyToOne
    private People submitter;
    @JoinColumn(name = "STUDENT", referencedColumnName = "ID")
    @ManyToOne
    private People student;
    @JoinColumn(name = "ORGANISATION", referencedColumnName = "ID")
    @ManyToOne
    private People organisation;

    public Ideas() {
    }

    public Ideas(Integer id) {
        this.id = id;
    }

    public Ideas(Integer id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAimsAndObjectives() {
        return aimsAndObjectives;
    }

    public void setAimsAndObjectives(String aimsAndObjectives) {
        this.aimsAndObjectives = aimsAndObjectives;
    }

    public String getAcademicQuestion() {
        return academicQuestion;
    }

    public void setAcademicQuestion(String academicQuestion) {
        this.academicQuestion = academicQuestion;
    }

    public String getAnticipatedDeliverables() {
        return anticipatedDeliverables;
    }

    public void setAnticipatedDeliverables(String anticipatedDeliverables) {
        this.anticipatedDeliverables = anticipatedDeliverables;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateSubmitted() {
        if(dateSubmitted == null){
            dateSubmitted = new Date();
        }
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public People getModerator() {
        return moderator;
    }

    public void setModerator(People moderator) {
        this.moderator = moderator;
    }

    public People getSubmitter() {
        return submitter;
    }

    public void setSubmitter(People submitter) {
        this.submitter = submitter;
    }
    
    public People getOrganisation() {
        return organisation;
    }

    public void setOrganisation(People organisation) {
        this.organisation = organisation;
    }

    public People getStudent() {
        return student;
    }

    public void setStudent(People student) {
        this.student = student;
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
        if (!(object instanceof Ideas)) {
            return false;
        }
        Ideas other = (Ideas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.title;
        //return "entwa.entities.Ideas[ id=" + id + " ]";
    }
    
}
