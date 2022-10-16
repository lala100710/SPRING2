package org.example.persistence.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDataRequest {

    @NotEmpty(message = "主檔ID不為空值")
    private String mgniId;

    @Pattern(regexp = "^.$", message = "存入類型長度必須為1")
    private String mgniType;


    @Pattern(regexp = "^.{7}$", message = "結算會員代號有誤")
    private String mgniCmNo;


    @Pattern(regexp = "^.{3}$", message = "結算會員代號有誤")
    private String mgniBankNo;


    @Pattern(regexp = "^.$", message = "存入保管專戶有誤")
    private String mgniKacType;


    @Pattern(regexp = "^.{3}$", message = "存入幣別有誤")
    private String mgniCcy;


    private String mgniPvType;


    private String mgniBicaccNo;

    @Pattern(regexp = "^.$", message = "存入類別")
    private String mgniIType;

    private String mgniPReason;


    private String mgniCtName;

    private String mgniCtTel;

    private List<@Valid CreateMgnCashiRequest> mgnCashiList;

}
