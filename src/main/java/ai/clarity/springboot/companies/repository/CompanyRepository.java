package ai.clarity.springboot.companies.repository;

import ai.clarity.springboot.companies.model.Company;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class CompanyRepository {

  private final JdbcTemplate jdbcTemplate;

  public CompanyRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Company> getCompanies() {
    return jdbcTemplate.query("select id, name from companies", this::mapCompany);
  }

  public Company findCompany(String id) {
    return jdbcTemplate.queryForObject("select id, name from companies where id = ?",
      new String[] {id}, new int[] {Types.VARCHAR}, this::mapCompany);
  }

  private Company mapCompany(ResultSet rs, int rowNum) throws SQLException {
    String id = rs.getString("id");
    String name = rs.getString("name");
    return new Company(id, name);
  }

}
