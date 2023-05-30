package com.example.desafiobackvotos.controller;

import com.example.desafiobackvotos.domain.Agenda;

import com.example.desafiobackvotos.domain.Associate;
import com.example.desafiobackvotos.domain.Poll;

import com.example.desafiobackvotos.service.AssemblyService;
import com.example.desafiobackvotos.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Tag(name = "AssemblyController", description = "Assembly Controller")
@RequestMapping("/assembly")
public class AssemblyController {

    @Autowired
    private AssemblyService assemblyService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AssemblyController.class);

    @PostMapping(value = "/createAssociate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create new associate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful POST request - Associate was created", content = {@Content(schema = @Schema(implementation = Associate.class))}),
            @ApiResponse(responseCode = "400", description = "Problem during the process")
                    })
    public ResponseEntity<Associate> createAssociate(
            @Valid @RequestBody Associate associate){
        String methodName = "createAssociate";
        if(associate != null) {
            assemblyService.saveAssociate(associate);
            LOGGER.info("Associate [ID:{},CPF:{}] created", associate.getAssociateId(), associate.getCpf());
            return new ResponseEntity<>(associate, HttpStatus.OK);
        } else {
            LOGGER.error("Error during Associate creation, method [{}}", methodName);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/createAgenda", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create new agenda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful POST request - Agenda was created", content = {@Content(schema = @Schema(implementation = Agenda.class))}),
            @ApiResponse(responseCode = "400", description = "Problem during the process")
    })
    public ResponseEntity<Agenda> createAgenda(
            @Valid @RequestBody Agenda agenda) {
        String methodName = "createAgenda";
        if(agenda != null) {
            assemblyService.saveAgenda(agenda);
            LOGGER.info("Agenda [ID:{},Subject:{}] created", agenda.getAgendaId(), agenda.getSubject());
            return new ResponseEntity<>(agenda, HttpStatus.OK);
        } else {
            LOGGER.error("Error during Agenda creation, method [{}]", methodName);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/validateDocument")
    @Operation(summary = "Validate the associate document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET request", content = {@Content(schema = @Schema(implementation = HttpStatus.class))}),
            @ApiResponse(responseCode = "400", description = "Problem during the process"),
            @ApiResponse(responseCode = "404", description = "No document was found")
    })
    public ResponseEntity<?> validateDocument(
            @Parameter(description = "Associate document", required = true) @RequestParam String document){
        String methodName = "voteRegister";
        Associate associate = assemblyService.getAssociateByCpf(document);
        if(associate != null){
            LOGGER.info("Document has been validated");
            return new ResponseEntity<>("The associate exists and the document is valid.", HttpStatus.OK);
        }
        LOGGER.error("Error during the validation of the [{}] document, method [{}]", document, methodName);
        return new ResponseEntity<>("A problem occurred during the validation of the document.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register the vote from an associate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful POST request", content = {@Content(schema = @Schema(implementation = Poll.class))}),
            @ApiResponse(responseCode = "400", description = "Problem during the process"),
            @ApiResponse(responseCode = "500", description = "Problem with API validator")
    })
    public ResponseEntity<?> voteRegister(
            @Parameter(description = "Associate document", required = true) @RequestParam String document,
            @Parameter(description = "Agenda subject to vote", required = true) @RequestParam String agendaSubject,
            @Parameter(description = "Associate vote", required = true) @RequestParam String vote){
        String methodName = "voteRegister";
        if(StringUtils.equalsIgnoreCase(vote, Constants.VOTED_NO) || StringUtils.equalsIgnoreCase(vote, Constants.VOTED_YES)) {
            if (StringUtils.isNotBlank(document) && StringUtils.isNotBlank(agendaSubject)) {
                Poll poll = assemblyService.getPollObject(document, agendaSubject);
                if(poll != null) {
                    poll.setAssociateVote(vote);
                    assemblyService.registerVote(poll);
                    LOGGER.info("Poll {} created", poll);
                    return new ResponseEntity<>(poll, HttpStatus.OK);
                }
            }
        }
        LOGGER.error("Error during registering the vote, method [{}]", methodName);
        return new ResponseEntity<>("A problem occurred during the vote registering.", HttpStatus.BAD_REQUEST);
    }
}
