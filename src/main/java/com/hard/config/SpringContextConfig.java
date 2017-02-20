package com.hard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	SpringMvcConfig.class,
	SpringBeansConfig.class,
})
public class SpringContextConfig {
	
}