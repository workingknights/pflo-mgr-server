package name.aknights.module;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import dagger.Module;
import dagger.Provides;
import name.aknights.config.QuotesServiceConfiguration;
import name.aknights.config.YahooQuotesConfiguration;
import name.aknights.services.BarchartFxRatesService;
import name.aknights.services.BarchartQuotesService;
import name.aknights.services.CachingFxRatesService;
import name.aknights.services.CachingQuotesService;
import name.aknights.services.FxRatesService;
import name.aknights.services.LocalFxRatesService;
import name.aknights.services.LocalQuotesService;
import name.aknights.services.QuotesService;
import name.aknights.services.YahooFxRatesService;
import name.aknights.services.YahooQuotesService;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.inject.Singleton;
import javax.ws.rs.client.Client;

@Module
public class QuotesModule {

    private static final String BARCHART = "BARCHART";
    private static final String YAHOO = "YAHOO";

    private final QuotesServiceConfiguration config;

    public QuotesModule(QuotesServiceConfiguration config) {
        this.config = config;
    }

    private Client getClient()  {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
        jacksonProvider.setMapper(objectMapper);
        return  JerseyClientBuilder.createClient(new ClientConfig(jacksonProvider));
    }

    @Singleton
    @Provides
    QuotesService provideQuotesService() {
        QuotesService[] services = (config == null)
                ? new QuotesService[] {new LocalQuotesService()}
                : new QuotesService[] {new BarchartQuotesService(getClient(), config), new YahooQuotesService(getClient(), config)};

        if (config != null && config.getCacheTtl().isPresent())
            return new CachingQuotesService(config.getCacheTtl().get(), services);
        else
            return new CachingQuotesService(services);
    }

    @Singleton
    @Provides
    FxRatesService provideFxRatesService() {
        FxRatesService[] services = (config == null)
                ? new FxRatesService[] {new LocalFxRatesService()}
                : new FxRatesService[] {new BarchartFxRatesService(getClient(), config), new YahooFxRatesService(getClient(), config)};


        if (config != null && config.getCacheTtl().isPresent())
            return new CachingFxRatesService(config.getCacheTtl().get(), services);
        else
            return new CachingFxRatesService(services);
    }

}