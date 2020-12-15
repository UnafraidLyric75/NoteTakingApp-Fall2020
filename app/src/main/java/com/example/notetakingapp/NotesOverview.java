package com.example.notetakingapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesOverview#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class NotesOverview extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotesOverview.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesOverview newInstance(String param1, String param2) {
        NotesOverview fragment = new NotesOverview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NotesOverview() {
        // Required empty public constructor
    }

    private static final String TAG = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes_overview, container, false);

        // get database acces point
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

        ListView notesList = (ListView) v.findViewById(R.id.notesList);
        List<NotesModel> allNotes = databaseHelper.getAllNotes();
        ArrayAdapter notesArrayAdapter = new ArrayAdapter<NotesModel>(getActivity(), android.R.layout.simple_list_item_1, allNotes);
        notesList.setAdapter(notesArrayAdapter);


        Button createNewNote = (Button) v.findViewById(R.id.btnAddNew);
        createNewNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                NotesBody notesBody = new NotesBody();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.mainLayout, notesBody, notesBody.getTag())
                        .commit();
            }
        });

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //String title = adapterView.getItemAtPosition(position).toString();
                NotesBody notesBody = new NotesBody();

                //Integer noteId = databaseHelper.getId(title);
                Integer noteId = position;

                Bundle bundle = new Bundle();
                bundle.putInt("ID", noteId);
                notesBody.setArguments(bundle);

                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.mainLayout, notesBody, notesBody.getTag())
                        .commit();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}