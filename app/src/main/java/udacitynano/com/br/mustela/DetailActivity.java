package udacitynano.com.br.mustela;

import android.content.DialogInterface;
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

import java.util.ArrayList;

import udacitynano.com.br.mustela.fragments.MyDialogFragment;
import udacitynano.com.br.mustela.model.Measure;
import udacitynano.com.br.mustela.model.User;
import udacitynano.com.br.mustela.util.Constant;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener,AppBarLayout.OnOffsetChangedListener {


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

    ArrayList<Measure> measures = new ArrayList<>();

    User user;
    int  projectId;

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


        mUserPhoto  = (ImageView) findViewById(R.id.profile_image);
        mUserName = (TextView) findViewById(R.id.textView_detail_user_name);
        mUserWeight = (TextView) findViewById(R.id.textView_detail_user_weight);
        mUserFatPercentage = (TextView) findViewById(R.id.textView_detail_fat_percentage);
        mUserFatPercentageLost = (TextView) findViewById(R.id.textView_detail_fat_percentage_lost);
        linearLayout = (LinearLayout)findViewById(R.id.title_container);

        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable(Constant.INTENT_USER_DETAIL);
        projectId = getIntent().getIntExtra(Constant.INTENT_USER_PROJECT,1);



        mUserPhoto  = (ImageView) findViewById(R.id.profile_image);
        mUserName = (TextView) findViewById(R.id.textView_detail_user_name);
        mUserWeight = (TextView) findViewById(R.id.textView_detail_user_weight);
        mUserFatPercentage = (TextView) findViewById(R.id.textView_detail_fat_percentage);
        mUserFatPercentageLost = (TextView) findViewById(R.id.textView_detail_fat_percentage_lost);
        linearLayout = (LinearLayout)findViewById(R.id.title_container);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        Log.e("Debug","user name "+user.getUserName());

        mUserName.setText(user.getUserName());
        mUserPhoto.setImageDrawable(ContextCompat.getDrawable(this,this.getResources().getIdentifier(user.getUserPhotoPath(),"drawable",this.getPackageName())));

        linearLayout.setBackgroundColor(ContextCompat.getColor(this,this.getResources().getIdentifier(user.getColorName(),"color",this.getPackageName())));
        toolbar.setBackgroundColor(ContextCompat.getColor(this,this.getResources().getIdentifier(user.getColorName(),"color",this.getPackageName())));
        appbarLayout.setBackgroundColor(ContextCompat.getColor(this,this.getResources().getIdentifier(user.getColorName(),"color",this.getPackageName())));

        Measure msr = new Measure(this);
        measures = msr.getLast2Measures(1,user.getUserId());//TODO remove hardcoded project id
        if (measures.size() > 0) {
            mUserWeight.setText(String.format(this.getString(R.string.detail_user_weight), String.valueOf(measures.get(0).getMeasureWeight())));
            mUserFatPercentage.setText(String.format(this.getString(R.string.detail_user_fat_percentage), String.valueOf(measures.get(0).getMeasureFatPercentage())));
            mUserFatPercentageLost.setText(String.format(this.getString(R.string.detail_fat_percentage_lost), String.valueOf(msr.getFatPercentageLost(measures))));
        }



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
                editNameDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Log.e("Debug","funcionou o callback");

                        Measure msr = new Measure(getApplication());
                        measures = msr.getLast2Measures(1,user.getUserId());//TODO remove hardcoded project id
                        if(measures.size() > 0) {
                            mUserWeight.setText(String.format(getString(R.string.detail_user_weight), String.valueOf(measures.get(0).getMeasureWeight())));
                            mUserFatPercentage.setText(String.format(getString(R.string.detail_user_fat_percentage), String.valueOf(measures.get(0).getMeasureFatPercentage())));
                            mUserFatPercentageLost.setText(String.format(getString(R.string.detail_fat_percentage_lost), String.valueOf(msr.getFatPercentageLost(measures))));
                        }

                    }
                });
                editNameDialog.show(manager, "fragment_edit_name");

            }
        });


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
