package dev.moozavar.crediting.adapter.jdbc;

import dev.moozavar.crediting.core.port.CustomerQuery;
import dev.moozavar.crediting.core.port.mapper.CustomerDto;
import dev.moozavar.crediting.core.port.mapper.LoanReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerQueryJdbc implements CustomerQuery {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<LoanReportDto> getLoanReport() {
        return jdbcTemplate
                .query("SELECT * FROM loan", loanToDto());
    }

    private RowMapper<CustomerDto> customerTtoDto() {
        return (rs, rowNum) -> CustomerDto.builder()
                .id(UUID.fromString(rs.getString("id")))
                .build();
    }

    private RowMapper<LoanReportDto> loanToDto() {
        return (rs, rowNum) -> LoanReportDto.builder()
                .id(UUID.fromString(rs.getString("id")))
                .build();
    }
}
