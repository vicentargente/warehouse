package com.example.demo.controller;

import java.sql.SQLException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Crud;

@RestController
@RequestMapping(value="/api/query")
public class QueryController {
    private Crud crud;

    public QueryController(){
        try {
            this.crud = Crud.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @CrossOrigin
    @GetMapping(value="/health-centers")
    public ResponseEntity<String> getCentrosSanitarios(){
        return ResponseEntity.status(HttpStatus.OK).body(Arrays.toString(crud.getCentrosSanitarios()));
    }
}
