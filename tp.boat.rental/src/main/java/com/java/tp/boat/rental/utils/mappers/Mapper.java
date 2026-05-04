package com.java.tp.boat.rental.utils.mappers;


public interface Mapper<Model, Entity, RequestCreation, RequestUpdate, Response> {
    Model toDomainFromRequestCreation(RequestCreation requestCreation);
    Model toDomainFromRequestUpdate(RequestUpdate requestUpdate);
    Model toDomainFromEntity(Entity entity);
    Entity toEntityFromDomain(Model model);
    Response toResponseFromDomain(Model model);
    Response toResponseFromEntity(Entity entity);
    RequestCreation toRequestCreationFromRequestUpdate(RequestUpdate requestUpdate);
}
