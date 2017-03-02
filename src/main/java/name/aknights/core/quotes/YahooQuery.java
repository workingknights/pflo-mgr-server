package name.aknights.core.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * {
 *   "query": {
 *      "count":3,
 *      "created":"2016-12-13T14:05:40Z",
 *      "lang":"en",
 *      "results":{
 *          "quote":[
 *          ]
 *          }
 *     }
 * }
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class YahooQuery {
    private int count;
    private String created;
    private YahooResults results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public YahooResults getResults() {
        return results;
    }

    public void setResults(YahooResults results) {
        this.results = results;
    }
}
