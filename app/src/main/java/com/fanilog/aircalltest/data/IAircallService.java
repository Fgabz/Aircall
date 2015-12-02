package com.fanilog.aircalltest.data;

import com.fanilog.aircalltest.data.model.AirCallActivity;
import java.util.List;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by fanilo on 11/30/15.
 */
public interface IAircallService {

  @GET("/activities")
  Observable<List<AirCallActivity>> getActivityFeed();

  @POST("/activities/{id}")
  Observable<Void> updateAchivedCall(@Path("id") Double id, @Body AirCallActivity airCallActivity);
}
