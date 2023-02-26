package health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.io.IOException;
import java.util.Optional;

public class HealthChecker implements HealthIndicator {

    private static final Logger LOG = LoggerFactory.getLogger(HealthChecker.class);
    private static final String ERROR_KEY = "error";

    private final ConnectorUtil connectorUtil;

    public HealthChecker(ConnectorUtil connectorUtil) {
        this.connectorUtil = connectorUtil;
    }

    public Health health() {
        try {
            Optional<String> ready = connectorUtil.getConnectorStatus(ConnectorUtil.ProbeType.READY);
            if (!ready.isPresent()) {
                Optional<String> alive = connectorUtil.getConnectorStatus(ConnectorUtil.ProbeType.ALIVE);
                if (!alive.isPresent()) {
                    LOG.debug("kafkaconnector health is ok");
                    return Health.up().build();
                }
                LOG.debug("kafkaconnector health is not ok on alive");
                return Health.down()
                        .withDetail(ERROR_KEY, "cluster is not alive:" + alive.get())
                        .build();
            }
            LOG.debug("kafkaconnector health is not ok on ready");
            return Health.down()
                    .withDetail(ERROR_KEY, "cluster is not ready:" + ready.get())
                    .build();
        } catch (IOException e) {
            return Health.down()
                    .withDetail(ERROR_KEY, "cannot get state of kafkaconnector cluster:" + e.getMessage())
                    .build();
        }
    }
}
