package com.fanilog.aircalltest.data;

import android.app.Application;
import com.fanilog.aircalltest.BuildConfig;
import com.fanilog.aircalltest.annotations.PerApp;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;
import io.realm.RealmObject;
import java.io.File;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by fanilo on 11/30/15.
 */

@Module
public class ApiModule {
  private static final long   DISK_CACHE_SIZE     = 50 * 1024 * 1024; // 50MB
  private static final String FOURSQUARE_ENDPOINT = "https://aircall-job.herokuapp.com";

  public static void setUpCache(Application app, OkHttpClient client) {
    // Install an HTTP cache in the application cache directory.
    final File cacheDir = new File(app.getCacheDir(), "http");
    final Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
    client.setCache(cache);
  }

  @Provides
  @PerApp
  OkHttpClient provideOkHttpClient(final Application app) {
    OkHttpClient okHttpClient = new OkHttpClient();
    setUpCache(app, okHttpClient);
    return okHttpClient;
  }

  @Provides
  @PerApp
  Client provideClient(OkHttpClient client) {
    return new OkClient(client);
  }

  @Provides
  @PerApp
  Endpoint provideFoursquareEndPoint() {
    return Endpoints.newFixedEndpoint(FOURSQUARE_ENDPOINT);
  }

  @Provides
  @PerApp
  Gson provideGsonBuilder() {
    return new GsonBuilder()
        .setExclusionStrategies(new ExclusionStrategy() {
          @Override
          public boolean shouldSkipField(FieldAttributes f) {
            return f.getDeclaringClass().equals(RealmObject.class);
          }

          @Override
          public boolean shouldSkipClass(Class<?> clazz) {
            return false;
          }
        })
        .create();
  }

  @Provides
  @PerApp
  RestAdapter proideFoursquareRestAdapter(Endpoint endpoint, Client client, Gson gson) {
    return new RestAdapter.Builder()
        .setEndpoint(endpoint)
        .setConverter(new GsonConverter(gson))
        .setClient(client)
        .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
        .build();
  }

  @Provides
  @PerApp
  IAircallService provideAircallService(RestAdapter restAdapter) {
    return restAdapter.create(IAircallService.class);
  }

  @Provides
  @PerApp
  AirCallApiCalls provideAircallApiCalls(IAircallService aircallService) {
    return new AirCallApiCalls(aircallService);
  }
}
