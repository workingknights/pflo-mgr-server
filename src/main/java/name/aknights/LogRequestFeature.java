package name.aknights;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class LogRequestFeature implements DynamicFeature {
    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
//        if (resourceInfo.getResourceMethod().getName().equals(HoldingsResource.class.getName())) {
            context.register(LogRequestFilter.class);
//        }
    }
}