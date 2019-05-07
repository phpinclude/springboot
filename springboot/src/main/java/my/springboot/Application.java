package my.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan({"kr.pe.withwind","my.springboot"})

@ComponentScan({"my.springboot"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
//        new SpringApplicationBuilder(Application.class) 
//            .properties( "spring.config.location="
//                    + "classpath:/test0.prop"
//                    + ", file:./data, file:/data")
//            .run(args);

    }
    
    public Application() {
        System.out.println("Application constructor call!!");
    }
}