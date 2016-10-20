package udacitynano.com.br.mustela.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import udacitynano.com.br.mustela.R;
import udacitynano.com.br.mustela.adapter.MeasureAdapter;
import udacitynano.com.br.mustela.model.Measure;
import udacitynano.com.br.mustela.model.User;
import udacitynano.com.br.mustela.util.Constant;


public class DetailActivityFragment extends Fragment {

    private RecyclerView.Adapter mAdapter;



    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);



        Bundle bundle = getActivity().getIntent().getExtras();
        User user = bundle.getParcelable(Constant.INTENT_USER_DETAIL);
        int  projectId = getActivity().getIntent().getIntExtra(Constant.INTENT_USER_PROJECT,1);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler_view_measures);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        Measure msr = new Measure(getActivity());
        mAdapter = new MeasureAdapter(getActivity(), msr.getMeasures(projectId,user.getUserId()));
        rv.setAdapter(mAdapter);


        return view;
    }





}
