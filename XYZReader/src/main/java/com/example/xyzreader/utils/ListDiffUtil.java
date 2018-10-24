package com.example.xyzreader.utils;

import android.support.v7.util.DiffUtil;
import com.example.xyzreader.data.model.Article;
import java.util.List;

public class ListDiffUtil extends DiffUtil.Callback {

  private List<Article> oldList;
  private List<Article> newList;


  public ListDiffUtil(List<Article> oldList, List<Article> newList) {
    this.oldList = oldList;
    this.newList = newList;
  }

  @Override
  public int getOldListSize() {
    return oldList.size();
  }

  @Override
  public int getNewListSize() {
    return newList.size();
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return oldList.get(oldItemPosition).id == newList.get(newItemPosition).id;
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
  }
}
