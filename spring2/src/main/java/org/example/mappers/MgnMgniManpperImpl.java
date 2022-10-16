package org.example.mappers;

import org.example.entity.MgnCashi;
import org.example.entity.MgnMgni;
import org.example.persistence.dto.request.AddMgnMgniRequest;
import org.example.persistence.dto.request.CreateMgnCashiRequest;
import org.example.persistence.dto.request.UpdateDataRequest;
import org.example.service.MgnMgniService;
import org.example.service.MgnMgniServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MgnMgniManpperImpl implements MgnMgniMapper {
    @Override
    public MgnMgni AddRequestToEntity(AddMgnMgniRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String mgniTime = LocalDateTime.now().format(formatter);
        LocalDateTime parseDate = LocalDateTime.parse(mgniTime, formatter);
        String mgniID = "MGI" + LocalDateTime.now().format(formatter);
        BigDecimal amt=BigDecimal.ZERO;
        for (CreateMgnCashiRequest createMgnCashiRequest:request.getCreateMgnCashiRequestList()){
            amt=amt.add(createMgnCashiRequest.getCashiAmt());
        }
        MgnMgni mgnmgni = MgnMgni.builder()
                .mgniId(mgniID)
                .mgniTime(parseDate)
                .mgniType(request.getMgniType())
                .mgniCmNo(request.getMgniCmNo())
                .mgniKacType(request.getMgniKacType())
                .mgniBankNo(request.getMgniBankNo())
                .mgniCcy(request.getMgniCcy())
                .mgniPvType(request.getMgniPVType())
                .mgniBicaccNo(request.getMgniBicaccNo())
                .mgniAmt(amt)
                .mgniIType(request.getMgniIType())
                .mgniCtName(request.getMgniCtName())
                .mgniCtTel(request.getMgniCtTel())
                .mgniStatus("1")
                .mgniUTime(parseDate)
                .build();


        return mgnmgni;
    }

    @Override
    public MgnMgni UpdateRequestToEntity(UpdateDataRequest request) {
        MgnMgniService mgnMgniService=new MgnMgniServiceImpl();
        MgnMgni mgnMgni=mgnMgniService.findById(request.getMgniId()).getMgnMgni();
        if (mgnMgni !=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String mgniTime = LocalDateTime.now().format(formatter);
            LocalDateTime parseDate = LocalDateTime.parse(mgniTime, formatter);
            BigDecimal amt=BigDecimal.ZERO;
            if (request.getMgniKacType().equals("2")){
                amt=request.getMgnCashiList().get(0).getCashiAmt();
            }else {
                for (CreateMgnCashiRequest createMgnCashiRequest:request.getMgnCashiList()){
                    for (MgnCashi mgnCashi : mgnMgni.getMgnCashiList() ) {
                        if (mgnCashi.getCashiAccNo().equals(createMgnCashiRequest.getCashiAccNo())){

                            amt=mgnMgni.getMgniAmt().subtract(mgnCashi.getCashiAmt().subtract(createMgnCashiRequest.getCashiAmt()));
                        }
                    }
                }
            }
            mgnMgni.setMgniType(request.getMgniType())
                    .setMgniCmNo(request.getMgniCmNo())
                    .setMgniKacType(request.getMgniKacType())
                    .setMgniBankNo(request.getMgniBankNo())
                    .setMgniCcy(request.getMgniCcy())
                    .setMgniPvType(request.getMgniPvType())
                    .setMgniBicaccNo(request.getMgniBicaccNo())
                    .setMgniAmt(amt)
                    .setMgniPReason(request.getMgniPReason())
                    .setMgniIType(request.getMgniIType())
                    .setMgniCtName(request.getMgniCtName())
                    .setMgniCtTel(request.getMgniCtTel())
                    .setMgniStatus("1")
                    .setMgniUTime(parseDate);
        }
        return mgnMgni;
    }

}
