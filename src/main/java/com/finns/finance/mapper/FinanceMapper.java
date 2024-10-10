package com.finns.finance.mapper;

import com.finns.finance.dto.Finance;
import com.finns.finance.dto.FinanceDTO;

import java.util.List;

public interface FinanceMapper {

    // 예금 상품을 가져오는 메서드
    List<FinanceDTO> getDepositProducts();

    // 적금 상품을 가져오는 메서드
    List<FinanceDTO> getinstallProducts();

    // 금융상품 한개 검색
    FinanceDTO selectOneProduct(Long no);

}
