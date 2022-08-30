package ai.clarity.springboot.config;

import ai.clarity.springboot.companies.repository.CompanyRepository;
import ai.clarity.springboot.esg.repository.ESGScoreRepository;
import ai.clarity.springboot.companies.service.CompanyService;
import ai.clarity.springboot.controversies.service.ControversyService;
import ai.clarity.springboot.esg.service.ESGService;
import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfig {

  @Bean
  public CompanyRepository companyRepository(JdbcTemplate jdbcTemplate) {
    return new CompanyRepository(jdbcTemplate);
  }

  @Bean
  public ESGScoreRepository companyScoreRepository(MongoClient mongoClient) {
    return new ESGScoreRepository(mongoClient);
  }

  @Bean
  public CompanyService companyService(CompanyRepository companyRepository) {
    return new CompanyService(companyRepository);
  }

  @Bean
  public ESGService esgService(CompanyRepository companyRepository, ESGScoreRepository companyScoreRepository) {
    return new ESGService(companyRepository, companyScoreRepository);
  }

  @Bean
  public ControversyService controversyService(MongoClient mongoClient) {
    return new ControversyService(new ControversyRepository(mongoClient));
  }
}
