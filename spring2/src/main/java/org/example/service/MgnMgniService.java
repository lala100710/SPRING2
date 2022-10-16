package org.example.service;

import org.example.entity.MgnMgni;
import org.example.persistence.dto.request.AddMgnMgniRequest;
import org.example.persistence.dto.request.UpdateDataRequest;
import org.example.persistence.dto.response.DataResponse;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface MgnMgniService {
    DataResponse create(AddMgnMgniRequest entity);

    DataResponse findById (String id);


    DataResponse update(UpdateDataRequest entity);

    DataResponse delete(String id);
}
