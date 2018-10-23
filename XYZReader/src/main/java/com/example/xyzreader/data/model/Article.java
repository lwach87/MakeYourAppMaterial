package com.example.xyzreader.data.model;


import static com.example.xyzreader.utils.Constants.TABLE_NAME;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = TABLE_NAME)
public class Article {

  @PrimaryKey
  public int id;
  public String title;
  public String author;
  public String body;
  public String thumb;
  public String photo;
  public double aspect_ratio;
  public String published_date;

  public Article() {
  }
}
