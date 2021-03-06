package org.schulcloud.mobile.ui.start;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;

import org.schulcloud.mobile.R;
import org.schulcloud.mobile.ui.base.BaseActivity;
import org.schulcloud.mobile.ui.dashboard.DashboardActivity;
import org.schulcloud.mobile.ui.files.FileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends BaseActivity implements StartMvpView {

    @BindView(R.id.cloudy_icon)
    AwesomeTextView cloudIcon;
    @BindView(R.id.start_layout)
    RelativeLayout relativeLayout;
    @BindView(R.id.text_logo)
    TextView logoText;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, FileActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        this.showDisplayAnimation();

        //this.goToMain();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void goToMain() {
        Handler handler = new Handler();
        Context context = this;
        handler.postDelayed(() -> {
            Intent intent = new Intent(context, DashboardActivity.class);
            context.startActivity(intent);
            finish();
        }, 500);
    }

    @Override
    public void showDisplayAnimation() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        relativeLayout.clearAnimation();
        relativeLayout.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation cloudAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse);
                cloudAnim.reset();
                cloudIcon.clearAnimation();
                cloudIcon.startAnimation(cloudAnim);
                StartActivity.this.goToMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        cloudIcon.setTextColor(ContextCompat.getColor(this, R.color.hpiRed));
        cloudIcon.clearAnimation();
        cloudIcon.startAnimation(anim);
    }

    @Override
    public void goToSignIn() {

    }


}
