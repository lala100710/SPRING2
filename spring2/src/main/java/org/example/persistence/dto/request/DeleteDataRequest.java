package org.example.persistence.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteDataRequest {
    @NotEmpty(message = "主檔ID不為空值")
    private String mgniId;

    private String cashiAccNo;

    private String cashiCcy;
}
