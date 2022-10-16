package org.example.mappers;

import org.example.entity.MgnCashi;
import org.example.persistence.dto.request.CreateMgnCashiRequest;

import java.util.ArrayList;
import java.util.List;

public class MgnCahiMapperImpl implements MgnCahiMapper{
    @Override
    public List<MgnCashi> AddRequestToEntity(List<CreateMgnCashiRequest> createMgnCashiRequestList, String id) {
        List<MgnCashi> mgnCashiList=new ArrayList<>();
        for (CreateMgnCashiRequest mgnCashiRequest : createMgnCashiRequestList) {
            MgnCashi mgnCashi=MgnCashi.builder()
                    .cashiAccNo(mgnCashiRequest.getCashiAccNo())
                    .cashiAmt(mgnCashiRequest.getCashiAmt())
                    .cashiCcy(mgnCashiRequest.getCashiCcy())
                    .cashiMgniId(id)
                    .build();
            mgnCashiList.add(mgnCashi);
        }

        return mgnCashiList;
    }

    @Override
    public List<CreateMgnCashiRequest> MgnCahiListToCreateMgnCahiRequest(List<MgnCashi> mgnCashiList) {
        List<CreateMgnCashiRequest> createMgnCashiRequestList=new ArrayList<>();
        for (MgnCashi mgnCashi : mgnCashiList) {
            CreateMgnCashiRequest createMgnCashiRequest=new CreateMgnCashiRequest();
            createMgnCashiRequest.setCashiAccNo(mgnCashi.getCashiAccNo());
            createMgnCashiRequest.setCashiAmt(mgnCashi.getCashiAmt());
            createMgnCashiRequest.setCashiCcy(mgnCashi.getCashiCcy());
            createMgnCashiRequestList.add(createMgnCashiRequest);
        }
        return createMgnCashiRequestList;
    }
}
