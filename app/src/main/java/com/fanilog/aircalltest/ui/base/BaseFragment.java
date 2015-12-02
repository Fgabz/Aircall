package com.fanilog.aircalltest.ui.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by fanilo on 11/30/15.
 */
public class BaseFragment extends Fragment {

  private int layoutId;
  private final CompositeSubscription subscriptions = new CompositeSubscription();

  protected void setLayoutId(int layoutId) {
    this.layoutId = layoutId;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(layoutId, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    ButterKnife.bind(this, getActivity());
  }

  @Override public void onDestroyView() {
    ButterKnife.unbind(this);
    super.onDestroyView();
    subscriptions.unsubscribe();
  }

  protected void bind(Subscription subscription) {
    subscriptions.add(subscription);
  }


  @Override public void onDestroy() {
    ButterKnife.unbind(this);
    subscriptions.clear();
    super.onDestroy();
  }
}
