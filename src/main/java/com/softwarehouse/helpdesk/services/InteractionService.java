package com.softwarehouse.helpdesk.services;

import com.softwarehouse.helpdesk.models.Interaction;

public interface InteractionService {
	
	public Interaction create(Interaction interaction, Long ticketId);
	public Boolean delete(Long id, Long ticketId);
}
