package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class MgnCashi implements Serializable {

    private String cashiAccNo ="";

    private String cashiCcy ="";

    private BigDecimal cashiAmt =BigDecimal.ZERO;

    private String cashiMgniId = "";


}
