package project.springData.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * Created by Danylo on 19.03.2019
 */

@EnableWebMvc
@Configuration
@ComponentScans({@ComponentScan("project.controller"),@ComponentScan("project.springData.config"),@ComponentScan("project.springData.exception")})
public class MainMvcConfig extends AnnotationConfigWebApplicationContext {
}
