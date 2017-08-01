// Generated code from Butter Knife. Do not modify!
package uk.co.alt236.btlescan.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends uk.co.alt236.btlescan.activities.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427443, "field 'mTvItemCount'");
    target.mTvItemCount = finder.castView(view, 2131427443, "field 'mTvItemCount'");
    view = finder.findRequiredView(source, 16908298, "field 'mList'");
    target.mList = finder.castView(view, 16908298, "field 'mList'");
    view = finder.findRequiredView(source, 16908292, "field 'mEmpty'");
    target.mEmpty = view;
    view = finder.findRequiredView(source, 2131427428, "field 'mestimates_x'");
    target.mestimates_x = finder.castView(view, 2131427428, "field 'mestimates_x'");
    view = finder.findRequiredView(source, 2131427430, "field 'mestimates_y'");
    target.mestimates_y = finder.castView(view, 2131427430, "field 'mestimates_y'");
    view = finder.findRequiredView(source, 2131427439, "field 'merrord'");
    target.merrord = finder.castView(view, 2131427439, "field 'merrord'");
    view = finder.findRequiredView(source, 2131427423, "field 'mSetTime'");
    target.mSetTime = finder.castView(view, 2131427423, "field 'mSetTime'");
    view = finder.findRequiredView(source, 2131427434, "field 'mrealx'");
    target.mrealx = finder.castView(view, 2131427434, "field 'mrealx'");
    view = finder.findRequiredView(source, 2131427436, "field 'mrealy'");
    target.mrealy = finder.castView(view, 2131427436, "field 'mrealy'");
    view = finder.findRequiredView(source, 2131427441, "field 'mcalculate'");
    target.mcalculate = finder.castView(view, 2131427441, "field 'mcalculate'");
    view = finder.findRequiredView(source, 2131427424, "field 'receive'");
    target.receive = finder.castView(view, 2131427424, "field 'receive'");
    view = finder.findRequiredView(source, 2131427419, "field 'Ifwrite'");
    target.Ifwrite = finder.castView(view, 2131427419, "field 'Ifwrite'");
    view = finder.findRequiredView(source, 2131427418, "field 'loopChk'");
    target.loopChk = finder.castView(view, 2131427418, "field 'loopChk'");
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
    target.loopChk = null;
  }
}
