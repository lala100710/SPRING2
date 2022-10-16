package org.example.persistence.dao;


import lombok.SneakyThrows;
import org.example.entity.MgnMgni;
import org.example.persistence.dto.response.DataResponse;
import org.example.util.ConnectManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class MgnMgniImpl implements MgnMgniDao {

    @SneakyThrows
    @Override
    public MgnMgni save(MgnMgni entity, Connection connection) {
        final String SQL = "INSERT INTO mgn_mgni VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, entity.getMgniId());
            preparedStatement.setBigDecimal(2, entity.getMgniAmt());
            preparedStatement.setString(3, entity.getMgniBankNo());
            preparedStatement.setString(4, entity.getMgniBicaccNo());
            preparedStatement.setString(5, entity.getMgniCcy());
            preparedStatement.setString(6, entity.getMgniCmNo());
            preparedStatement.setString(7, entity.getMgniCtName());
            preparedStatement.setObject(8, entity.getMgniCtTel());
            preparedStatement.setString(9, entity.getMgniIType());
            preparedStatement.setObject(10, entity.getMgniKacType());
            preparedStatement.setObject(11, entity.getMgniPReason());
            preparedStatement.setObject(12, entity.getMgniPvType());
            preparedStatement.setString(13, entity.getMgniStatus());
            preparedStatement.setObject(14, entity.getMgniTime());
            preparedStatement.setString(15, entity.getMgniType());
            preparedStatement.setObject(16, entity.getMgniUTime());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error" + e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return entity;
    }


    @Override
    public DataResponse findById(String id) {
        ConnectManager instance = ConnectManager.getInstance();
        Connection connection = instance.getConnection();
        DataResponse dataResponse = new DataResponse();

        String sql = "SELECT * FROM mgn_mgni WHERE MGNI_ID=?";
        MgnMgni mgnMgni = null;
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    mgnMgni = MgnMgni.builder().
                            mgniId(resultSet.getString(1))
                            .mgniAmt(resultSet.getBigDecimal(2))
                            .mgniBankNo(resultSet.getString(3))
                            .mgniBicaccNo(resultSet.getString(4))
                            .mgniCcy(resultSet.getString(5))
                            .mgniCmNo(resultSet.getString(6))
                            .mgniCtName(resultSet.getString(7))
                            .mgniCtTel(resultSet.getString(8))
                            .mgniIType(resultSet.getString(9))
                            .mgniKacType(resultSet.getString(10))
                            .mgniPReason(resultSet.getString(11))
                            .mgniPvType(resultSet.getString(12))
                            .mgniStatus(resultSet.getString(13))
                            .mgniTime((LocalDateTime) resultSet.getObject(14))
                            .mgniType(resultSet.getString(15))
                            .mgniUTime((LocalDateTime) resultSet.getObject(16))
                            .build();

                }

            }
        } catch (SQLException e) {
            System.out.println("error findById " + e.getMessage());
            dataResponse.setMessage("error findById " + e.getMessage());
        }
        dataResponse.setMgnMgni(mgnMgni);
        return dataResponse;
    }


    public Optional<MgnMgni> update(MgnMgni entity, Connection connection) {
        String sql = "UPDATE mgn_mgni " +
                "SET MGNI_BANK_NO =? , MGNI_BICACC_NO=? ,MGNI_CCY=?,MGNI_CM_NO=?,MGNI_CT_NAME=?," +
                "MGNI_CT_TEL=?,MGNI_I_TYPE=?,MGNI_KAC_TYPE=?,MGNI_P_REASON=?,MGNI_PV_TYPE=?," +
                "MGNI_STATUS=?,MGNI_TYPE=?" + ", MGNI_AMT=?" + ", MGNI_U_TIME=?" +
                "WHERE MGNI_ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


            preparedStatement.setString(1, entity.getMgniBankNo());
            preparedStatement.setString(2, entity.getMgniBicaccNo());
            preparedStatement.setString(3, entity.getMgniCcy());
            preparedStatement.setString(4, entity.getMgniCmNo());
            preparedStatement.setString(5, entity.getMgniCtName());
            preparedStatement.setObject(6, entity.getMgniCtTel());
            preparedStatement.setString(7, entity.getMgniIType());
            preparedStatement.setObject(8, entity.getMgniKacType());
            preparedStatement.setObject(9, entity.getMgniPReason());
            preparedStatement.setObject(10, entity.getMgniPvType());
            preparedStatement.setString(11, entity.getMgniStatus());
            preparedStatement.setString(12, entity.getMgniType());
            preparedStatement.setBigDecimal(13, entity.getMgniAmt());
            preparedStatement.setString(14, String.valueOf(entity.getMgniUTime()));
            preparedStatement.setString(15, entity.getMgniId());
            preparedStatement.executeUpdate();
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("updated Entity" + entity);
                return Optional.of(entity);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void delete(String id) {
        ConnectManager instance = ConnectManager.getInstance();
        Connection connection = instance.getConnection();
        String sql = "DELETE  FROM mgn_mgni WHERE MGNI_ID=?";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
