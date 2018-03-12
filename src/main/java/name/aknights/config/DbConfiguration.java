package name.aknights.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public final class DbConfiguration {
    @NotEmpty
    private final String dbName;

    @Min(1)
    @Max(65535)
    private final int port;

    @NotEmpty
    private final String host;

    private final String user;

    private final String password;

    public DbConfiguration(@JsonProperty("dbName") String dbName, @JsonProperty("port") int port, @JsonProperty("host") String host,
                           @JsonProperty("user") String user, @JsonProperty("password") String password) {
        this.dbName = dbName;
        this.port = port;
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public final String getDbName() {
        return dbName;
    }

    public final int getPort() {
        return port;
    }

    public final String getHost() {
        return host;
    }

    public final String getUser() {
        return user;
    }

    public final String getPassword() {
        return password;
    }
}
