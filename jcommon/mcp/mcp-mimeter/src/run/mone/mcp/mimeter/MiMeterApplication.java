package run.mone.mcp.mimeter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"run.mone.mcp.ali.email"})
public class MiMeterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiMeterApplication.class, args);
    }

}