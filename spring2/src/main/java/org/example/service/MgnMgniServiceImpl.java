package org.example.service;


import lombok.SneakyThrows;
import org.example.entity.MgnCashi;
import org.example.entity.MgnMgni;
import org.example.mappers.MgnCahiMapper;
import org.example.mappers.MgnCahiMapperImpl;
import org.example.mappers.MgnMgniManpperImpl;
import org.example.mappers.MgnMgniMapper;
import org.example.persistence.dao.MgnCashiDao;
import org.example.persistence.dao.MgnCashiImpl;
import org.example.persistence.dao.MgnMgniDao;
import org.example.persistence.dao.MgnMgniImpl;
import org.example.persistence.dto.request.AddMgnMgniRequest;
import org.example.persistence.dto.request.CreateMgnCashiRequest;
import org.example.persistence.dto.request.UpdateDataRequest;
import org.example.persistence.dto.response.DataResponse;
import org.example.util.ConnectManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MgnMgniServiceImpl implements MgnMgniService {
    private final MgnMgniDao mgnMgniDao = new MgnMgniImpl();

    private final MgnCashiDao mgnCashiDao = new MgnCashiImpl();
    private final MgnMgniMapper mgnMgniMapper = new MgnMgniManpperImpl();

    private final MgnCahiMapper mgnCahiMapper = new MgnCahiMapperImpl();

    private static final ConnectManager instance = ConnectManager.getInstance();
    private static final Connection CONNECTION = instance.getConnection();

    @SneakyThrows
    @Override
    public DataResponse create(AddMgnMgniRequest entity) {
        DataResponse dataResponse = new DataResponse();
        MgnMgni mgnMgni = mgnMgniMapper.AddRequestToEntity(entity);
        List<MgnCashi> mgnCashiList = mgnCahiMapper.AddRequestToEntity(entity.getCreateMgnCashiRequestList(), mgnMgni.getMgniId());

        try {
            CONNECTION.setAutoCommit(false);
            if (mgnMgni.getMgniKacType().equals("2") && mgnCashiList.size() > 1) {
                throw new Exception("交割金 明細檔只有一筆");
            } else {
                this.mgnMgniDao.save(mgnMgni, CONNECTION);
                for (MgnCashi mgnCashi : mgnCashiList) {
                    this.mgnCashiDao.save(mgnCashi, CONNECTION);
                }
                CONNECTION.commit();

                dataResponse.setMgnMgni(mgnMgni);
            }

        } catch (Exception e) {
            System.out.println("testing  新增失敗");
            System.out.println(e.getMessage());
            try {
                CONNECTION.rollback();
                dataResponse.setMgnMgni(null);
                dataResponse.setMessage(e.getMessage());

            } catch (SQLException exception) {

                System.out.println("rollback Error" + exception.getMessage());
            }
        }

        return dataResponse;
    }

    @Override
    public DataResponse findById(String id) {
        DataResponse dataResponse = mgnMgniDao.findById(id);
        if (dataResponse.getMgnMgni() != null) {
            MgnMgni mgnMgni = dataResponse.getMgnMgni();
            mgnMgni.setMgnCashiList(mgnCashiDao.findById(id));
            dataResponse.setMgnMgni(mgnMgni);
        }else {
            dataResponse.setMessage("查無此資料");
        }
        return dataResponse;
    }


    @SneakyThrows
    @Override
    public DataResponse update(UpdateDataRequest entity) {
        DataResponse dataResponse = new DataResponse();
        List<MgnCashi> mgnCashiList = mgnCahiMapper.AddRequestToEntity(entity.getMgnCashiList(), entity.getMgniId());
        try {
            CONNECTION.setAutoCommit(false);
            if (entity.getMgniKacType().equals("2")) {
                if (entity.getMgnCashiList().size() <= 1) {
                    mgnCashiDao.deleteAll(entity.getMgniId());
                    mgnCashiDao.save(mgnCashiList.get(0), CONNECTION);
                } else {
                    throw new SQLException("交割金的明細只會有一筆");
                }
            }


            MgnMgni mgnMgni = mgnMgniMapper.UpdateRequestToEntity(entity);
            if (mgnMgni != null) {
                for (MgnCashi mgnCashi : mgnCashiList) {
                    mgnCashiDao.update(mgnCashi,CONNECTION);
                }
                mgnMgniDao.update(mgnMgni, CONNECTION);

                CONNECTION.commit();
                dataResponse.setMgnMgni(mgnMgni);
            } else {
                throw new SQLException("查無此資料");
            }

        } catch (SQLException e) {
            dataResponse.setMessage("update data error " + e.getMessage());
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                System.out.println("error"+e.getMessage());
            }
        }

        return dataResponse;
    }

    @Override
    public DataResponse delete(String id) {
        DataResponse dataResponse = new DataResponse();
        if (null != findById(id).getMgnMgni()) {
            dataResponse.setMgnMgni(findById(id).getMgnMgni());
            mgnMgniDao.delete(id);
            mgnCashiDao.deleteAll(id);
        } else {
            dataResponse.setMessage("查無此資料");
        }
        return dataResponse;
    }
}
