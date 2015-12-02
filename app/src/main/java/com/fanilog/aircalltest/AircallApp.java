package com.fanilog.aircalltest;

import android.app.Application;
import android.content.Context;
import com.fanilog.aircalltest.data.ApiModule;
import timber.log.Timber;

/**
 * Created by fanilo on 11/30/15.
 */
public class AircallApp extends Application {

  private AircallComponent aircallComponent;
  @Override
  public void onCreate() {
    super.onCreate();
    initDagger();
  }

  private void initDagger() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }

    if (aircallComponent == null) {
      aircallComponent = DaggerAircallComponent.builder()
          .aircallModule(new AircallModule(this))
          .apiModule(new ApiModule())
          .build();
    }
  }

  public AircallComponent getAircallComponent() {
    return aircallComponent;
  }

  public static AircallApp getApp(Context ctx) {
    return (AircallApp) ctx.getApplicationContext();
  }


}
