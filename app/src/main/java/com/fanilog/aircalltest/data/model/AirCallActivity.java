package com.fanilog.aircalltest.data.model;

import com.google.gson.annotations.SerializedName;
import io.realm.AirCallActivityRealmProxy;
import io.realm.RealmObject;
import org.parceler.Parcel;

/**
 * Created by fanilo on 11/30/15.
 */
@Parcel(implementations = { AirCallActivityRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { AirCallActivity.class })
public class AirCallActivity extends RealmObject {

  @SerializedName("id")
  private Double idActivity;

  @SerializedName("created_at")
  private String startDate;

  @SerializedName("direction")
  private String directionCallType;

  @SerializedName("from")
  private String callFrom;

  @SerializedName("to")
  private String callTo;

  @SerializedName("duration")
  private String callDuration;

  @SerializedName("via")
  private String aircallNameLine;

  @SerializedName("is_archived")
  private Boolean isCallArchived;

  @SerializedName("call_type")
  private String callType;

  public Double getIdActivity() {
    return idActivity;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getDirectionCallType() {
    return directionCallType;
  }

  public String getCallFrom() {
    return callFrom;
  }

  public String getCallTo() {
    return callTo;
  }

  public String getCallDuration() {
    return callDuration;
  }

  public String getAircallNameLine() {
    return aircallNameLine;
  }

  public Boolean getIsCallArchived() {
    return isCallArchived;
  }

  public String getCallType() {
    return callType;
  }

  public void setIsCallArchived(Boolean isCallArchived) {
    this.isCallArchived = isCallArchived;
  }

  public void setIdActivity(Double idActivity) {
    this.idActivity = idActivity;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public void setDirectionCallType(String directionCallType) {
    this.directionCallType = directionCallType;
  }

  public void setCallFrom(String callFrom) {
    this.callFrom = callFrom;
  }

  public void setCallTo(String callTo) {
    this.callTo = callTo;
  }

  public void setCallDuration(String callDuration) {
    this.callDuration = callDuration;
  }

  public void setAircallNameLine(String aircallNameLine) {
    this.aircallNameLine = aircallNameLine;
  }

  public void setCallType(String callType) {
    this.callType = callType;
  }
}
