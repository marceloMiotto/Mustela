package udacitynano.com.br.mustela.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import udacitynano.com.br.mustela.R;
import udacitynano.com.br.mustela.adapter.MeasureAdapter;
import udacitynano.com.br.mustela.model.Measure;
import udacitynano.com.br.mustela.model.User;
import udacitynano.com.br.mustela.util.Constant;
import udacitynano.com.br.mustela.util.DividerItemDecoration;


public class DetailActivityFragment extends Fragment  implements View.OnClickListener{


    private RecyclerView.Adapter mAdapter;
    User user;
    int  projectId;
    RecyclerView rv;


    public DetailActivityFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);



        Bundle bundle = getActivity().getIntent().getExtras();
        user = bundle.getParcelable(Constant.INTENT_USER_DETAIL);
        projectId = getActivity().getIntent().getIntExtra(Constant.INTENT_USER_PROJECT,1);


        rv = (RecyclerView) view.findViewById(R.id.recycler_view_measures);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(getActivity()));

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.e("Debug","notify resume ");

        Measure msr = new Measure(getActivity());
        mAdapter = new MeasureAdapter(getActivity(), msr.getMeasures(projectId,user.getUserId()));
        rv.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View v) {

    }
}
