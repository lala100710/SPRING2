package org.example.persistence.dao;

import lombok.SneakyThrows;
import org.example.entity.MgnCashi;
import org.example.util.ConnectManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MgnCashiImpl implements MgnCashiDao {
    @SneakyThrows
    @Override
    public MgnCashi save(MgnCashi entity, Connection connection) {
        final String SQL = "INSERT INTO mgn_cashi VALUES(?,?,?,?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, entity.getCashiAccNo());
            preparedStatement.setString(2, entity.getCashiCcy());
            preparedStatement.setString(3, entity.getCashiMgniId());
            preparedStatement.setBigDecimal(4, entity.getCashiAmt());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error" + e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return entity;
    }

    @Override
    public List<MgnCashi> findById(String s) {
        ConnectManager instance = ConnectManager.getInstance();
        Connection connection = instance.getConnection();
        String sql = "SELECT * FROM mgn_cashi WHERE CASHI_MgniId=?";
        List<MgnCashi> mgnCashiList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, s);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MgnCashi mgnCashi=MgnCashi.builder()
                            .cashiAccNo(resultSet.getString(1))
                            .cashiCcy(resultSet.getString(2))
                            .cashiMgniId(resultSet.getString(3))
                            .cashiAmt(resultSet.getBigDecimal(4))
                            .build();

                    mgnCashiList.add(mgnCashi);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return mgnCashiList;
}


    @SneakyThrows
    @Override
    public Optional<MgnCashi> update(MgnCashi entity, Connection connection) {
        String sql = "UPDATE mgn_cashi " +
                "SET CASHI_CCY=? ,CASHI_AMT=? WHERE  CASHI_ACC_NO =? AND CASHI_MgniId=? ";
        System.out.println("cashi "+entity.getCashiAmt());
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(3,entity.getCashiAccNo());
            preparedStatement.setString(1,entity.getCashiCcy());
            preparedStatement.setBigDecimal(2,entity.getCashiAmt());
            preparedStatement.setString(4,entity.getCashiMgniId());
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("updated Entity"+entity);
                return Optional.of(entity);
            }
        }catch (SQLException ex) {
            System.out.println("cashi"+ex.getMessage());
            throw new SQLException(ex.getMessage());

        }
        return Optional.empty();
    }


    @Override
    public void deleteAll(String id) {

        ConnectManager instance = ConnectManager.getInstance();
        Connection connection = instance.getConnection();
        String sql = "DELETE  FROM mgn_cashi WHERE CASHI_MgniId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteByAccAndCct(String acc, String ccy,String id) {
        ConnectManager instance = ConnectManager.getInstance();
        Connection connection = instance.getConnection();
        String sql = "DELETE  FROM mgn_cashi WHERE CASHI_MgniId=? AND CASHI_ACC_NO=? AND CASHI_CCY=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, acc);
            preparedStatement.setString(2,ccy);
            preparedStatement.setString(3,id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
