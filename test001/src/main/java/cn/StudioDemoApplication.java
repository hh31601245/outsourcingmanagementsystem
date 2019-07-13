package cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@Controller
public class StudioDemoApplication extends WebMvcConfigurerAdapter {
    @RequestMapping("/")  
    public String greeting(ModelMap map) {  
        String jreVersion = System.getProperty("java.specification.version");
        map.addAttribute("jreVersion", "v" + jreVersion);
        return "index";
    }
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

	public static void main(String[] args) {
		SpringApplication.run(StudioDemoApplication.class, args);
	}
}
