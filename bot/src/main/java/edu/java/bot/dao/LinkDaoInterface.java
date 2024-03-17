package edu.java.bot.dao;

import edu.java.bot.models.Link;
import java.util.Collection;

public interface LinkDaoInterface {

    void add(Link link);

    void remove(Link link);

    Collection<Link> getAll(long id);

    int getSize(long id);
}
