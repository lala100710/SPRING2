package org.example.persistence.dao;

import org.example.entity.MgnMgni;
import org.example.persistence.dto.response.DataResponse;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface MgnMgniDao {

    MgnMgni save(MgnMgni entity, Connection connection);

    DataResponse findById(String id);

    Optional<MgnMgni> update(MgnMgni entity, Connection connection);

    public void delete(String id);



}
