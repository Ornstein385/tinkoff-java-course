package edu.java.bot.dao;

import edu.java.bot.model.Link;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import org.springframework.stereotype.Component;

@Component
public class LinkDaoMockImpl implements LinkDaoInterface {

    private HashMap<Long, HashSet<Link>> tracked = new HashMap<>();

    @Override
    public void add(Link link) {
        if (!tracked.containsKey(link.getUserId())) {
            tracked.put(link.getUserId(), new HashSet<>());
        }
        tracked.get(link.getUserId()).add(link);
    }

    @Override
    public void remove(Link link) {
        if (tracked.containsKey(link.getUserId())) {
            tracked.get(link.getUserId()).remove(link);
        }
    }

    @Override
    public Collection<Link> getAll(long id) {
        return tracked.get(id);
    }

    @Override
    public int getSize(long id) {
        if (tracked.containsKey(id)) {
            return tracked.get(id).size();
        } else {
            return 0;
        }
    }
}
