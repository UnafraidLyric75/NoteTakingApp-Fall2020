package com.example.notetakingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesBody#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesBody extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer itemId;
    private  Boolean isThereData = false;

    public NotesBody() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotesBody.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesBody newInstance(String param1, String param2) {
        NotesBody fragment = new NotesBody();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes_body, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        Bundle bundle = getArguments();

        EditText noteTitle = view.findViewById(R.id.NoteTitle);
        EditText noteBody = view.findViewById(R.id.NoteBody);

        if(bundle != null){
            itemId = bundle.getInt("ID");
            noteTitle.setText(databaseHelper.getNoteTitle(itemId));
            noteBody.setText(databaseHelper.getNoteBody(itemId));
            isThereData = true;
        }

        // Save
        Button saveNote = (Button) view.findViewById(R.id.btnSave);
        saveNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                NotesModel notesModel;
                try {
                    notesModel = new NotesModel(-1, noteTitle.getText().toString(), noteBody.getText().toString());

                    //Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();

                    boolean success = databaseHelper.addOne(notesModel);
                    Toast.makeText(getActivity(), "Save successful: " + success, Toast.LENGTH_SHORT).show();

                    if(isThereData){
                        databaseHelper.deleteOne(itemId);
                    }
                }
                catch(Exception e){
                    Toast.makeText(getActivity(), "Please make sure both fields have been typed in", Toast.LENGTH_LONG).show();
                }

                List<NotesModel> allNotes = databaseHelper.getAllNotes();

                //Toast.makeText(getActivity(), allNotes.toString(), Toast.LENGTH_LONG).show();

                NotesOverview notesOverview = new NotesOverview();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.mainLayout, notesOverview, notesOverview.getTag())
                        .commit();
            }
        });

        // Delete
        Button deleteNote = (Button) view.findViewById(R.id.btnDelete);
        deleteNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try {
                    databaseHelper.deleteOne(itemId);
                } catch(Exception e){
                    Toast.makeText(getActivity(), "Didn't delete correctly", Toast.LENGTH_LONG).show();
                }

                NotesOverview notesOverview = new NotesOverview();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.mainLayout, notesOverview, notesOverview.getTag())
                        .commit();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}