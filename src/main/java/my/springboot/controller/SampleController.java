package my.springboot.controller;

import java.awt.event.WindowFocusListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.pe.withwind.common.WindConfigure;
import kr.pe.withwind.exception.WindCommonException;
import my.springboot.domain.PropertyTest;
import my.springboot.domain.PropertyTest2;

@Controller
@EnableAutoConfiguration
public class SampleController {
    
    private final static Logger logger = LoggerFactory.getLogger(SampleController.class);
    
//    @Autowired
//    private PropertyTest2 pTest2;
    
    @Autowired
    private PropertyTest pTest;
    
//    @Autowired
//    private WindConfigure windConfig;

 
    @RequestMapping(value="/")
    @ResponseBody
    public String sampleHome() throws WindCommonException {
        
//        System.out.println(pTest2.toString());
//        System.out.println(pTest.toString());
//        
//        System.out.println(windConfig.getStrWithAes(WindConfigure.THEME_FILE_DIR));
        
        
        return "Hello Gradle! Hello Spring Boot!";
        
    }
    
    @RequestMapping(value="/test")
    public String sampleHome2() throws WindCommonException {
      
      
      return "Hello Gradle! Hello Spring Boot!";
      
  }
    
    public SampleController() {
        System.out.println("SampleController Constructor call");
    }
}
