package rogersm.entwa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rogersm.entwa.entities.People;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-03-20T13:58:53")
@StaticMetamodel(Ideas.class)
public class Ideas_ { 

    public static volatile SingularAttribute<Ideas, Integer> id;
    public static volatile SingularAttribute<Ideas, String> anticipatedDeliverables;
    public static volatile SingularAttribute<Ideas, String> title;
    public static volatile SingularAttribute<Ideas, People> student;
    public static volatile SingularAttribute<Ideas, String> status;
    public static volatile SingularAttribute<Ideas, People> moderator;
    public static volatile SingularAttribute<Ideas, String> description;
    public static volatile SingularAttribute<Ideas, People> submitter;
    public static volatile SingularAttribute<Ideas, Date> dateSubmitted;
    public static volatile SingularAttribute<Ideas, People> organisation;
    public static volatile SingularAttribute<Ideas, String> aimsAndObjectives;
    public static volatile SingularAttribute<Ideas, String> academicQuestion;

}