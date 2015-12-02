package com.fanilog.aircalltest;

import com.fanilog.aircalltest.annotations.PerApp;
import com.fanilog.aircalltest.data.ApiModule;
import com.fanilog.aircalltest.ui.home.CallDetailFragment;
import com.fanilog.aircalltest.ui.home.CallListFragment;
import dagger.Component;

/**
 * Created by fanilo on 11/30/15.
 */

@PerApp
@Component(
    modules = {
        AircallModule.class,
        ApiModule.class
    }
)
public interface AircallComponent {
  void inject(CallListFragment callListFragment);
  void inject(CallDetailFragment callDetailFragment);
}
