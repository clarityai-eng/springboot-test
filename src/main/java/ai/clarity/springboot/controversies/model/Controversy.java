package ai.clarity.springboot.controversies.model;

public record Controversy(String id, String companyId, String title, int severity, String date, String articleUrl) {

}
