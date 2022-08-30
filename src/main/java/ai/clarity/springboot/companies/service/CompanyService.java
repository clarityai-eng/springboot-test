package ai.clarity.springboot.companies.service;

import ai.clarity.springboot.companies.model.Company;
import ai.clarity.springboot.companies.repository.CompanyRepository;
import java.util.List;

public class CompanyService {

  private final CompanyRepository companyRepository;

  public CompanyService(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  public List<Company> getAllCompanies() {
    return companyRepository.getCompanies();
  }

  //More service methods

}
