package project.springData;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import project.springData.config.SpringDataConfig;
import project.springData.repository.OfficesRepository;

import java.math.BigDecimal;

/**
 * Created by Danylo on 16.03.2019
 */
public class MainSpring {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(SpringDataConfig.class);
        OfficesRepository officesRepository = context.getBean(OfficesRepository.class);

        officesRepository.findFirstByTargetAfter(BigDecimal.valueOf(400)).forEach(System.out::println);
        System.out.println("---------------------------------------");
        officesRepository.findAll().forEach(System.out::println);

        context.close();
    }
}
