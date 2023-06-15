package co.com.luisgomez29.r2dbc.config;

import lombok.Data;

@Data
public class PostgresqlConnectionProperties {
    private String dbname;
    private String schema;
    private String username;
    private String password;
    private String host;
    private Integer port;
}
