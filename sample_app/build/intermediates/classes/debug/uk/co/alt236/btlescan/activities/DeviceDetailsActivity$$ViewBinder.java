// Generated code from Butter Knife. Do not modify!
package uk.co.alt236.btlescan.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DeviceDetailsActivity$$ViewBinder<T extends uk.co.alt236.btlescan.activities.DeviceDetailsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 16908298, "field 'mList'");
    target.mList = finder.castView(view, 16908298, "field 'mList'");
    view = finder.findOptionalView(source, 16908292, null);
    target.mEmpty = view;
  }

  @Override public void unbind(T target) {
    target.mList = null;
    target.mEmpty = null;
  }
}
