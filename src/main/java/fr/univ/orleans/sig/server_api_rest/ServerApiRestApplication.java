package fr.univ.orleans.sig.server_api_rest;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApiRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApiRestApplication.class, args);
    }

//    @Bean
//    public JtsModule jtsModule()
//    {
//        return new JtsModule();
//    }

}
