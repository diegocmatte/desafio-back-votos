package com.example.desafiobackvotos.service;

import com.example.desafiobackvotos.domain.Agenda;
import com.example.desafiobackvotos.repository.AssemblyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssemblyService {

    @Autowired
    private AssemblyRepository assemblyRepository;

    @Transactional
    public void saveAgenda(Agenda agenda) {
        if(agenda.getSubject() != null) {
            assemblyRepository.save(agenda);
        }
    }
}
