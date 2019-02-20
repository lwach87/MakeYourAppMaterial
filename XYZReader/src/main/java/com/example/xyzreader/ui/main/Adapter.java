package com.example.xyzreader.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.xyzreader.R;
import com.example.xyzreader.data.model.Article;
import com.example.xyzreader.utils.ListDiffUtil;
import com.example.xyzreader.utils.StringUtils;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

  private List<Article> articleList = new ArrayList<>();
  private OnClickListener listener;
  private Context context;
  private Picasso picasso;

  public Adapter(Picasso picasso, Context context) {
    this.picasso = picasso;
    this.context = context;
  }

  public interface OnClickListener {

    void onArticleClick(int id);
  }

  public void setOnClickListener(OnClickListener onClickListener) {
    this.listener = onClickListener;
  }

  @Override
  public long getItemId(int position) {
    return articleList.get(position).id;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context)
        .inflate(R.layout.list_item_article, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    holder.titleView.setText(articleList.get(position).title);

    holder.subtitleView.setText(StringUtils
        .getFormattedSubtitle(articleList.get(position).published_date,
            articleList.get(position).author));

    picasso
        .load(articleList.get(position).photo)
        .into(holder.thumbnailView);
  }

  @Override
  public int getItemCount() {
    return articleList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.thumbnail)
    ImageView thumbnailView;

    @BindView(R.id.article_title)
    TextView titleView;

    @BindView(R.id.article_subtitle)
    TextView subtitleView;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      listener.onArticleClick(articleList.get(getAdapterPosition()).id);
    }
  }

  public void swapData(List<Article> newArticles) {
    DiffUtil.DiffResult result = DiffUtil.calculateDiff(new ListDiffUtil(articleList, newArticles));
    articleList.clear();
    articleList.addAll(newArticles);
    result.dispatchUpdatesTo(this);
  }
}



