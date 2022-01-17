package my.education.iexcloudapidemo;

import my.education.iexcloudapidemo.model.Company;
import my.education.iexcloudapidemo.restclient.GetAllCompanyRestClientCommand;
import my.education.iexcloudapidemo.restclient.RestClientInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class IexCloudApiDemoApplication implements CommandLineRunner {

    private GetAllCompanyRestClientCommand getAllCompanyRestClientCommand;
    private RestClientInvoker restClientInvoker;

    public IexCloudApiDemoApplication(@Lazy GetAllCompanyRestClientCommand getAllCompanyRestClientCommand, RestClientInvoker restClientInvoker) {
        this.getAllCompanyRestClientCommand = getAllCompanyRestClientCommand;
        this.restClientInvoker = restClientInvoker;
    }

    public static void main(String[] args) {
        SpringApplication.run(IexCloudApiDemoApplication.class, args);
        new SpringApplicationBuilder(IexCloudApiDemoApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println();
        restClientInvoker.setRestClientCommand(getAllCompanyRestClientCommand);

        CompletableFuture<?> future = restClientInvoker.executeAsyncRequest();

        if(future.isDone()) {
            System.out.println();
            List<Company> o = (List<Company>)future.get();
            System.out.println();
        }
    }
}
