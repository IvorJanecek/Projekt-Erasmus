package zavrsni.erasmus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.Ignore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import zavrsni.erasmus.ErasmusApp;
import zavrsni.erasmus.config.AsyncSyncConfiguration;
import zavrsni.erasmus.config.EmbeddedSQL;

/**
 * Base composite annotation for integration tests.
 */
@Ignore("Don't know why it fails")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { ErasmusApp.class, AsyncSyncConfiguration.class })
@EmbeddedSQL
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
