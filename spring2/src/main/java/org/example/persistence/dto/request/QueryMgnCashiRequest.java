package org.example.persistence.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.MgnMgni;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryMgnCashiRequest {

    private String cashiAccNo = "";

    private String cashiCcy = "";


    private BigDecimal cashiAmt = BigDecimal.ZERO;

    private MgnMgni cashiMgniId;
}
