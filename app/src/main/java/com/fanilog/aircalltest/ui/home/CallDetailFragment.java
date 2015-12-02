package com.fanilog.aircalltest.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.fanilog.aircalltest.AircallApp;
import com.fanilog.aircalltest.R;
import com.fanilog.aircalltest.data.AirCallApiCalls;
import com.fanilog.aircalltest.data.model.AirCallActivity;
import com.fanilog.aircalltest.ui.base.BaseFragment;
import io.realm.Realm;
import javax.inject.Inject;
import org.parceler.Parcels;
import timber.log.Timber;

public class CallDetailFragment extends BaseFragment {
  private static final String ARG_PARAM1 = "aircall_Activity";

  @Inject AirCallApiCalls airCallApiCalls;

  @Bind(R.id.author_call)             TextView     authorCall;
  @Bind(R.id.call_destination)        TextView     callDestination;
  @Bind(R.id.aircall_line)            TextView     aircallLine;
  @Bind(R.id.time)                    TextView     time;
  @Bind(R.id.root_layout_detail_call) LinearLayout rootLayout;

  private AirCallActivity airCallActivity;
  private Realm           realm;

  public static CallDetailFragment newInstance(AirCallActivity airCallActivity) {
    CallDetailFragment fragment = new CallDetailFragment();
    Bundle args = new Bundle();
    args.putParcelable(ARG_PARAM1, Parcels.wrap(airCallActivity));
    fragment.setArguments(args);
    return fragment;
  }

  public CallDetailFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AircallApp.getApp(this.getActivity()).getAircallComponent().inject(this);
    realm = Realm.getInstance(getActivity());

    if (getArguments() != null) {
      airCallActivity = Parcels.unwrap(getArguments().getParcelable(ARG_PARAM1));
    }
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (airCallActivity != null) {
      authorCall.setText("Call from " + airCallActivity.getCallFrom());
      callDestination.setText("Call to " + airCallActivity.getCallTo());
      aircallLine.setText("via " + airCallActivity.getAircallNameLine());
      time.setText(airCallActivity.getStartDate());
    }
  }

  @OnClick(R.id.archived_button)
  public void onArchivedClickListener() {

    airCallActivity.setIsCallArchived(true);

    bind(airCallApiCalls.updateAchivedCall(airCallActivity.getIdActivity(), airCallActivity)
        .subscribe(aVoid -> {
              //Save in a DB
              realm.executeTransaction(realm1 -> {
                AirCallActivity realmObject = realm1.copyToRealm(airCallActivity);
              });
            }, this::handleError,
            () -> {
              Snackbar.make(rootLayout, getString(R.string.call_archive),
                  Snackbar.LENGTH_SHORT)
                  .show();
            }));
  }

  private void handleError(Throwable throwable) {
    Timber.e(throwable, "error archiving call");

    Snackbar.make(rootLayout, getString(R.string.call_archive_error),
        Snackbar.LENGTH_SHORT)
        .show();
    airCallActivity.setIsCallArchived(false);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    realm.close();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    setLayoutId(R.layout.fragment_call_detail);
    // Inflate the layout for this fragment
    return super.onCreateView(inflater, container, savedInstanceState);
  }
}
