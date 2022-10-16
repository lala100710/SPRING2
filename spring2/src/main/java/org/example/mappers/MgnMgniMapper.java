package org.example.mappers;

import org.example.entity.MgnMgni;
import org.example.persistence.dto.request.AddMgnMgniRequest;
import org.example.persistence.dto.request.UpdateDataRequest;

public interface MgnMgniMapper {
    MgnMgni AddRequestToEntity(AddMgnMgniRequest addMgnMgniRequest);
    MgnMgni UpdateRequestToEntity(UpdateDataRequest updateDataRequest);

}
