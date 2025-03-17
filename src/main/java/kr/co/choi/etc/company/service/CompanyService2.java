package kr.co.choi.etc.company.service;

import kr.co.choi.etc.company.entity.Company;
import kr.co.choi.etc.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CompanyService2 {

    private final CompanyRepository companyRepository;
    private final CompanyService3 companyService3;

    public String findCompany2(UUID companyId) {
        log.info("### CompanyService2.findCompany2");
        Optional<Company> companyOp = companyRepository.findById(companyId);
        if (companyOp.isPresent()) {
            Company company = companyOp.get();
            log.info("### CompanyService2.findCompany2 name = {}", company.getName());

            companyService3.findCompany3(companyId);

            return company.getName();
        }
        return "";
    }

    public List<UUID> findCompanyIdList2() {
        log.info("### CompanyService2.findCompanyIdList2");

        List<UUID> list = companyRepository.findAll()
                .stream()
                .map(Company::getId)
                .toList();
        for (UUID uuid : list) {
//            log.info("### CompanyService2.findCompanyIdList2 uuid = {}", uuid);
        }
        return list;
    }


}
