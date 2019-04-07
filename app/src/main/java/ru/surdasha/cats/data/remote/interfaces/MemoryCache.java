package ru.surdasha.cats.data.remote.interfaces;

import java.util.List;

import ru.surdasha.cats.data.remote.models.CatRemote;

public interface MemoryCache {
    List<CatRemote> getCats();

    boolean isEmpty();

    void addCats(List<CatRemote> remotes);

    void clearCache();
}
