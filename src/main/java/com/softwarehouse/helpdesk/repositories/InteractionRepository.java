package com.softwarehouse.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softwarehouse.helpdesk.models.Interaction;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {

}
