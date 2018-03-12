package name.aknights.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class DbConfigurationTest {

    private DbConfiguration config;
    private String dbName;
    private int port;
    private String host;
    private String user;
    private String pas5word;

    @Before
    public void setup() {
        dbName = "dbName";
        port = 5000;
        host = "host";
        user = "user";
        pas5word = "pas5word";
        config = new DbConfiguration(dbName, port, host, user, pas5word);
    }

    @Test
    public void whenAccessorsCalledValueAreReturned() throws Exception {
        assertEquals(dbName, config.getDbName());
        assertEquals(port, config.getPort());
        assertEquals(host, config.getHost());
        assertEquals(user, config.getUser());
        assertEquals(pas5word, config.getPassword());
    }

    @Test
    public void whenInitialisedFromConfigValuesAreInstantiatedCorrectly() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream configYml = this.getClass().getResourceAsStream("/dbConfig.yml");
        DbConfiguration config = mapper.readValue(configYml, DbConfiguration.class);

        assertEquals("localhost", config.getHost());
        assertEquals( 27017, config.getPort());
        assertEquals( "pflomgr", config.getDbName());
        assertEquals( "pflomgr", config.getUser());
        assertEquals( "pas5word", config.getPassword());
    }
}