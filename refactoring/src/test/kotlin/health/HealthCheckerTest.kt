package health

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.Status
import java.io.IOException
import java.util.Optional

class HealthCheckerTest {
    private var NOK = "NOK"
    private var READY = "READY"

    @Throws(IOException::class)
    private fun mockKafkaConnectorUtil(readyAnswer: Optional<String>, aliveAnswer: Optional<String>): ConnectorUtil {
        val kafkaConnectorUtil = Mockito.mock(ConnectorUtil::class.java)
        Mockito.`when`(kafkaConnectorUtil.getConnectorStatus(ConnectorUtil.ProbeType.READY)).thenReturn(readyAnswer)
        Mockito.`when`(kafkaConnectorUtil.getConnectorStatus(ConnectorUtil.ProbeType.ALIVE)).thenReturn(aliveAnswer)
        return kafkaConnectorUtil
    }

    @Test
    @Throws(IOException::class)
    fun kafkaConnectorHealthOkTest() {
        val healthChecker = HealthChecker(mockKafkaConnectorUtil(Optional.empty(), Optional.empty()))
        val healthResult = healthChecker.health()
        assertThat(healthResult.status).isEqualTo(Status.UP)
    }

    private fun verifyHealthIsInError(healthResult: Health, expectedError: String) {
        assertThat(healthResult.status).isEqualTo(Status.DOWN)
        assertThat(healthResult.details["error"].toString()).isEqualTo(expectedError)
    }

    @Test
    @Throws(IOException::class)
    fun kafkaConnectorNokTest() {
        val healthChecker = HealthChecker(mockKafkaConnectorUtil(Optional.empty(), Optional.of(NOK)))
        val healthResult = healthChecker.health()
        val expectedError = "cluster is not alive:$NOK"
        verifyHealthIsInError(healthResult, expectedError)
    }

    @Test
    @Throws(IOException::class)
    fun kafkaConnectorNotReadyTest() {
        val healthChecker = HealthChecker(mockKafkaConnectorUtil(Optional.of(READY), Optional.of(NOK)))
        val healthResult = healthChecker.health()
        val expectedError = "cluster is not ready:$READY"
        verifyHealthIsInError(healthResult, expectedError)
    }

    @Test
    @Throws(IOException::class)
    fun kafkaConnectorNotReachableTest() {
        val kafkaConnectorUtil = Mockito.mock(ConnectorUtil::class.java)
        Mockito.`when`(kafkaConnectorUtil.getConnectorStatus(ConnectorUtil.ProbeType.READY))
            .thenThrow(IOException(NOK))
        val healthResult = HealthChecker(kafkaConnectorUtil).health()
        verifyHealthIsInError(healthResult, "cannot get state of kafkaconnector cluster:$NOK")
    }

}