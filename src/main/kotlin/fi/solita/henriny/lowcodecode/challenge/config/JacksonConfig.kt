package fi.solita.henriny.lowcodecode.challenge.config

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.format.DateTimeFormatter

@Configuration
class JacksonConfig {

    private val dateFormat = "yyyy-MM-dd";
    private val dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer? {
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            builder.simpleDateFormat(dateTimeFormat)
            builder.serializers(LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
            builder.serializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
        }
    }

}