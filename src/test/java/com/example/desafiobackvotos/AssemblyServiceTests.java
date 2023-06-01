package com.example.desafiobackvotos;

import com.example.desafiobackvotos.domain.Agenda;
import com.example.desafiobackvotos.domain.Associate;
import com.example.desafiobackvotos.exception.DocumentAlreadyExistsException;
import com.example.desafiobackvotos.exception.NoDocumentFoundException;
import com.example.desafiobackvotos.exception.NoSubjectFoundException;
import com.example.desafiobackvotos.exception.SubjectAlreadyExistsException;
import com.example.desafiobackvotos.repository.AgendaRepository;
import com.example.desafiobackvotos.repository.AssociateRepository;
import com.example.desafiobackvotos.service.AssemblyService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AssemblyServiceTests {

    @InjectMocks
    private AssemblyService assemblyService;
    @Mock
    private AssociateRepository associateRepository;
    @Mock
    private AgendaRepository agendaRepository;

    private Agenda agenda;
    private Associate associate;

    @BeforeEach
    void init(){
        associate = Associate.builder()
                .associateId(1)
                .cpf("12345678900")
                .build();

        agenda = Agenda.builder()
                .agendaId(1)
                .subject("test subject")
                .build();

    }

    @DisplayName("Test for saveAssociate")
    @Test
    public void givenAssociateObject_whenSaveAssociate_thenReturnAssociateObject(){
        given(associateRepository.getAssociateByCpf(associate.getCpf()))
                .willReturn(associate);

        given(associateRepository.save(associate)).willReturn(associate);

        System.out.println(associateRepository);
        System.out.println(assemblyService);

        System.out.println(associate);
        assertThat(associate).isNotNull();
    }

    @DisplayName("Test for saveAssociate which throws exception DocumentAlreadyExistsException")
    @Test
    public void givenAssociateObject_whenSaveAssociate_thenReturnDocumentAlreadyExistsException(){
        given(associateRepository.getAssociateByCpf(associate.getCpf()))
                .willReturn(associate);

        System.out.println(associateRepository);
        System.out.println(assemblyService);

        assertThrows(DocumentAlreadyExistsException.class, () -> assemblyService.saveAssociate(associate));

        verify(associateRepository, never()).save(any(Associate.class));
    }

    @DisplayName("Test for getAssociateByCpf")
    @Test
    public void givenAssociateObject_whenGetByCpf_thenReturnAssociateObject(){
        given(associateRepository.getAssociateByCpf(associate.getCpf()))
                .willReturn(associate);

        given(associateRepository.save(associate)).willReturn(associate);

        System.out.println(associateRepository);
        System.out.println(assemblyService);

        Optional<Associate> getAssociateByCpf = assemblyService.getAssociateByCpf(associate.getCpf());

        System.out.println(getAssociateByCpf);
        assertThat(getAssociateByCpf.isPresent()).isNotNull();
    }

    @DisplayName("Test for getAssociateByCpf when CPF is blank")
    @Test
    public void givenAssociateObject_whenGetByCpf_thenReturnNoDocumentFoundException(){
        given(associateRepository.getAssociateByCpf(associate.getCpf()))
                .willReturn(associate);

        given(associateRepository.save(associate)).willReturn(associate);

        assertThrows(NoDocumentFoundException.class, () -> assemblyService.getAssociateByCpf(""));

        verify(associateRepository, never()).save(any(Associate.class));
    }

    @DisplayName("Test for saveAgenda")
    @Test
    public void givenAgendaObject_whenSaveAgenda_thenReturnAgendaObject(){
        given(agendaRepository.getAgendaBySubject(agenda.getSubject()))
                .willReturn(agenda);

        given(agendaRepository.save(agenda)).willReturn(agenda);

        System.out.println(agendaRepository);
        System.out.println(assemblyService);

        System.out.println(agenda);
        assertThat(agenda).isNotNull();
    }

    @DisplayName("Test for saveAgenda which throws exception SubjectAlreadyExistsException")
    @Test
    public void givenAgendaObject_whenSaveAgenda_thenReturnSubjectAlreadyExistsException(){
        given(agendaRepository.getAgendaBySubject(agenda.getSubject()))
                .willReturn(agenda);

        given(agendaRepository.save(agenda)).willReturn(agenda);

        System.out.println(agendaRepository);
        System.out.println(assemblyService);

        assertThrows(SubjectAlreadyExistsException.class, () -> assemblyService.saveAgenda(agenda));

        verify(agendaRepository, never()).save(any(Agenda.class));
    }

    @DisplayName("Test for getAgendaBySubject")
    @Test
    public void givenAgendaObject_whenGetBySubject_thenReturnAgendaObject(){
        given(agendaRepository.getAgendaBySubject(agenda.getSubject()))
                .willReturn(agenda);

        given(agendaRepository.save(agenda)).willReturn(agenda);

        System.out.println(agendaRepository);
        System.out.println(assemblyService);

        Optional<Agenda> getAgendaBySubject = assemblyService.getAgendaBySubject(agenda.getSubject());

        assertThat(getAgendaBySubject.isPresent()).isNotNull();
    }

    @DisplayName("Test for getAgendaBySubject when subject is blank")
    @Test
    public void givenAgendaObject_whenGetBySubject_thenReturnNoSubjectFoundException(){
        given(agendaRepository.getAgendaBySubject((agenda.getSubject())))
                .willReturn(agenda);

        given(agendaRepository.save(agenda)).willReturn(agenda);

        assertThrows(NoSubjectFoundException.class, () -> assemblyService.getAgendaBySubject(""));

        verify(agendaRepository, never()).save(any(Agenda.class));
    }
}
