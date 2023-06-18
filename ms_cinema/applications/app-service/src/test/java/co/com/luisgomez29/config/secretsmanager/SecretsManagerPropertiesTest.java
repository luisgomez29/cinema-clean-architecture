package co.com.luisgomez29.config.secretsmanager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class SecretsManagerPropertiesTest {

    @Test
    void getSuccessProperties() {
        var properties = new SecretsManagerProperties(
                "http://localhost:4566",
                5,
                3600,
                "secretPostgres"
        );

        assertAll(
                "getSuccessProperties",
                () -> assertInstanceOf(SecretsManagerProperties.class, properties),
                () -> assertEquals("http://localhost:4566", properties.endpoint()),
                () -> assertEquals(5, properties.cacheSize()),
                () -> assertEquals(3600, properties.cacheTime()),
                () -> assertEquals("secretPostgres", properties.postgres())
        );
    }
}
