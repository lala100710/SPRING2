package org.example.persistence.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMgnCashiRequest {

    @NotBlank(message = "存入結算帳號不為空值")
    @Pattern(regexp = "^.{7}$", message = "存入結算帳號有誤")
    private String cashiAccNo;

    @NotNull(message = "金額不為空值")
    @Digits(integer = 20, fraction = 4, message = "金額格式不正確")
    private BigDecimal cashiAmt ;

    @NotNull(message = "幣別不為空值")
    private String cashiCcy ;



}
