package com.myimdb.searchbook.config;

import com.myimdb.searchbook.model.NewTypeOfBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    @ConditionalOnProperty(
         prefix = "keyP1",
         value="keyP2",
        havingValue = "MYSQl",
        matchIfMissing = false
    )
    public NewTypeOfBean newTypeOfBean() {
        return new NewTypeOfBean();
    }
}
