package edu.eci.arsw.blueprints.Filters.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.Filters.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

@Component
@Qualifier("Subsampling")
public class SubsamplingFilter implements Filter {

    @Override
    public Blueprint blueprintFilter(Blueprint blueprint) {
        List<Point> points = blueprint.getPoints();
        List<Point> filteredList = new ArrayList<>();

        for (int index = 0; index < points.size() - 1; index++) {
            if (index % 2 == 0) {
                filteredList.add(points.get(index));
            }
        }
        blueprint.setPoints(filteredList);
        return blueprint;
    }

    @Override
    public Set<Blueprint> blueprintFilter(Set<Blueprint> blueprints) {
        blueprints.forEach(blueprint -> {
            blueprintFilter(blueprint);
        });
        return blueprints;
    }

}
