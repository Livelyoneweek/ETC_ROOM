package kr.co.choi.etc.company.service;

import jakarta.annotation.PostConstruct;
import kr.co.choi.etc.company.dto.CompanyDto;
import kr.co.choi.etc.company.entity.Company;
import kr.co.choi.etc.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyService2 companyService2;

    public String findCompany(UUID companyId) {
        log.info("### CompanyService.findCompany");

        Optional<Company> companyOp = companyRepository.findById(companyId);
        if (companyOp.isPresent()) {
            Company company = companyOp.get();
            log.info("### CompanyService.findCompany name = {}", company.getName());

            companyService2.findCompany2(companyId);

            return company.getName();
        }
        return "";
    }

    public List<UUID> findCompanyIdList() {
        log.info("### CompanyService.findCompanyIdList");
        return companyRepository.findAll()
                .stream()
                .map(Company::getId)
                .toList();
    }

    @PostConstruct
    public void initData() {
        log.info("### CompanyService.initData");
        // 기존 데이터가 없을 경우에만 추가
        if (companyRepository.count() == 0) {
            List<Company> companies = new ArrayList<>();
            companies.add(new Company("name1"));
            companies.add(new Company("name2"));
            companies.add(new Company("name3"));
            companies.add(new Company("name4"));
            companies.add(new Company("name5"));

            companyRepository.saveAll(companies);
        }
    }

    public List<UUID> findCompanyIdListUseDsl() {
        log.info("### CompanyService.findCompanyIdListUseDsl");
        return companyRepository.useQueryDslForList()
                .stream()
                .map(Company::getId)
                .toList();
    }

    public List<CompanyDto.Query.CompanyBasic> findCompanyIdListUseDslWithRecord() {
        log.info("### CompanyService.findCompanyIdListUseDslWithRecord");
        return companyRepository.useQueryDslForListWithRecord();

    }
}
