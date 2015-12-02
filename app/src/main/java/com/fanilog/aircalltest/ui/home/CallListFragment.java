package com.fanilog.aircalltest.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import com.fanilog.aircalltest.AircallApp;
import com.fanilog.aircalltest.R;
import com.fanilog.aircalltest.data.AirCallApiCalls;
import com.fanilog.aircalltest.data.model.AirCallActivity;
import com.fanilog.aircalltest.ui.base.BaseFragment;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import timber.log.Timber;

public class CallListFragment extends BaseFragment implements CallListAdapter.Callbacks {

  @Inject AirCallApiCalls airCallApiCalls;

  @Bind(R.id.recyclerview) RecyclerView recyclerView;

  private Observable<List<AirCallActivity>> aircallActivityObservable;
  private CallListAdapter                   callListAdapter;
  private List<AirCallActivity> itemList = new ArrayList<>();

  public static CallListFragment newInstance() {
    CallListFragment fragment = new CallListFragment();
    return fragment;
  }

  public CallListFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    AircallApp.getApp(this.getActivity()).getAircallComponent().inject(this);

    Timber.d("Create Fragment");
    aircallActivityObservable = airCallApiCalls.getAircallActivityList().cache();
  }

  private void apiCall() {
    bind(aircallActivityObservable.subscribe(airCallActivities -> {
          Timber.d("Getting feeds form Aircall api");
          itemList = airCallActivities;
        }, throwable -> {
          Timber.e(throwable, "Error getting throwable");
        }, () -> setRecyclerView()
    ));
  }

  private void setRecyclerView() {
    Timber.d("Set Recyclerview");
    callListAdapter = new CallListAdapter(getActivity().getLayoutInflater());
    cleanListCall();
    callListAdapter.addDatas(itemList);
    callListAdapter.setCallbacks(this);
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(callListAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
  }

  private void cleanListCall() {
    List<AirCallActivity> cleanList = new ArrayList<>();

    Observable.from(itemList)
        .filter(airCallActivity -> !airCallActivity.getIsCallArchived())
        .subscribe(
            cleanList::add,
            throwable -> Timber.e(throwable, "error cleaning list"),
            () -> {
              itemList.clear();
              itemList.addAll(cleanList);
              cleanList.clear();
            });

    Timber.d("Done cleaning list");
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    apiCall();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    setLayoutId(R.layout.fragment_call_list);
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onItemClick(View v, int position) {
    CallDetailFragment callDetailFragment = CallDetailFragment.newInstance(itemList.get(position));
    getFragmentManager().beginTransaction()
        .addToBackStack(null)
        .replace(R.id.fragment_container, callDetailFragment)
        .commit();
  }
}
