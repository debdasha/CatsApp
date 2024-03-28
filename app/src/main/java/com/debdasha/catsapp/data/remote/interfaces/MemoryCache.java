package com.debdasha.catsapp.data.remote.interfaces;

import java.util.List;

import com.debdasha.catsapp.data.remote.models.CatRemote;

public interface MemoryCache {
    List<CatRemote> getCats();

    boolean isEmpty();

    void addCats(List<CatRemote> remotes);

    void clearCache();
}
