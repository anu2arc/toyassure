package com.increff.assure;


import com.increff.assure.spring.SpringConfig;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(//
		basePackages = { "com.increff.assure" }, //
		excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, value = { SpringConfig.class })//
)
@PropertySources({ //
		@PropertySource(value = "classpath:./test.properties", ignoreResourceNotFound = true) //
})
public class QaConfig {

}
