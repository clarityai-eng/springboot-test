package ai.clarity.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import ai.clarity.testutil.ApiBasePath;
import ai.clarity.testutil.TestSetupDataSource;
import ai.clarity.testutil.TestSetupMongoClient;
import com.mongodb.client.MongoClient;
import io.restassured.RestAssured;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
abstract public class EsgApiTest {

  @TestSetupMongoClient
  private MongoClient mongoClient;

  @TestSetupDataSource
  private DataSource dataSource;

  @ApiBasePath
  private String apiBasePath;

  @BeforeAll
  public void beforeAll() throws SQLException {
    try (var con = dataSource.getConnection()) {
      var st = con.createStatement();
      st.executeUpdate("drop table if exists companies");
      st.executeUpdate("create table companies(id text, name text)");
      st.executeUpdate("insert into companies(id, name) values ('AAPL', 'Apple')");
      st.executeUpdate("insert into companies(id, name) values ('GOOGL', 'Google')");
    }
    Document apple = new Document(Map.of("_id", "AAPL", "environmental", 86, "social", 51, "governance", 79));
    Document google = new Document(Map.of("_id", "GOOGL", "environmental", 76, "social", 46, "governance", 59));
    var scores = mongoClient.getDatabase("test").getCollection("scores");
    scores.drop();
    scores.insertMany(List.of(apple, google));
  }

  @Test
  void companyEsgScore() {
    RestAssured
      .given()
      .get(apiBasePath + "/companies/AAPL/esg")
      .then()
      .log().ifValidationFails()
      .statusCode(200)
      .body("environmental", is(86))
      .body("social", is(51))
      .body("governance", is(79));
  }
}
