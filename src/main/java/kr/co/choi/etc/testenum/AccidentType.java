package kr.co.choi.etc.testenum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
@Slf4j
public enum AccidentType {
    FALL("FALL","떨어짐", Arrays.asList("default","t1", "sb1")), //추락, 떨어짐
    STUCK("STUCK","끼임", Arrays.asList("default","t1", "sb1")), //끼임
    HIT("HIT","부딪힘", Arrays.asList("default","t1", "sb1")), //부딪힘
    CRUMBLING("CRUMBLING","무너짐", Arrays.asList("default","t1", "sb1")), //무너짐
    HIT_BY_OBJECT("HIT_BY_OBJECT","물체에 맞음", Arrays.asList("default","t1", "sb1")), //물체에 맞음
    TRAPPED("TRAPPED","깔림", Arrays.asList("default","t1", "sb1")), //깔림


    ELECTRIC_SHOCK("ELECTRIC_SHOCK","감전", Arrays.asList("default","sb1")), //감전
    FIRE_AND_EXPLOSION("FIRE_AND_EXPLOSION","화재 및 폭발", Arrays.asList("default","sb1")), //화재 및 폭발
    CUTTING("CUTTING", "절단/베임/찔림", Arrays.asList("default","sb1")), // 절단/베임/찔림
    FALLING("FALLING", "넘어짐", Arrays.asList("default","sb1")), // 넘어짐
    ETC("ETC","기타", Arrays.asList("default","sb1")), //기타

    ALL("ALL","ALL", Arrays.asList("default")); //온리 조회용

    private String type;
    private String kor;
    private final List<String> tenantList;

    // tenantId로 해당 AccidentType이 유효한지 확인
    public boolean isApplicableToTenant(String tenantId) {
        return tenantList.contains(tenantId);
    }

    /**
     * tenantId와 type으로 특정 AccidentType 찾기, tenantId 없으면 default로 폴백
     */
    public static AccidentType findByCodeAndTenant(String type, String tenantId) {

        return Arrays.stream(values())
                .filter(accidentType -> accidentType.getType().equals(type) && accidentType.isApplicableToTenant(tenantId))
                .findFirst()
                .orElseGet(() ->
                    Arrays.stream(values())
                            .filter(accidentType -> accidentType.getType().equals(type) && accidentType.isApplicableToTenant("default"))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException(
                                    String.format("AccidentType '%s' is not valid for tenant '%s' or default", type, tenantId)))
                );
    }

    /**
     * tenantId로 유효한 AccidentType 목록 반환, tenantId 없으면 default 로 조회 후 반환
     */
    public static List<AccidentType> getByTenantId(String tenantId) {
        List<AccidentType> result = Arrays.stream(values())
                .filter(accidentType -> accidentType.isApplicableToTenant(tenantId))
                .toList();

        // 결과가 비어있으면 default로 재시도
        if (result.isEmpty() ) {
            return Arrays.stream(values())
                    .filter(accidentType -> accidentType.isApplicableToTenant("default"))
                    .toList();
        }

        return result;
    }


    public static AccidentType findForQuery(String name) {
        for (AccidentType accidentType : AccidentType.values()) {
            if (accidentType.type.contains(name)) {
                return accidentType;
            }
        }
        return null;
    }
}
