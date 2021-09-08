package edu.eci.arsw.blueprints.Filters;

import java.util.Set;

import edu.eci.arsw.blueprints.model.Blueprint;

public interface Filter {
    public Blueprint blueprintFilter(Blueprint blueprint);

    public Set<Blueprint> blueprintFilter(Set<Blueprint> blueprints);
}
