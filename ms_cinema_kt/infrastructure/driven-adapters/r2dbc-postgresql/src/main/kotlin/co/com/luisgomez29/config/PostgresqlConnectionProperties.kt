package co.com.luisgomez29.config

data class PostgresqlConnectionProperties(
    val host: String,
    val port: Int,
    val dbname: String,
    val schema: String,
    val username: String,
    val password: String
)
