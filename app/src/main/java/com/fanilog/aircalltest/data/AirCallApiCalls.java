package com.fanilog.aircalltest.data;

import com.fanilog.aircalltest.data.model.AirCallActivity;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fanilo on 12/1/15.
 */
public class AirCallApiCalls {

  private IAircallService iAircallService;

  public AirCallApiCalls(IAircallService iAircallService) {
    this.iAircallService = iAircallService;
  }
  
  public Observable<List<AirCallActivity>> getAircallActivityList() {
    Timber.d("Call API");
    return iAircallService.getActivityFeed()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  public Observable<Void> updateAchivedCall(Double id, AirCallActivity
      airCallActivity) {
    return iAircallService.updateAchivedCall(id, airCallActivity)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }
}
