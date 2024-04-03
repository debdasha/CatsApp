package com.debdasha.catsapp.data.remote;

import com.debdasha.catsapp.data.remote.interfaces.MemoryCache;
import com.debdasha.catsapp.data.remote.models.CatRemote;

import java.util.LinkedList;
import java.util.List;

public class MemoryCacheImpl implements MemoryCache {
    private final int MAX_SIZE = 100;
    private List<CatRemote> cats = new LinkedList<>();

    @Override
    public List<CatRemote> getCats() {
        return cats;
    }

    @Override
    public void addCats(List<CatRemote> remotes) {
        checkCacheSize();
        cats.addAll(remotes);
    }

    @Override
    public void clearCache() {
        cats.clear();
    }

    private void checkCacheSize() {
        if (shouldShrinkCache()) {
            cats = getShrinkedCache();
        }
    }

    private boolean shouldShrinkCache() {
        return cats.size() > MAX_SIZE;
    }

    private List<CatRemote> getShrinkedCache() {
        return cats.subList(cats.size() / 2, cats.size());
    }
}
