package com.example.desafiobackvotos.controller;

import com.example.desafiobackvotos.domain.Agenda;
import com.example.desafiobackvotos.restclient.response.UserResponse;
import com.example.desafiobackvotos.service.AssemblyService;

import io.micrometer.common.util.StringUtils;
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
@RequestMapping("/assembly")
public class AssemblyController {

    @Autowired
    private AssemblyService assemblyService;

    @PostMapping(value = "/createAgenda", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Agenda> createAgenda(@RequestBody Agenda agenda) {
        assemblyService.saveAgenda(agenda);
        return ResponseEntity.ok(agenda);
    }

    @GetMapping(value = "/validateDocument")
    public ResponseEntity<HttpStatus> validateDocument(@RequestParam String document){
        Boolean validate = assemblyService.validateDocument(document);
        if(validate){
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
