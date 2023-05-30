package com.example.desafiobackvotos.service;

import com.example.desafiobackvotos.domain.Agenda;
import com.example.desafiobackvotos.domain.Associate;
import com.example.desafiobackvotos.domain.Poll;
import com.example.desafiobackvotos.exception.NoDocumentFoundException;
import com.example.desafiobackvotos.exception.NoSubjectFoundException;
import com.example.desafiobackvotos.repository.AgendaRepository;
import com.example.desafiobackvotos.repository.AssociateRepository;
import com.example.desafiobackvotos.repository.PollRepository;
import com.example.desafiobackvotos.restclient.RestClient;
import com.example.desafiobackvotos.util.Constants;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssemblyService {

    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private AssociateRepository associateRepository;
    @Autowired
    private RestClient restClient;

    @Transactional
    public void saveAssociate(Associate associate){
        if(StringUtils.isNotBlank(associate.getCpf())){
            associateRepository.save(associate);
        } else {
            throw new NoDocumentFoundException(Constants.DOCUMENT_NOT_FOUND);
        }
    }

    @Transactional
    public void saveAgenda(Agenda agenda) {
        if(StringUtils.isNotBlank(agenda.getSubject())) {
            agendaRepository.save(agenda);
        } else {
            throw new NoSubjectFoundException(Constants.NO_SUBJECT_FOUND);
        }
    }

    //  This method was removed because the external API is not working properly
    public Boolean validateDocument(String document) {
        return restClient.validateDocument(document);
    }

    public Associate getAssociateByCpf(String document){
        if(StringUtils.isNotBlank(document)) {
            return associateRepository.getAssociateByCpf(document);
        } else {
            throw new NoSubjectFoundException(Constants.DOCUMENT_NOT_FOUND);
        }
    }

    @Transactional
    public void registerVote(Poll poll) {
        if (getAssociateByCpf(poll.getAssociate().getCpf()) != null &&
                agendaRepository.findAgendaBySubject(poll.getAgenda().getSubject()) &&
                !pollRepository.findByAssociateCpfAndAgendaSubject(poll.getAssociate().getCpf(), poll.getAgenda().getSubject())) {
            pollRepository.save(poll);
        }
    }

    public Poll getPollObject(String document, String agendaSubject){
        Associate associate = associateRepository.getAssociateByCpf(document);
        Agenda agenda = agendaRepository.getAgendaBySubject(agendaSubject);
        return new Poll(null, associate, agenda, null);
    }
}
