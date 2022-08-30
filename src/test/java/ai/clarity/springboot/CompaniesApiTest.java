package ai.clarity.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

import ai.clarity.testutil.ApiBasePath;
import ai.clarity.testutil.TestSetupDataSource;
import io.restassured.RestAssured;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
abstract public class CompaniesApiTest {

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
  }

  @Test
  void companies() {
    List<Object> companies = RestAssured
      .given()
      .get(apiBasePath + "/companies")
      .then()
      .log().ifValidationFails()
      .statusCode(200)
      .body("", hasSize(2))
      .extract().jsonPath()
      .getList("");
    assertThat(companies.get(0)).isEqualTo(Map.of("id", "AAPL", "name", "Apple"));
    assertThat(companies.get(1)).isEqualTo(Map.of("id", "GOOGL", "name", "Google"));
    assertThat(companies).hasSize(2)
      .contains(Map.of("id", "AAPL", "name", "Apple"), Map.of("id", "GOOGL", "name", "Google"));
  }
}
