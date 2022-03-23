package com.example.accont;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AccountDao {
    @Query("SELECT * FROM ACCOUNTS")
    List<AccountEntity> getAll();

    @Insert
    void insertAll(AccountEntity...accounts);

    @Insert
    void insert(AccountEntity account);

    @Delete
    void delete(AccountEntity account);

}
