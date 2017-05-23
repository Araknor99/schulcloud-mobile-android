package org.schulcloud.mobile.ui.homework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import org.schulcloud.mobile.R;
import org.schulcloud.mobile.data.model.Homework;
import org.schulcloud.mobile.data.sync.HomeworkSyncService;
import org.schulcloud.mobile.ui.base.BaseActivity;
import org.schulcloud.mobile.ui.signin.SignInActivity;
import org.schulcloud.mobile.util.DialogFactory;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeworkActivity extends BaseActivity implements HomeworkMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "org.schulcloud.mobile.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    HomeworkPresenter mHomeworkPresenter;
    @Inject
    HomeworkAdapter mHomeworkAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, HomeworkActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        //setContentView(R.layout.activity_main);

        LayoutInflater inflater =
                (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_homework, null, false);
        mDrawer.addView(contentView, 0);
        ButterKnife.bind(this);


        mRecyclerView.setAdapter(mHomeworkAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHomeworkPresenter.attachView(this);
        mHomeworkPresenter.checkSignedIn(this);

        mHomeworkPresenter.loadHomework();

        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(HomeworkSyncService.getStartIntent(this));
        }
    }

    @Override
    protected void onDestroy() {
        mHomeworkPresenter.detachView();
        super.onDestroy();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showHomework(List<Homework> homeworks) {
        mHomeworkAdapter.setHomework(homeworks);
        mHomeworkAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, "Leider gab es ein Problem beim fetchen der Hausaufgaben")
                .show();
    }

    @Override
    public void showHomeworkEmpty() {
        mHomeworkAdapter.setHomework(Collections.emptyList());
        mHomeworkAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Momentan gibt es keine Hausaufgaben mehr...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void goToSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        this.startActivity(intent);
    }
}
