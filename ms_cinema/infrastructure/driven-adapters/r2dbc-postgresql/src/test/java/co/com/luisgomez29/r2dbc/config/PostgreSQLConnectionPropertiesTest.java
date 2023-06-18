package co.com.luisgomez29.r2dbc.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PostgreSQLConnectionPropertiesTest {

    @Test
    void getPropertiesSuccess() {
        var properties = new PostgresqlConnectionProperties(
                "localhost",
                5432,
                "dbName",
                "schema",
                "username",
                "password"
        );

        assertAll(
                "getPropertiesSuccess",
                () -> assertInstanceOf(PostgresqlConnectionProperties.class, properties),
                () -> assertEquals("localhost", properties.host()),
                () -> assertEquals(5432, properties.port()),
                () -> assertEquals("dbName", properties.dbname()),
                () -> assertEquals("schema", properties.schema()),
                () -> assertEquals("username", properties.username()),
                () -> assertEquals("password", properties.password())
        );
    }
}
