package hh.crossreview.configuration;

import jakarta.inject.Inject;
import org.hibernate.SessionFactory;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
