package com.java.tp.boat.rental.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java.tp.boat.rental.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> { }
