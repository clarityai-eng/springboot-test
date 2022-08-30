package ai.clarity.springboot;

import ai.clarity.testutil.BaseApiTest;
import org.junit.jupiter.api.Nested;

public class ApiTest extends BaseApiTest {

  @Nested
  class Companies extends CompaniesApiTest {}

  @Nested
  class Esg extends EsgApiTest {}

  @Nested
  class Controversies extends ControversiesApiTest {}

}
