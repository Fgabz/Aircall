package com.fanilog.aircalltest.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by fanilo on 11/30/15.
 */
public class BaseActivity extends AppCompatActivity {
  private final CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

  }

  @Override
  protected void onDestroy() {
    compositeSubscription.unsubscribe();
    ButterKnife.unbind(this);
    super.onDestroy();
  }

  protected Subscription bind(Subscription subscription) {
    compositeSubscription.add(subscription);
    return subscription;
  }
}
