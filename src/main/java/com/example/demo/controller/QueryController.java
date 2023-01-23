package com.example.demo.controller;

import java.sql.SQLException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Crud;
import com.example.demo.entidades.CentroSanitario;

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
    public ResponseEntity<String> getCentrosSanitarios(
            @RequestParam("loc") String loc,
            @RequestParam("cp") String cp,
            @RequestParam("prov") String prov,
            @RequestParam("tipo") String tipo)
        {

        if(tipo.equals("Cualquiera")) tipo = "";

        CentroSanitario[] centrosSanitarios = crud.getCentrosSanitarios(loc, cp, prov, tipo);

        //Genera un JSON con toda la informacion de los hospitales
        String jsonRes = "[";
        if(centrosSanitarios.length > 0){
            for (int i = 0; i < centrosSanitarios.length - 1; i++) {
                jsonRes += centrosSanitarios[i].toString() + ",";
                // System.out.println(i + " " + centrosSanitarios[i].getNombre());
            }
            jsonRes += centrosSanitarios[centrosSanitarios.length - 1].toString();
        }
        jsonRes += "]";
        

        //return ResponseEntity.status(HttpStatus.OK).body(Arrays.toString(crud.getCentrosSanitarios(loc, cp, prov, tipo)));
        return ResponseEntity.status(HttpStatus.OK).body(jsonRes);
    }
}
