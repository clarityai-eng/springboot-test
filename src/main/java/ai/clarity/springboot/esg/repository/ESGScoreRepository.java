package ai.clarity.springboot.esg.repository;

import static com.mongodb.client.model.Filters.eq;

import ai.clarity.springboot.esg.model.Score;
import com.mongodb.client.MongoClient;
import org.bson.Document;

public class ESGScoreRepository {

  private MongoClient mongoClient;

  public ESGScoreRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  public Score findCompanyScore(String id) {
    return mongoClient.getDatabase("test").getCollection("scores")
      .find(eq("_id", id))
      .map(this::mapCompanyScore)
      .first();
  }

  private Score mapCompanyScore(Document doc) {
    String id = doc.getString("_id");
    int environmental = doc.getInteger("environmental");
    int social = doc.getInteger("social");
    int governance = doc.getInteger("governance");
    return new Score(id, environmental, social, governance);
  }

}
