package ai.clarity.springboot.esg.service;

import ai.clarity.springboot.companies.model.Company;
import ai.clarity.springboot.esg.model.CompanyScore;
import ai.clarity.springboot.esg.model.Score;
import ai.clarity.springboot.companies.repository.CompanyRepository;
import ai.clarity.springboot.esg.repository.ESGScoreRepository;

public class ESGService {

  private final CompanyRepository companyRepository;
  private final ESGScoreRepository esgScoreRepository;

  public ESGService(CompanyRepository companyRepository, ESGScoreRepository esgScoreRepository) {
    this.companyRepository = companyRepository;
    this.esgScoreRepository = esgScoreRepository;
  }

  public CompanyScore getCompanyScore(String id) {
    Company company = companyRepository.findCompany(id);
    Score score = esgScoreRepository.findCompanyScore(id);
    return new CompanyScore(company.id(), company.name(), score.environmental(), score.social(), score.governance());
  }

  //More service methods

}
