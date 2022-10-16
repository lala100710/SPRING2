package org.example.persistence.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMgnMgniRequest {

    @NotBlank(message = "存入類型不為空值")
    @Pattern(regexp = "^.$", message = "存入類型長度必須為1")
    private String mgniType;

    @NotBlank(message = "結算會員代號不為空值")
    @Pattern(regexp = "^.{7}$", message = "結算會員代號有誤")
    private String mgniCmNo;

    @NotBlank(message = "存入銀行結算代碼不為空值")
    @Pattern(regexp = "^.{3}$", message = "結算會員代號有誤")
    private String mgniBankNo;

    @NotBlank(message = "存入保管專戶別不為空值")
    @Pattern(regexp = "^[1-2]$", message = "存入保管專戶有誤")
    private String mgniKacType;

    @NotBlank(message = "存入幣別不為空值")
    @Pattern(regexp = "^.{3}$", message = "存入幣別有誤")
    private String mgniCcy;

    @NotBlank(message = "存入方式不為空值")
    @Pattern(regexp = "^[1-2]$", message = "存入幣別有誤")
    private String mgniPVType;

    @NotBlank(message = "存放帳號類型不為空值")
    private String mgniBicaccNo;

    @Pattern(regexp = "^[1-4]$", message = "存入類別有誤")
    private String mgniIType;

    @Pattern(regexp = "^.{3}$", message = "存入實體帳號原因有誤")
    private String mgniPReason;

    @NotBlank(message = "聯絡人姓名不為空值")
    private String mgniCtName;

    @NotBlank(message = "聯絡人電話不為空值")
    private String mgniCtTel;

    //客製化validation
    @NotNull(message = "現金明細檔不為空值")
    private List<@Valid CreateMgnCashiRequest> createMgnCashiRequestList;

}
