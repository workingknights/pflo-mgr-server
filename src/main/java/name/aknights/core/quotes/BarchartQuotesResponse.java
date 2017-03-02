package name.aknights.core.quotes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/*
 {
    "status": {
        "code": 200,
        "message": "Success."
    },
    "results": [
    {
        "symbol": "CPJ1.LS",
        "exchange": "LSE",
        ...
    }
 }
 */
public class BarchartQuotesResponse {

    @JsonProperty
    private Status status;

    @JsonProperty
    private Collection<BarchartQuoteDetail> results;

    public Status getStatus() {
        return status;
    }

    public Collection<BarchartQuoteDetail> getResults() {
        return results;
    }

    class Status {
        @JsonProperty
        int code;
        @JsonProperty
        String message;

        public Status() {
        }
    }
}
