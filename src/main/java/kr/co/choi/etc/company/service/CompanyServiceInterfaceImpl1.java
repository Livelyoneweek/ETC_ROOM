package kr.co.choi.etc.company.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@Profile("production")
public class CompanyServiceInterfaceImpl1 implements CompanyServiceInterface {

    @Override
    public String findCompany(UUID companyId) {
        log.info("### CompanyServiceInterfaceImpl1.findCompany");
        return "1";
    }
}
