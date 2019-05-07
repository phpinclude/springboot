package my.springboot.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@PropertySource(value = {"classpath:/test0.prop"})
@ConfigurationProperties(prefix="test")
public class PropertyTest {
    private String name;
    private String color;
    
    public PropertyTest() {
        System.out.println("PropertyTest constructor call !!");
    }
}
