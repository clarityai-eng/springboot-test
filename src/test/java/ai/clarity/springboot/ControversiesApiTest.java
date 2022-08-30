package ai.clarity.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

import ai.clarity.testutil.ApiBasePath;
import ai.clarity.testutil.TestSetupMongoClient;
import com.mongodb.client.MongoClient;
import io.restassured.RestAssured;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
abstract public class ControversiesApiTest {

  @TestSetupMongoClient
  private MongoClient mongoClient;

  @ApiBasePath
  private String apiBasePath;

  @BeforeAll
  public void beforeAll() {
    Document controversy1 = new Document(Map.of("_id", "CON_AAPL_001", "companyId", "AAPL", "date", "20/05/2022", "title",
      "Apple, Samsung face new fines in Brazil for not shipping power adapters", "articleUrl",
      "https://www.gadgetsnow.com/tech-news/apple-samsung-face-new-fines-in-brazil-for-not-shipping-power-adapters/articleshow/91695291.cms",
      "severity", 5));
    Document controversy2 = new Document(Map.of("_id", "CON_AAPL_002", "companyId", "AAPL", "date", "30/10/2021", "title",
      "Top investor to grill Apple over 'slavery': L&G plans showdown as tech giant is accused of using parts made in Chinese labour camps",
      "articleUrl", "https://www.thisismoney.co.uk/money/markets/article-10147385/Top-investor-grill-Apple-slavery-L-G-plans-showdown.html",
      "severity", 3));
    var scores = mongoClient.getDatabase("test").getCollection("controversies");
    scores.drop();
    scores.insertMany(List.of(controversy1, controversy2));
  }

  @Test
  void companyControversies() {
    List<Object> companyControversies = RestAssured
      .given()
      .get(apiBasePath + "/companies/AAPL/controversies")
      .then()
      .log().ifValidationFails()
      .statusCode(200)
      .body("", hasSize(2))
      .extract().jsonPath()
      .getList("");

    assertThat(companyControversies).hasSize(2).contains(
      Map.of("id", "CON_AAPL_001", "companyId", "AAPL", "date", "20/05/2022", "title",
        "Apple, Samsung face new fines in Brazil for not shipping power adapters", "articleUrl",
        "https://www.gadgetsnow.com/tech-news/apple-samsung-face-new-fines-in-brazil-for-not-shipping-power-adapters/articleshow/91695291.cms",
        "severity", 5));
  }
}
