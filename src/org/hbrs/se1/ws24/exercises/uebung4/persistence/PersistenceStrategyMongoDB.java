package org.hbrs.se1.ws24.exercises.uebung4.persistence;

import java.util.List;

public class PersistenceStrategyMongoDB<E> implements PersistenceStrategy<E> {

    @Override
    public void save(List<E> UserStory) {
        throw new UnsupportedOperationException("Not implemented!");
    }

    @Override
    public List<E> load() {
        throw new UnsupportedOperationException("Not implemented!");
    }
}
