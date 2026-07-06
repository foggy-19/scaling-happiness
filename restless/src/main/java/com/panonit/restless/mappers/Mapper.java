package com.panonit.restless.mappers;

public interface Mapper<A, B> {

    B mapToDto(A a);

    A mapToEntity(B b);
}
