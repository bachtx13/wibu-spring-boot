package com.bachtx.mangaservice.services;

import java.util.List;
import java.util.UUID;

public interface IBaseCRUDService<R, C, U> {
    R create(C payload);
    R update(UUID id, U payload);
    R updateStatus(UUID id, boolean status);
    R getById(UUID id);
    List<R> getAll();
}
