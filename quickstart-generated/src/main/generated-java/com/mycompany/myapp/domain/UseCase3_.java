/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-angular:src/main/java/domain/EntityMeta_.java.e.vm
 */
package com.mycompany.myapp.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UseCase3.class)
public abstract class UseCase3_ {
    // Composite primary key
    public static volatile SingularAttribute<UseCase3, UseCase3Pk> id;

    // Raw attributes
    public static volatile SingularAttribute<UseCase3, String> dummy;

    // Many to one
    public static volatile SingularAttribute<UseCase3, UseCase2> id2;
}