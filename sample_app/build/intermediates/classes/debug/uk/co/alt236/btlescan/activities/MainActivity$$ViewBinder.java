// Generated code from Butter Knife. Do not modify!
package uk.co.alt236.btlescan.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends uk.co.alt236.btlescan.activities.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427442, "field 'mTvItemCount'");
    target.mTvItemCount = finder.castView(view, 2131427442, "field 'mTvItemCount'");
    view = finder.findRequiredView(source, 16908298, "field 'mList'");
    target.mList = finder.castView(view, 16908298, "field 'mList'");
    view = finder.findRequiredView(source, 16908292, "field 'mEmpty'");
    target.mEmpty = view;
    view = finder.findRequiredView(source, 2131427427, "field 'mestimates_x'");
    target.mestimates_x = finder.castView(view, 2131427427, "field 'mestimates_x'");
    view = finder.findRequiredView(source, 2131427429, "field 'mestimates_y'");
    target.mestimates_y = finder.castView(view, 2131427429, "field 'mestimates_y'");
    view = finder.findRequiredView(source, 2131427438, "field 'merrord'");
    target.merrord = finder.castView(view, 2131427438, "field 'merrord'");
    view = finder.findRequiredView(source, 2131427422, "field 'mSetTime'");
    target.mSetTime = finder.castView(view, 2131427422, "field 'mSetTime'");
    view = finder.findRequiredView(source, 2131427433, "field 'mrealx'");
    target.mrealx = finder.castView(view, 2131427433, "field 'mrealx'");
    view = finder.findRequiredView(source, 2131427435, "field 'mrealy'");
    target.mrealy = finder.castView(view, 2131427435, "field 'mrealy'");
    view = finder.findRequiredView(source, 2131427440, "field 'mcalculate'");
    target.mcalculate = finder.castView(view, 2131427440, "field 'mcalculate'");
    view = finder.findRequiredView(source, 2131427423, "field 'receive'");
    target.receive = finder.castView(view, 2131427423, "field 'receive'");
    view = finder.findRequiredView(source, 2131427418, "field 'Ifwrite'");
    target.Ifwrite = finder.castView(view, 2131427418, "field 'Ifwrite'");
  }

  @Override public void unbind(T target) {
    target.mTvItemCount = null;
    target.mList = null;
    target.mEmpty = null;
    target.mestimates_x = null;
    target.mestimates_y = null;
    target.merrord = null;
    target.mSetTime = null;
    target.mrealx = null;
    target.mrealy = null;
    target.mcalculate = null;
    target.receive = null;
    target.Ifwrite = null;
  }
}
