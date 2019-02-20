package com.example.xyzreader.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.xyzreader.data.model.Article;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM article")
    List<Article> getAllArticles();

    @Query("SELECT * FROM article WHERE id = :id")
    Article getArticleById(int id);

    @Insert(onConflict = REPLACE)
    void insertArticles(List<Article> article);
}
