package com.example.harishmanikantan.checkers;

import CheckersAttr.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Game extends Activity {

    private Checkers checkers;

    private GridView boardView;
    private PieceAdapter pieceAdapter;

    private boolean pieceSelected = false;
    private int selectedPositionX = -1;
    private int selectedPositonY = -1;
    private int numberOfMoves = 0;

    private String gameKey = "";
    public static final String GAME = "GAME";
    public static final String YOUR_ID = "YOUR_ID";
    public static final String OPPONENT_ID = "OPPONENT_ID";

    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    private boolean flag = true;
    private String currentTurn = "";
    private String your_id = "";
    private String opponent_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        gameKey = intent.getStringExtra(GAME);

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("games").child(gameKey);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        checkers = new Checkers();

        createGame();

        pieceAdapter = new PieceAdapter(this, checkers.board);
        boardView = (GridView) findViewById(R.id.board_view);
        boardView.setAdapter(pieceAdapter);

        boardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("Turn", "YourID " + your_id);
                Log.d("Turn", "CurrentTurn " + currentTurn);
                if (currentTurn.equals(your_id)) {
                    Log.d("Turn", "Test");
                    moveOnTouch(position);
                }
            }
        });

        listenForTurns();
        loadHostAndOpponent();
    }

    private void loadHostAndOpponent() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                your_id = currentUser.getUid();
                opponent_id = dataSnapshot.child("opponent").getValue(String.class);

                if (currentUser.getUid().equals(opponent_id)) {
                    opponent_id = dataSnapshot.child("host").getValue(String.class);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void listenForTurns() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentTurn = dataSnapshot.child("turn").getValue(String.class);
                Log.d("Turn", currentTurn);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createGame() {

        databaseReference.child("moves").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (currentTurn.equals(your_id)) {
                    numberOfMoves++;
                    Move move = convertStringToMove(dataSnapshot.getValue(String.class));

                    checkers.play(move);

                    pieceSelected = false;
                    pieceAdapter.notifyDataSetChanged();

                    if (!checkers.isGameOn()) {
                        displayWinner();
                    }

                    boardView.setClickable(true);
                    //flag = false;
                }
                /*else {
                    flag = true;
                    boardView.setClickable(false);
                }*/
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private Move convertStringToMove(String value) {
        int sourceX = Integer.valueOf(value.substring(0, 1));
        int sourceY = Integer.valueOf(value.substring(2, 3));
        int targetX = Integer.valueOf(value.substring(4, 5));
        int targetY = Integer.valueOf(value.substring(6, 7));

        return new Move(sourceX, sourceY, targetX, targetY);
    }

    private void moveOnTouch(int position) {
        int targetX = position / Board.DIMENSION;
        int targetY = position % Board.DIMENSION;

        if (pieceSelected) {
            Move move = new Move(selectedPositionX, selectedPositonY, targetX, targetY);

            if (checkers.isValidMove(move)) {

                Log.d("Game", "Valid move");
                Toast.makeText(this, "Piece selected " + targetX + "," + targetY,Toast.LENGTH_SHORT).show();

                numberOfMoves++;
                checkers.play(move);

                pieceSelected = false;
                pieceAdapter.notifyDataSetChanged();
                databaseReference.child("turn").setValue(opponent_id);

                if (!checkers.isGameOn()) {
                    displayWinner();
                }

                flag = false;
                boardView.setClickable(false);
                updateDatabase(move);
            }
            else if (checkers.isValidPieceSelection(targetX, targetY)) {
                selectedPositionX = targetX;
                selectedPositonY = targetY;
            }
        }
        else if (checkers.isValidPieceSelection(targetX, targetY)){
            Toast.makeText(this, "Piece selected " + targetX + "," + targetY,Toast.LENGTH_SHORT).show();
            pieceSelected = true;
            selectedPositionX = targetX;
            selectedPositonY = targetY;
        }

    }

    private void updateDatabase(Move move) {
        String convertedMove = convertMoveToString(move);
        databaseReference.child("moves").child(numberOfMoves + "").setValue(convertedMove);
    }

    private String convertMoveToString(Move move) {
        return move.getSourceX() + "," + move.getSourceY() + "-"
                + move.getTargetX() + "," + move.getTargetY();
    }

    private void displayWinner() {
        boardView.setVisibility(View.INVISIBLE);
        String winner = checkers.getWinner();
    }

}
