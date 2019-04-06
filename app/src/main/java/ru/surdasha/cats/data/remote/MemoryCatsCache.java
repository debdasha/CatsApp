package ru.surdasha.cats.data.remote;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import ru.surdasha.cats.data.remote.models.CatRemote;

public class MemoryCatsCache {
    private final int MAX_SIZE = 100;
    private List<CatRemote> cats = new LinkedList<>();

    public List<CatRemote> getCats(){
        return cats;
    }

    public boolean isEmpty(){
        return cats.isEmpty();
    }

    public void addCats(List<CatRemote> remotes){
        checkCacheSize();
        cats.addAll(remotes);
    }

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
