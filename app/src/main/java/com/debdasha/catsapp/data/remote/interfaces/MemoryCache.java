package com.debdasha.catsapp.data.remote.interfaces;

import com.debdasha.catsapp.data.remote.models.CatRemote;

import java.util.List;

public interface MemoryCache {
    List<CatRemote> getCats();

    void addCats(List<CatRemote> remotes);

    void clearCache();
}
