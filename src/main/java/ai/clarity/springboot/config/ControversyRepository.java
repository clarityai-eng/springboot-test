package ai.clarity.springboot.config;

import static com.mongodb.client.model.Filters.eq;

import ai.clarity.springboot.controversies.model.Controversy;
import com.mongodb.client.MongoClient;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class ControversyRepository {

  private final MongoClient mongoClient;

  public ControversyRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  public List<Controversy> findControversiesByCompanyId(String companyId) {
    return mongoClient.getDatabase("test").getCollection("controversies")
      .find(eq("companyId", companyId))
      .map(this::mapControversy)
      .into(new ArrayList<>());
  }

  private Controversy mapControversy(Document doc) {
    String id = doc.getString("_id");
    String companyId = doc.getString("companyId");
    String date = doc.getString("date");
    String title = doc.getString("title");
    String articleUrl = doc.getString("articleUrl");
    int severity = doc.getInteger("severity");
    return new Controversy(id, companyId, title, severity, date, articleUrl);
  }
}
