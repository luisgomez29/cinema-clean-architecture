package co.com.luisgomez29.r2dbc.config;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostgreSQLConnectionPoolTest {

    @InjectMocks
    private PostgreSQLConnectionPool connectionPool;

    @Mock
    private PostgresqlConnectionProperties properties;

    @Mock
    private PostgreSQLConnectionPoolProperties poolProperties;

    @BeforeEach
    void setUp() {
        when(properties.host()).thenReturn("localhost");
        when(properties.port()).thenReturn(5432);
        when(properties.dbname()).thenReturn("dbName");
        when(properties.schema()).thenReturn("schema");
        when(properties.username()).thenReturn("username");
        when(properties.password()).thenReturn("password");

        when(poolProperties.initialSize()).thenReturn(12);
        when(poolProperties.maxSize()).thenReturn(15);
        when(poolProperties.maxIdleTime()).thenReturn(10);
    }

    @Test
    void getConnectionConfigSuccess() {
        assertNotNull(connectionPool.getConnectionConfig(properties));
    }
}
