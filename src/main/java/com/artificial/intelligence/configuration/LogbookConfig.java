package com.artificial.intelligence.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.Conditions;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.json.JsonHttpLogFormatter;

@Configuration
public class LogbookConfig {
    @Bean
    public Logbook logbook() {
        return Logbook.builder()
                .condition(Conditions.exclude())
                .sink(new DefaultSink(
                        new JsonHttpLogFormatter(),
                        new DefaultHttpLogWriter()
                ))
                .build();
    }
}
