/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
@Qualifier("InMemory")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();

    public InMemoryBlueprintPersistence() {
        // load stub data
        Point[] pts = new Point[] { new Point(140, 140), new Point(115, 115) };
        Blueprint bp = new Blueprint("Mina", "Jayco1", pts);
        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        Point[] pts1 = new Point[] { new Point(140, 140), new Point(130, 130) };
        Blueprint bp1 = new Blueprint("Mina", "Jayco2", pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);

        Point[] pts2 = new Point[] { new Point(100, 100), new Point(20, 20) };
        Blueprint bp2 = new Blueprint("Luis", "Tego1", pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getAllBluePrints() {
        return blueprints.values().stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Blueprint> getBluePrintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprintsByAuthor = new HashSet<>();
        boolean authorExists = false;
        for (Blueprint blueprint : getAllBluePrints()) {
            if (blueprint.getAuthor().equals(author)) {
                authorExists = true;
                blueprintsByAuthor.add(blueprint);
            }
        }
        if (!authorExists) {
            throw new BlueprintNotFoundException("The given author doesn't exist");
        }
        return blueprintsByAuthor;
    }

}
