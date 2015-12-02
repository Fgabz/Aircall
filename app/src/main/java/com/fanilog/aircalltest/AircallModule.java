package com.fanilog.aircalltest;

import android.app.Application;
import android.content.Context;
import com.fanilog.aircalltest.annotations.PerApp;
import dagger.Module;
import dagger.Provides;

/**
 * Created by fanilo on 11/30/15.
 */

@Module
public class AircallModule {

  private AircallApp aircallApp;

  public AircallModule(AircallApp aircallApp) {
    this.aircallApp = aircallApp;
  }

  @PerApp
  @Provides
  AircallApp provideApp() {
    return this.aircallApp;
  }

  @Provides
  @PerApp
  Application provideApplication(AircallApp aircallApp) {
    return aircallApp;
  }

  @Provides
  @PerApp
  Context provideAppContext() {
    return aircallApp.getApplicationContext();
  }
}
