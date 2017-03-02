package name.aknights;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Provider
public class LogRequestFilter implements ContainerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(LogRequestFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
//        logger.debug(new BufferedReader(new InputStreamReader(requestContext.getEntityStream())).lines().collect(Collectors.joining("\n")));
    }
}
