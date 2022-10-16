package org.example.mappers;

import org.example.entity.MgnCashi;
import org.example.entity.MgnMgni;
import org.example.persistence.dto.request.AddMgnMgniRequest;
import org.example.persistence.dto.request.CreateMgnCashiRequest;

import java.util.List;

public interface MgnCahiMapper {
    List<MgnCashi> AddRequestToEntity(List<CreateMgnCashiRequest> createMgnCashiRequest, String id);

    List<CreateMgnCashiRequest> MgnCahiListToCreateMgnCahiRequest(List<MgnCashi> mgnCashiList);
}
