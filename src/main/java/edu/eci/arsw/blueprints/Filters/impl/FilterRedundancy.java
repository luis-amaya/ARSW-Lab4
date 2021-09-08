package edu.eci.arsw.blueprints.Filters.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.Filters.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

@Service
@Qualifier("Redundancy")
public class FilterRedundancy implements Filter {

    @Override
    public Blueprint blueprintFilter(Blueprint blueprint) {
        List<Point> points = blueprint.getPoints();
        List<Point> filteredList = new ArrayList<>();
        int size = points.size();
        for (int index = 0; index < size - 1; index++) {
            Point currentPoint = points.get(index);
            Point nextPoint = points.get(index + 1);
            if (!currentPoint.equals(nextPoint)) {
                filteredList.add(currentPoint);
            }
        }
        if (size != 0) {
            filteredList.add(points.get(size - 1));
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
