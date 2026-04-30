package com.java.tp.boat.rental.utils.mappers;

public interface Mapper<Model, Entity, RequestCreation, RequestUpdate, Response> {
    Model toDomainFromRequestCreation(RequestCreation requestCreation) throws Exception;
    Model toDomainFromEntity(Entity entity) throws Exception;
    Entity toEntityFromDomain(Model model);
    Response toResponseFromDomain(Model model);
    Response toResponseFromEntity(Entity entity);
    RequestCreation toRequestCreationFromRequestUpdate(RequestUpdate requestUpdate);
    
    
}
