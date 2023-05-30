package com.example.desafiobackvotos.repository;

import com.example.desafiobackvotos.domain.Poll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {


    boolean findByAssociateCpfAndAgendaSubject(String document, String agendaSubject);

}
