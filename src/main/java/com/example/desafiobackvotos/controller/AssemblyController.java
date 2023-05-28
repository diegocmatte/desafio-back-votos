package com.example.desafiobackvotos.controller;

import com.example.desafiobackvotos.domain.Agenda;
import com.example.desafiobackvotos.service.AssemblyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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

    @PostMapping(value = "/createAgenda", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create new agenda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful POST request", content = {@Content(schema = @Schema(implementation = Agenda.class))})
    })
    public ResponseEntity<Agenda> createAgenda(
            @RequestBody Agenda agenda) {
        assemblyService.saveAgenda(agenda);
        return ResponseEntity.ok(agenda);
    }

    @GetMapping(value = "/validateDocument")
    @Operation(summary = "Validate the associate document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful GET request", content = {@Content(schema = @Schema(implementation = HttpStatus.class))})
    })
    public ResponseEntity<HttpStatus> validateDocument(
            @Parameter(description = "Associate document", required = true) @RequestParam String document){
        Boolean validate = assemblyService.validateDocument(document);
        if(validate){
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
