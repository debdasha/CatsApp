package ru.surdasha.cats.data.remote;

import java.util.LinkedList;
import java.util.List;

import ru.surdasha.cats.data.remote.interfaces.MemoryCache;
import ru.surdasha.cats.data.remote.models.CatRemote;

public class MemoryCacheImpl implements MemoryCache {
    private final int MAX_SIZE = 100;
    private List<CatRemote> cats = new LinkedList<>();

    @Override
    public List<CatRemote> getCats(){
        return cats;
    }

    @Override
    public boolean isEmpty(){
        return cats.isEmpty();
    }

    @Override
    public void addCats(List<CatRemote> remotes){
        checkCacheSize();
        cats.addAll(remotes);
    }

    @Override
    public void clearCache(){
        cats.clear();
    }

    private void checkCacheSize(){
        if (shouldShrinkCache()){
            cats = getShrinkedCache();
        }
    }

    private boolean shouldShrinkCache(){
        return cats.size() > MAX_SIZE;
    }

    private List<CatRemote> getShrinkedCache(){
        List<CatRemote> newList = new LinkedList<>();
        newList = cats.subList(cats.size() / 2, cats.size());
        return newList;
    }
}
