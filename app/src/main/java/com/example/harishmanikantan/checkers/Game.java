package com.example.harishmanikantan.checkers;

import CheckersAttr.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Game extends Activity {

    private Checkers checkers;

    private GridView boardView;
    private PieceAdapter pieceAdapter;
    private TextView winnerMessageView;

    private ImageView redPictureView;
    private ImageView blackPictureView;
    private TextView redNameView;
    private TextView blackNameView;

    private String redPictureUrl;
    private String blackPictureUrl;
    private String redName;
    private String blackName;

    private boolean pieceSelected = false;
    private int selectedPositionX = -1;
    private int selectedPositonY = -1;
    private int numberOfMoves = 0;

    private String gameKey = "";
    public static final String GAME = "GAME";
    public static final String YOUR_ID = "YOUR_ID";
    public static final String OPPONENT_ID = "OPPONENT_ID";

    private DatabaseReference databaseReference;
    private DatabaseReference userReference;
    private FirebaseUser currentUser;

    private String currentTurn = "";
    private String your_id = "";
    private String opponent_id = "";

    private String host = "";

    /**
     * This method is called when the activity is created. It creates the game, board and calls a
     * listener for turns
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setTitle("");

        Intent intent = getIntent();
        gameKey = intent.getStringExtra(GAME);

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("games").child(gameKey);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        blackNameView = (TextView) findViewById(R.id.black_name_view);
        redNameView = (TextView) findViewById(R.id.red_name_view);
        blackPictureView = (ImageView) findViewById(R.id.black_picture_view);
        redPictureView = (ImageView) findViewById(R.id.red_picture_view);

        winnerMessageView = (TextView) findViewById(R.id.winner_message_view);
        winnerMessageView.setVisibility(View.INVISIBLE);

        checkers = new Checkers();

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

        loadHostAndOpponent();
        listenForTurns();
        listenForMoves();
    }

    /**
     * This method loads the host and opponent of the game
     */
    private void loadHostAndOpponent() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                your_id = currentUser.getUid();
                opponent_id = dataSnapshot.child("opponent").getValue(String.class);
                host = your_id;

                if (currentUser.getUid().equals(opponent_id)) {
                    opponent_id = dataSnapshot.child("host").getValue(String.class);
                    host = opponent_id;
                }

                loadPlayersInformation();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method loads the players' information like photo uri and name
     */
    private void loadPlayersInformation() {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users");

        userReference.child(opponent_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (host.equals(opponent_id)) {
                    blackName = dataSnapshot.child("name").getValue(String.class);
                    blackPictureUrl = dataSnapshot.child("photo_uri").getValue(String.class);

                    redName = currentUser.getDisplayName();
                    redPictureUrl = currentUser.getPhotoUrl().toString();
                }
                else {
                    redName = dataSnapshot.child("name").getValue(String.class);
                    redPictureUrl = dataSnapshot.child("photo_uri").getValue(String.class);

                    blackName = currentUser.getDisplayName();
                    blackPictureUrl = currentUser.getPhotoUrl().toString();
                }

                redNameView.setText(redName);
                Picasso.with(redPictureView.getContext()).load(redPictureUrl).into(redPictureView);

                blackNameView.setText(blackName);
                Picasso.with(blackPictureView.getContext()).load(blackPictureUrl).into(blackPictureView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * This method listens for the current turn in the game
     */
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

    /**
     * This method listens for moves and changes the board if it was an opponent's move
     */
    private void listenForMoves() {

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
                }
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

    /**
     * This method changes the String format of a move to a Move object
     * @param value String format of a move
     * @return a Move object
     */
    private Move convertStringToMove(String value) {
        int sourceX = Integer.valueOf(value.substring(0, 1));
        int sourceY = Integer.valueOf(value.substring(2, 3));
        int targetX = Integer.valueOf(value.substring(4, 5));
        int targetY = Integer.valueOf(value.substring(6, 7));

        return new Move(sourceX, sourceY, targetX, targetY);
    }

    /**
     * This method is called when a piece is touched during the player's turn
     * @param position
     */
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

    /**
     * This method converts a move to String format and adds it to the database
     * @param move the Move object
     */
    private void updateDatabase(Move move) {
        String convertedMove = convertMoveToString(move);
        databaseReference.child("moves").child(numberOfMoves + "").setValue(convertedMove);
    }

    /**
     * This method converts a move object to a String format
     * @param move
     * @return
     */
    private String convertMoveToString(Move move) {
        return move.getSourceX() + "," + move.getSourceY() + "-"
                + move.getTargetX() + "," + move.getTargetY();
    }

    /**
     * When the game is over, this method makes the board invisible and displays a message
     * if you won or lost
     */
    private void displayWinner() {
        boardView.setVisibility(View.INVISIBLE);
        String winner = checkers.getWinner();

        winnerMessageView.setVisibility(View.VISIBLE);

        Log.d("Winner", winner);
        Log.d("Winner", your_id);

        if ((winner.equals(Checkers.PLAYER_BLACK_NAME) && your_id.equals(host))
                || (winner.equals(Checkers.PLAYER_RED_NAME) && !your_id.equals(host))) {

            winnerMessageView.setText("YOU WON!");
            updateUserScore();
        }
        else {
            winnerMessageView.setText("YOU LOST!");
        }

    }

    /**
     * When the game is over, this method updates the winner's score by 100
     */
    private void updateUserScore() {
        userReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(currentUser.getUid());

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int score = Integer.valueOf(dataSnapshot.child("total_score").getValue(String.class));
                userReference.child("total_score").setValue((score + 100) + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
