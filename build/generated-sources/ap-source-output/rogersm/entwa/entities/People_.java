package rogersm.entwa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rogersm.entwa.entities.Ideas;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-03-17T18:03:01")
@StaticMetamodel(People.class)
public class People_ { 

    public static volatile SingularAttribute<People, Integer> id;
    public static volatile SingularAttribute<People, String> orgPhoneNumber;
    public static volatile CollectionAttribute<People, Ideas> ideasCollection2;
    public static volatile SingularAttribute<People, String> email;
    public static volatile SingularAttribute<People, String> name;
    public static volatile SingularAttribute<People, String> orgDescription;
    public static volatile CollectionAttribute<People, Ideas> ideasCollection;
    public static volatile SingularAttribute<People, String> type;
    public static volatile SingularAttribute<People, String> orgName;
    public static volatile CollectionAttribute<People, Ideas> ideasCollection1;
    public static volatile SingularAttribute<People, String> password;

}