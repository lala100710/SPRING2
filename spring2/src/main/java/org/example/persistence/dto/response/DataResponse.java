package org.example.persistence.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.MgnMgni;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DataResponse {
    private String message="";

    private MgnMgni mgnMgni=null;
}
