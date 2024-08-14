package bg.softuni.bookstore.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dcyp9ocyf",
                "api_key", "517659666474199",
                "api_secret", "EVD38FWGrFAt5f2Mf21RDicVIHw"
        ));
    }
}