package fi.solita.henriny.lowcodecode.challenge.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.relational.core.mapping.NamingStrategy
import org.springframework.data.relational.core.mapping.PersistentPropertyPathExtension
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.support.JdbcTransactionManager
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty
import com.zaxxer.hikari.HikariDataSource

import com.zaxxer.hikari.HikariConfig

@Configuration
class JdbcConfig : AbstractJdbcConfiguration() {

    @Autowired
    lateinit var environment: Environment

    @Value("\${spring.datasource.url}")
    private lateinit var dbUrl: String

    @Bean
    fun postgresDataSource(): DataSource {
        val config = HikariConfig()
        config.jdbcUrl = dbUrl
        return HikariDataSource(config)
    }

    @Bean
    fun namingStrategy(): NamingStrategy {
        return object : NamingStrategy {
            override fun getReverseColumnName(path: PersistentPropertyPathExtension): String {
                return super.getReverseColumnName(path) + "_id"
            }
        }
    }

    @Bean
    fun namedParameterJdbcOperations(dataSource: DataSource): NamedParameterJdbcOperations? {
        return NamedParameterJdbcTemplate(dataSource)
    }

    @Bean
    fun transactionManager(dataSource: DataSource): PlatformTransactionManager {
        return JdbcTransactionManager(dataSource)
    }



}