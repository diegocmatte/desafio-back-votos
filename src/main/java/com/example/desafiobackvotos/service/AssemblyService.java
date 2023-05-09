package com.example.desafiobackvotos.service;

import com.example.desafiobackvotos.domain.Agenda;
import com.example.desafiobackvotos.exception.NoSubjectFoundExcpetion;
import com.example.desafiobackvotos.repository.AssemblyRepository;
import com.example.desafiobackvotos.restclient.RestClient;
import com.example.desafiobackvotos.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssemblyService {

    @Autowired
    private AssemblyRepository assemblyRepository;
    @Autowired
    private RestClient restClient;

    @Transactional
    public Agenda saveAgenda(Agenda agenda) {
        if(agenda.getSubject() != null) {
            assemblyRepository.save(agenda);
        }
        throw new NoSubjectFoundExcpetion(Constants.NO_SUBJECT_FOUND);
    }

    public Boolean validateDocument(String document) {
        return restClient.validateDocument(document);
    }
}
