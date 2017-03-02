package name.aknights.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class DbConfiguration {
    @NotEmpty
    @JsonProperty
    private String dbName;

    @Min(1)
    @Max(65535)
    @JsonProperty
    private int port;

    @NotEmpty
    @JsonProperty
    private String host;

    @JsonProperty
    private String user;

    @JsonProperty
    private String password;

    public DbConfiguration() {
    }

    public DbConfiguration(String dbName, int port, String host, String user, String password) {
        this.dbName = dbName;
        this.port = port;
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
