package com.example.desafiobackvotos.controller;

import com.example.desafiobackvotos.domain.Agenda;
import com.example.desafiobackvotos.service.AssemblyService;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assembly")
public class AssemblyController {

    @Autowired
    private AssemblyService assemblyService;

    @PostMapping("/createAgenda")
    public ResponseEntity<Agenda> createAgenda(@RequestBody Agenda agenda) throws NoSuchFieldException {
        assemblyService.saveAgenda(agenda);
        return ResponseEntity.ok(agenda);
    }
}
