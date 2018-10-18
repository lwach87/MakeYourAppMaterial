package com.example.xyzreader.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.example.xyzreader.data.model.Article;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;

@Dao
public interface ArticleDao {

  @Query("SELECT * FROM article")
  Flowable<List<Article>> getAllArticles();

  @Query("SELECT * FROM article WHERE id = :id")
  Single<Article> getArticleById(int id);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertArticles(List<Article> articles);

  @Query("DELETE FROM article")
  void deleteArticle();
}
