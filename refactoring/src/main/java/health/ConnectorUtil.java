package health;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class ConnectorUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectorUtil.class);

    private final CloseableHttpClient client;

    public ConnectorUtil(CloseableHttpClient aClient) {
        this.client = aClient;
    }

    public Optional<String> getConnectorStatus(ProbeType probe) throws IOException {
        LOG.debug("request to endpoint:{}", probe.getEndpoint());
        HttpGet request = new HttpGet("http://localhost:8080/" + probe.getEndpoint());
        try (CloseableHttpResponse response = client.execute(request)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                LOG.debug("request to endpoint not ok with response {}", response);
                return Optional.of(responseBody);
            }
        }
        return Optional.empty();
    }


    public enum ProbeType {
        READY("ready"), ALIVE("alive");

        private final String endpoint;

        ProbeType(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getEndpoint() {
            return endpoint;
        }
    }
}
