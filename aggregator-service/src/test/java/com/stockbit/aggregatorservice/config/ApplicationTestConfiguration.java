package com.stockbit.aggregatorservice.config;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({
        ApplicationConfiguration.class,
        PersistenceConfiguration.class,
        ServiceConfiguration.class,
        TwitterConfiguration.class
})
@ComponentScan(basePackages = "com.mekari.aggregatorservice")
public class ApplicationTestConfiguration {
}
