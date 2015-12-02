package com.fanilog.aircalltest.ui.home;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fanilog.aircalltest.R;
import com.fanilog.aircalltest.data.model.AirCallActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanilo on 11/30/15.
 */
public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.ViewHolder> {

  private List<AirCallActivity> airCallActivityList = new ArrayList<>();
  private Callbacks      callbacks;
  private LayoutInflater layoutInflater;
  private Resources      resources;

  public interface Callbacks {
    void onItemClick(View v, int position);
  }

  public CallListAdapter(LayoutInflater layoutInflater) {
    this.layoutInflater = layoutInflater;
    this.resources = layoutInflater.getContext().getResources();

  }

  @Override
  public CallListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = layoutInflater.inflate(R.layout.item_list_aircallactvity, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CallListAdapter.ViewHolder holder, int position) {
    AirCallActivity airCallActivity = airCallActivityList.get(position);

    holder.rootLayoutItem.setBackgroundColor((position % 2 == 0) ? resources.getColor(android.R
        .color.transparent) : resources.getColor(R.color.dark_cream));
    holder.callDestination.setText(airCallActivity.getCallTo());
    holder.callType.setImageResource(airCallActivity.getCallType().equals("missed") ?
                                     R.drawable.circle_drawable_missed :
                                     R.drawable.circle_drawable_answered);
    holder.authorCall.setText(airCallActivity.getCallFrom());
    holder.aircallLine.setText(airCallActivity.getAircallNameLine());
    holder.time.setText(airCallActivity.getStartDate());
  }

  @Override
  public int getItemCount() {
    return airCallActivityList.size();
  }

  public void addDatas(List<AirCallActivity> data) {
    airCallActivityList.clear();

    if (data != null) {
      airCallActivityList.addAll(data);
    }
    notifyDataSetChanged();
  }

  public void setCallbacks(Callbacks callbacks) {
    this.callbacks = callbacks;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.call_destination) TextView    callDestination;
    @Bind(R.id.call_type)        ImageView   callType;
    @Bind(R.id.author_call)      TextView    authorCall;
    @Bind(R.id.aircall_line)     TextView    aircallLine;
    @Bind(R.id.time)             TextView    time;
    @Bind(R.id.root_item)        FrameLayout rootLayoutItem;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(onAircallItemClickListener);
    }

    private final View.OnClickListener onAircallItemClickListener =
        v -> callbacks.onItemClick(v, getAdapterPosition());
  }
}
