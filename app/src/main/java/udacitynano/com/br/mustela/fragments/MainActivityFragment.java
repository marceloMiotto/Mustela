package udacitynano.com.br.mustela.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import udacitynano.com.br.mustela.R;
import udacitynano.com.br.mustela.adapter.UserAdapter;
import udacitynano.com.br.mustela.model.Project;
import udacitynano.com.br.mustela.model.User;

public class MainActivityFragment extends Fragment {

    private RecyclerView.Adapter mAdapter;
    Button mDialogFragment;


    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);



        //Create the database
        Log.e("Debug", "Debug01");

        //Insert project and user (prototype only)
        User user = new User("Susana", 1.73, 34, "susana",0,"");
        User user1 = new User("Marcelo", 1.74, 42, "marcelo",0,"");
        user.addUser(getActivity());
        user1.addUser(getActivity());

        Project project = new Project("Operation Raul", "2016-10-10", "2016-12-25");
        project.addProject(getActivity());


        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerView_users);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        User usr = new User(getActivity());
        mAdapter = new UserAdapter(getActivity(), usr.getUsers());
        rv.setAdapter(mAdapter);



        return view;
    }




}


