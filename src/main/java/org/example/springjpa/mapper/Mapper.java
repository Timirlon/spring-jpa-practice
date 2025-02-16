package org.example.springjpa.mapper;

import java.util.List;

public interface Mapper<T, R> {
    R toDto(T t);

    List<R> toDto(List<T> t);
}
