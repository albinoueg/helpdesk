package com.softwarehouse.helpdesk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.softwarehouse.helpdesk.models.Interaction;
import com.softwarehouse.helpdesk.models.Ticket;
import com.softwarehouse.helpdesk.models.User;
import com.softwarehouse.helpdesk.repositories.InteractionRepository;
import com.softwarehouse.helpdesk.repositories.TicketRepository;
import com.softwarehouse.helpdesk.repositories.UserRepository;

import java.util.Optional;

@Service
public class InteractionServiceImpl implements InteractionService {

	@Autowired
	private InteractionRepository interactionRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public InteractionServiceImpl(InteractionRepository interactionRepository, TicketRepository ticketRepository, UserRepository userRepository) {
		this.interactionRepository = interactionRepository;
		this.ticketRepository = ticketRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public Interaction create(Interaction interaction, Long ticketId) {
		Optional<Ticket> optional = this.ticketRepository.findById(ticketId);
		Ticket ticket = optional.get();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User userLogged = this.userRepository.findByEmail(username);
		
		interaction.setTicket(ticket);
		interaction.setUserInteraction(userLogged);
		
		return this.interactionRepository.save(interaction);
	}

	@Override
	public Boolean delete(Long id, Long ticketId) {
		Optional<Interaction> optional = this.interactionRepository.findById(id);
		Interaction interaction = optional.get();
		
		if (interaction != null) {
			this.interactionRepository.delete(interaction);
			return true;
		}
		
		return false;		
	}

}
