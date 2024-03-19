package com.example.backend.configuration;

import jakarta.inject.Inject;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class HibernateConfigurationTest {

    private final SessionFactory sessionFactory;

    @Inject
    public HibernateConfigurationTest(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Test
    @Transactional
    void givenSession_whenHibernateLoaded_thenConnectionIsSuccessful() {
        assertNotNull(sessionFactory.getCurrentSession());
    }

}
