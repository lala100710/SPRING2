package org.example.persistence.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryMgnMgniRequest implements Serializable {

    private String mgniId="";

//    private String mgniCmNo="";
//
//    private String mgniBankNo="";
//
//    private String mgniKacType="";
//
//    private String mgniCtName="";
//
//    private String mgniCtTel="";
//
//    private BigDecimal mgniAmt=BigDecimal.ZERO;
//
//    private String mgniTime;


}
