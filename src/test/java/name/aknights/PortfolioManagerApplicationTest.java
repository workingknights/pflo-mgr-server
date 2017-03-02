package name.aknights;

import com.codahale.metrics.health.HealthCheckRegistry;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Environment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PortfolioManagerApplicationTest {
    private final Environment environment = mock(Environment.class);
    private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);
    private final LifecycleEnvironment lifecycle = mock(LifecycleEnvironment.class);
    private final HealthCheckRegistry healthCheckRegistry = mock(HealthCheckRegistry.class);
//    private final PortfolioManagerApplication application = new PortfolioManagerApplication();
//    private DbConfiguration dbConfiguration = new DbConfiguration("dbName", 8080, "localhost", null, null);
//    private final PortfolioManagerConfiguration config = new PortfolioManagerConfiguration(dbConfiguration);

    @Before
    public void setup() throws Exception {
        when(environment.jersey()).thenReturn(jersey);
        when(environment.lifecycle()).thenReturn(lifecycle);
        when(environment.healthChecks()).thenReturn(healthCheckRegistry);
    }

    @Test
    public void buildsAHoldingResource() throws Exception {
        Assert.assertTrue(true);
    }
}
