package ai.clarity.testutil;

import ai.clarity.testutil.BaseApiTest.Configuration;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
@Import(Configuration.class)
public abstract class BaseApiTest {

  @Container
  static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:10.9");

  @Container
  static final MongoDBContainer mongo = new MongoDBContainer("mongo:3.6-xenial");

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
  }

  @TestConfiguration
  public static class Configuration {

    @Lazy
    @Bean("testSetupMongoClient")
    public MongoClient testSetupMongoClient() {
      return MongoClients.create(mongo.getReplicaSetUrl("test"));
    }

    @Lazy
    @Bean("testSetupDataSource")
    public DataSource testInitDataSource() {
      PGSimpleDataSource ds = new PGSimpleDataSource();
      ds.setUrl(postgres.getJdbcUrl());
      ds.setDatabaseName(postgres.getDatabaseName());
      ds.setUser(postgres.getUsername());
      ds.setPassword(postgres.getPassword());
      return ds;
    }
  }
}
