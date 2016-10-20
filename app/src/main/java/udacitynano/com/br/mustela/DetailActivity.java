package udacitynano.com.br.mustela;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import udacitynano.com.br.mustela.fragments.MyDialogFragment;
import udacitynano.com.br.mustela.model.User;
import udacitynano.com.br.mustela.util.Constant;

public class DetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener , MyDialogFragment.UserNameListener,View.OnClickListener {

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;
    private ImageView mProfileImage;
    private int mMaxScrollSize;

    ImageView mUserPhoto;
    TextView mUserName;
    TextView  mUserWeight;
    TextView  mUserFatPercentage;
    TextView  mUserFatPercentageLost;
    LinearLayout linearLayout;

    public static String DETAIL_VIEW = "DETAIL_VIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mProfileImage = (ImageView) findViewById(R.id.profile_image);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mUserPhoto  = (ImageView) findViewById(R.id.profile_image);
        mUserName = (TextView) findViewById(R.id.textView_detail_user_name);
        mUserWeight = (TextView) findViewById(R.id.textView_detail_user_weight);
        mUserFatPercentage = (TextView) findViewById(R.id.textView_detail_fat_percentage);
        mUserFatPercentageLost = (TextView) findViewById(R.id.textView_detail_fat_percentage_lost);
        linearLayout = (LinearLayout) findViewById(R.id.title_container);

        Bundle bundle = getIntent().getExtras();
        User user = bundle.getParcelable(Constant.INTENT_USER_DETAIL);
        int  projectId = getIntent().getIntExtra(Constant.INTENT_USER_PROJECT,1);


        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        Log.e("Debug","user name "+user.getUserName());

        mUserName.setText(user.getUserName());
        mUserPhoto.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(user.getUserPhotoPath(),"drawable",this.getPackageName())));

        linearLayout.setBackgroundColor(ContextCompat.getColor(this,this.getResources().getIdentifier(user.getColorName(),"color",this.getPackageName())));
        toolbar.setBackgroundColor(ContextCompat.getColor(this,this.getResources().getIdentifier(user.getColorName(),"color",this.getPackageName())));
        appbarLayout.setBackgroundColor(ContextCompat.getColor(this,this.getResources().getIdentifier(user.getColorName(),"color",this.getPackageName())));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // close existing dialog fragments
                FragmentManager manager = getSupportFragmentManager();
                Fragment frag = manager.findFragmentByTag("fragment_edit_name");
                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }

                MyDialogFragment editNameDialog = new MyDialogFragment();
                editNameDialog.show(manager, "fragment_edit_name");


                }
        });


    }

    @Override
    public void onFinishUserDialog(String user) {
        Toast.makeText(this, "Hello, " + user, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            mProfileImage.animate().scaleY(0).scaleX(0).setDuration(200).start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }


    @Override
    public void onClick(View v) {

    }
}
