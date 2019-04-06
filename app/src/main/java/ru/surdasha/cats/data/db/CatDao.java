package ru.surdasha.cats.data.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import ru.surdasha.cats.domain.models.Cat;

@Dao
public interface CatDao {
    @Query("SELECT * FROM cats")
    Maybe<List<CatDb>> getAll();

    @Query("SELECT * FROM cats where serverId = :serverId")
    Maybe<CatDb> getByServerId(String serverId);

    @Delete
    Completable delete(CatDb catDb);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable add(CatDb cat);
}
