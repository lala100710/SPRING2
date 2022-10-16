package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class MgnMgni {

    private String mgniId = "";

    private LocalDateTime
            mgniTime = null;

    private String mgniType = "";


    private String mgniCmNo = "";


    private String mgniKacType = "";


    private String mgniBankNo = "";


    private String mgniCcy = "";


    private String mgniPvType = "";


    private String mgniBicaccNo = "";


    private String mgniIType = "";


    private String mgniPReason = "";


    private BigDecimal mgniAmt ;


    private String mgniCtName = "";


    private String mgniCtTel = "";


    private String mgniStatus = "";


    private LocalDateTime mgniUTime = null;


    private List<MgnCashi> mgnCashiList = null;

}

