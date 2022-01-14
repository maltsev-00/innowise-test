package com.innowise.test.model.meta;

import com.innowise.test.model.entity.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public class User_ {
    public static volatile SingularAttribute<User, UUID> id;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, String> email;

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String ID = "id";
}
