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
    @NamedQuery(name = "Ideas.findByPerson", query = "SELECT i FROM Ideas i WHERE i.submitter.id = :submitter "
        + "OR i.moderator.id = :moderator "
        + "OR i.student.id = :student "
        + "OR i.organisation.id = :organisation "),
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

    /**
     *
     */
    public Ideas() {
    }

    /**
     *
     * @param id
     */
    public Ideas(Integer id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param title
     * @param description
     * @param status
     */
    public Ideas(Integer id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getAimsAndObjectives() {
        return aimsAndObjectives;
    }

    /**
     *
     * @param aimsAndObjectives
     */
    public void setAimsAndObjectives(String aimsAndObjectives) {
        this.aimsAndObjectives = aimsAndObjectives;
    }

    /**
     *
     * @return
     */
    public String getAcademicQuestion() {
        return academicQuestion;
    }

    /**
     *
     * @param academicQuestion
     */
    public void setAcademicQuestion(String academicQuestion) {
        this.academicQuestion = academicQuestion;
    }

    /**
     *
     * @return
     */
    public String getAnticipatedDeliverables() {
        return anticipatedDeliverables;
    }

    /**
     *
     * @param anticipatedDeliverables
     */
    public void setAnticipatedDeliverables(String anticipatedDeliverables) {
        this.anticipatedDeliverables = anticipatedDeliverables;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public Date getDateSubmitted() {
        if(dateSubmitted == null){
            dateSubmitted = new Date();
        }
        return dateSubmitted;
    }

    /**
     *
     * @param dateSubmitted
     */
    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    /**
     *
     * @return
     */
    public People getModerator() {
        return moderator;
    }

    /**
     *
     * @param moderator
     */
    public void setModerator(People moderator) {
        this.moderator = moderator;
    }

    /**
     *
     * @return
     */
    public People getSubmitter() {
        return submitter;
    }

    /**
     *
     * @param submitter
     */
    public void setSubmitter(People submitter) {
        this.submitter = submitter;
    }
    
    /**
     *
     * @return
     */
    public People getOrganisation() {
        return organisation;
    }

    /**
     *
     * @param organisation
     */
    public void setOrganisation(People organisation) {
        this.organisation = organisation;
    }

    /**
     *
     * @return
     */
    public People getStudent() {
        return student;
    }

    /**
     *
     * @param student
     */
    public void setStudent(People student) {
        this.student = student;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.title;
        //return "entwa.entities.Ideas[ id=" + id + " ]";
    }
    
}
