package hh.crossreview.configuration;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class HibernateConfigurationTest {

    private final EntityManagerFactory entityManagerFactory;

    @Inject
    public HibernateConfigurationTest(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Test
    @Transactional
    void givenSession_whenHibernateLoaded_thenConnectionIsSuccessful() {
        assertNotNull(entityManagerFactory.createEntityManager());
    }

}
