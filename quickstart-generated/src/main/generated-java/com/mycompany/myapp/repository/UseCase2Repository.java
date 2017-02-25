/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-angular:src/main/java/repository/EntityRepository.java.e.vm
 */
package com.mycompany.myapp.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.*;

import com.mycompany.myapp.domain.UseCase2;
import com.mycompany.myapp.domain.UseCase2_;

public interface UseCase2Repository extends JpaRepository<UseCase2, String> {

    default List<UseCase2> complete(String query, int maxResults) {
        UseCase2 probe = new UseCase2();
        probe.setDummy(query);

        ExampleMatcher matcher = ExampleMatcher.matching() //
                .withMatcher(UseCase2_.dummy.getName(), match -> match.ignoreCase().startsWith());

        Page<UseCase2> page = findAll(Example.of(probe, matcher), new PageRequest(0, maxResults));
        return page.getContent();
    }
}