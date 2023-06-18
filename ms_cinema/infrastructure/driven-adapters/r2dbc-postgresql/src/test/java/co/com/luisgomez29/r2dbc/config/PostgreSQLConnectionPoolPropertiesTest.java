package co.com.luisgomez29.r2dbc.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PostgreSQLConnectionPoolPropertiesTest {
    @Test
    void getPropertiesSuccess() {
        var properties = new PostgreSQLConnectionPoolProperties(12, 15, 10);

        assertAll(
                "getPropertiesSuccess",
                () -> assertInstanceOf(PostgreSQLConnectionPoolProperties.class, properties),
                () -> assertEquals(12, properties.initialSize()),
                () -> assertEquals(15, properties.maxSize()),
                () -> assertEquals(10, properties.maxIdleTime())
        );

    }
}
