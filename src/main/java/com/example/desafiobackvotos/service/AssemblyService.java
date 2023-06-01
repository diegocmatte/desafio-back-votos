package com.example.desafiobackvotos.service;

import com.example.desafiobackvotos.domain.Agenda;
import com.example.desafiobackvotos.domain.Associate;
import com.example.desafiobackvotos.domain.Poll;
import com.example.desafiobackvotos.exception.DocumentAlreadyExistsException;
import com.example.desafiobackvotos.exception.NoDocumentFoundException;
import com.example.desafiobackvotos.exception.NoSubjectFoundException;
import com.example.desafiobackvotos.exception.SubjectAlreadyExistsException;
import com.example.desafiobackvotos.repository.AgendaRepository;
import com.example.desafiobackvotos.repository.AssociateRepository;
import com.example.desafiobackvotos.repository.PollRepository;
import com.example.desafiobackvotos.util.Constants;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AssemblyService {

    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private AssociateRepository associateRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AssemblyService.class);

    @Transactional
    public Associate saveAssociate(Associate associate){
        if(StringUtils.isNotBlank(associate.getCpf())){
            Optional<Associate> savedAssociate = Optional.ofNullable(associateRepository.getAssociateByCpf(associate.getCpf()));
            if(savedAssociate.isPresent()){
                throw new DocumentAlreadyExistsException("Document already exists: " + associate.getCpf());
            }
            LOGGER.info("Associate created in database");
            return associateRepository.save(associate);
        } else {
            LOGGER.error("No CPF was informed");
            throw new NoDocumentFoundException(Constants.DOCUMENT_NOT_FOUND);
        }
    }

    @Transactional
    public Agenda saveAgenda(Agenda agenda) {
        if(StringUtils.isNotBlank(agenda.getSubject())) {
            Optional<Agenda> savedAgenda = Optional.ofNullable(agendaRepository.getAgendaBySubject(agenda.getSubject()));
            if(savedAgenda.isPresent()){
                throw new SubjectAlreadyExistsException("Subject already exists: " + agenda.getSubject());
            }
            LOGGER.info("Agenda created in database");
            return agendaRepository.save(agenda);
        } else {
            LOGGER.error("No subject was informed");
            throw new NoSubjectFoundException(Constants.NO_SUBJECT_FOUND);
        }
    }

    //  This method was removed because the external API is not working properly
    private void validateDocument(String document) {
        //return restClient.validateDocument(document);
    }

    public Optional<Associate> getAssociateByCpf(String document){
        if(StringUtils.isNotBlank(document)) {
            LOGGER.info("Associate located in database");
            return Optional.ofNullable(associateRepository.getAssociateByCpf(document));
        } else {
            LOGGER.error("No document was found in database");
            throw new NoDocumentFoundException(Constants.DOCUMENT_NOT_FOUND);
        }
    }

    public Optional<Agenda> getAgendaBySubject(String subject) {
        if (StringUtils.isNotBlank(subject)) {
            LOGGER.info("Agenda located in database");
            return Optional.ofNullable(agendaRepository.getAgendaBySubject(subject));
        } else {
            LOGGER.error("No subject was found in database");
            throw new NoSubjectFoundException(Constants.NO_SUBJECT_FOUND);
        }
    }

    @Transactional
    public void registerVote(Poll poll) {
        if (getAssociateByCpf(poll.getAssociate().getCpf()).isPresent() &&
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
