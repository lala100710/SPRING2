package org.example.persistence.dao;

import org.example.entity.MgnCashi;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface MgnCashiDao {
    List<MgnCashi> findById(String id);

    MgnCashi save(MgnCashi entity, Connection connection);

    Optional<MgnCashi> update(MgnCashi entity, Connection connection);

    void deleteAll(String id);

    void deleteByAccAndCct(String acc,String ccy,String id);

}
