package dev.moozavar.crediting.core.port;

import dev.moozavar.crediting.core.port.mapper.LoanReportDto;

import java.util.List;

public interface CustomerQuery {
    List<LoanReportDto> getLoanReport();
}
