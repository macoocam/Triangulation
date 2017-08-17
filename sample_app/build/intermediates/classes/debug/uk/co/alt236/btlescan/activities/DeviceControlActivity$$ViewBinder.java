// Generated code from Butter Knife. Do not modify!
package uk.co.alt236.btlescan.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DeviceControlActivity$$ViewBinder<T extends uk.co.alt236.btlescan.activities.DeviceControlActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427417, "field 'mGattServicesList'");
    target.mGattServicesList = finder.castView(view, 2131427417, "field 'mGattServicesList'");
    view = finder.findRequiredView(source, 2131427409, "field 'mConnectionState'");
    target.mConnectionState = finder.castView(view, 2131427409, "field 'mConnectionState'");
    view = finder.findRequiredView(source, 2131427412, "field 'mGattUUID'");
    target.mGattUUID = finder.castView(view, 2131427412, "field 'mGattUUID'");
    view = finder.findRequiredView(source, 2131427413, "field 'mGattUUIDDesc'");
    target.mGattUUIDDesc = finder.castView(view, 2131427413, "field 'mGattUUIDDesc'");
    view = finder.findRequiredView(source, 2131427414, "field 'mDataAsString'");
    target.mDataAsString = finder.castView(view, 2131427414, "field 'mDataAsString'");
    view = finder.findRequiredView(source, 2131427415, "field 'mDataAsArray'");
    target.mDataAsArray = finder.castView(view, 2131427415, "field 'mDataAsArray'");
  }

  @Override public void unbind(T target) {
    target.mGattServicesList = null;
    target.mConnectionState = null;
    target.mGattUUID = null;
    target.mGattUUIDDesc = null;
    target.mDataAsString = null;
    target.mDataAsArray = null;
  }
}
