package health;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConnectorUtilTest {

    @Mock
    private CloseableHttpClient closeableHttpClient = mock(CloseableHttpClient.class);
    @Mock
    private CloseableHttpResponse response = mock(CloseableHttpResponse.class);
    @Mock
    private HttpEntity entity = mock(HttpEntity.class);

    @Test
    void ensureKafkaConnectorStatusWithOk() throws IOException {
        var result = getKafkaConnectorStatusFor(200, "OK");

        assertThat(result).isEqualTo(Optional.empty());
    }

    private Optional<String> getKafkaConnectorStatusFor(int statusCode, String reasonPhrase) throws IOException {
        var kafkaConnectorUtil = new ConnectorUtil(closeableHttpClient);
        var readyProbe = ConnectorUtil.ProbeType.READY;

        when(response.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, statusCode, reasonPhrase));
        when(entity.getContent()).thenReturn(new ByteArrayInputStream("{\"response\": \"test has some issue\"}".getBytes()));
        when(response.getAllHeaders()).thenReturn(new Header[]{new BasicHeader("test", "test")});
        when(response.getEntity()).thenReturn(entity);
        doNothing().when(response).close();
        when(closeableHttpClient.execute(any())).thenReturn(response);

        return kafkaConnectorUtil.getConnectorStatus(readyProbe);
    }

    @Test
    void ensureKafkaConnectorStatusWithNotOk() throws IOException {
        var responseBody = "{\"response\": \"test has some issue\"}";

        var result = getKafkaConnectorStatusFor(400, "Not OK");

        assertThat(result).isEqualTo(Optional.of(responseBody));
    }

}