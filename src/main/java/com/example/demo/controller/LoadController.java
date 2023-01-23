package com.example.demo.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Crud;
import com.example.demo.extractores.ExtractorCV;
import com.example.demo.extractores.ExtractorEUS;
import com.example.demo.extractores.ExtractorIB;
import com.example.demo.util.CentroSanitarioManager;
import com.example.demo.util.Fetch;
import com.example.demo.util.LocalidadManager;
import com.example.demo.util.Logger;
import com.example.demo.util.ProvinciaManager;

@RestController
@RequestMapping(value="/api/load")
public class LoadController {
    private Crud crud;

    public LoadController(){
        try {
            this.crud = Crud.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @CrossOrigin
    @PostMapping(value="/health-centers")
    public ResponseEntity<String> loadHealthCenters(@RequestBody String comunidades){
        JSONObject comunidadesJson = new JSONObject(comunidades);

        boolean cv = (boolean)comunidadesJson.get("cv");
        boolean eus = (boolean)comunidadesJson.get("eus");
        boolean ib = (boolean)comunidadesJson.get("ib");

        ProvinciaManager provinciaManager = ProvinciaManager.getInstance();
        LocalidadManager localidadManager = LocalidadManager.getInstance();
        CentroSanitarioManager centroSanitarioManager = CentroSanitarioManager.getInstance();

        Logger logger = Logger.getInstance();

        //Vaciamos la BD
        crud.clearTables();

        //Vaciamos el log
        logger.clear();

        String res = "Error";

        //Conseguimos las fuentes de los wrappers y extraemos los datos
        try {
            if (cv) {
                ExtractorCV extractorCV = new ExtractorCV(Fetch.get("/api/cv/health-centers"));
                extractorCV.convertir();
            }

            if (eus) {
                ExtractorEUS extractorEUS = new ExtractorEUS(Fetch.get("/api/eus/health-centers"));
                extractorEUS.convertir();
            }

            if (ib) {
                ExtractorIB extractorIB = new ExtractorIB(Fetch.get("/api/ib/health-centers"));
                extractorIB.convertir();
            }

            if(cv || eus || ib){
                crud.createProvincia(provinciaManager.obtenerProvincias());
                crud.createLocalidad(localidadManager.obtenerLocalidades());
                crud.createHospital(centroSanitarioManager.obtenerCentrosSanitarios());
            }

            res = "";

            res += String.format("Se ha añadido %d centros sanitarios exitosamente\\n", centroSanitarioManager.getAmount());
            res += logger.getLog();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).body(String.format("{\"results\":\"%s\"}", res));
    }

    @CrossOrigin
    @DeleteMapping(value="/health-centers")
    public ResponseEntity<String> clearHealthCenters(){
        ProvinciaManager provinciaManager = ProvinciaManager.getInstance();
        LocalidadManager localidadManager = LocalidadManager.getInstance();
        CentroSanitarioManager centroSanitarioManager = CentroSanitarioManager.getInstance();

        Logger logger = Logger.getInstance();

        //Vaciamos la BD
        crud.clearTables();

        //Vaciamos todas las entidades guardadas en cualquier momento
        centroSanitarioManager.clear();
        localidadManager.clear();
        provinciaManager.clear();

        //Vaciamos el log
        logger.clear();

        String res = "";

        res += String.format("Se han eliminado todos los centros sanitarios");
        res += logger.getLog();

        return ResponseEntity.status(HttpStatus.OK).body(String.format("{\"results\":\"%s\"}", res));
    }
}
