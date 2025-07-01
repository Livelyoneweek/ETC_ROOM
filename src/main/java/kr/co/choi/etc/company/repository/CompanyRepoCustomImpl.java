package kr.co.choi.etc.company.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.choi.etc.company.entity.Company;
import kr.co.choi.etc.company.entity.QCompany;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.co.choi.etc.company.entity.QCompany.company;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CompanyRepoCustomImpl implements CompanyRepoCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Company> useQueryDslForList() {
        log.info("### CompanyRepoCustomImpl.useQueryDslForList");

        return queryFactory
                .select(company)
                .from(company)
                .orderBy(company.id.desc())
                .fetch();
    }
}
