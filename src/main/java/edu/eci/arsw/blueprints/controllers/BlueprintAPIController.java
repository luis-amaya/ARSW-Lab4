/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    @Qualifier("blueprintsServices")
    BlueprintsServices blueprintsServices;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprints() throws BlueprintPersistenceException {
        Set<Blueprint> blueprintSet;
        blueprintSet = blueprintsServices.getAllBlueprints();
        return new ResponseEntity<>(blueprintSet, HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/authors/{author}")
    public ResponseEntity<?> getBluePrintsByAuthor(@PathVariable String author) {
        Set<Blueprint> blueprintSet;
        try {
            blueprintSet = blueprintsServices.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(blueprintSet, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/authors/{author}/blueprints/{bpname}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author, @PathVariable String bpname) {
        Blueprint blueprint;
        try {
            blueprint = blueprintsServices.getBlueprint(author, bpname);
            Set<Blueprint> blueprintSet = new HashSet<Blueprint>();
            blueprintSet.add(blueprint);
            return new ResponseEntity<>(blueprint, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostRecursoPlanos(@RequestBody JSONObject blueprint) {
        try {
            // blueprintsServices.addNewBlueprint(blueprint);
            System.out.println(blueprint.get("author") + " " + blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN);
        }

    }
}
