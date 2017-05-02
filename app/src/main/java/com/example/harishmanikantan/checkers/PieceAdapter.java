package com.example.harishmanikantan.checkers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import CheckersAttr.*;

/**
 * Created by harishmanikantan on 4/9/17.
 */

public class PieceAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Board board;

    private final int NUM_BOARD_SQUARES = 64;

    /**
     * This method constructs the PieceAdapter
     * @param context
     * @param board
     */
    public PieceAdapter(Context context, Board board) {
        layoutInflater = LayoutInflater.from(context.getApplicationContext());
        this.board = board;
    }

    /**
     * This method returns the total number of board squares
     * @return number of board squares
     */
    @Override
    public int getCount() {
        return NUM_BOARD_SQUARES;
    }

    /**
     * This method returns item at a position
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return null;
    }

    /**
     * This method returns the item id of a position
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * This method returns a view of a board square after setting it to its piece and background color
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = layoutInflater.inflate(R.layout.board_square, null);
        }

        int x = i / 8;
        int y = i % 8;

        ImageView backgroundView = (ImageView) view.findViewById(R.id.background_view);
        ImageView pieceView = (ImageView) view.findViewById(R.id.piece_view);

        if ((x + y) % 2 == 0) {
            backgroundView.setImageResource(R.drawable.brown_square);
        }
        else {
            backgroundView.setImageResource(R.drawable.skin_square);
        }

        String position = board.getPosition(x, y);

        if (position.equals(Board.BLACK_PIECE_NORMAL)) {
            pieceView.setImageResource(R.drawable.normal_black_piece);
        }
        else if (position.equals(Board.BLACK_PIECE_KING)) {
            pieceView.setImageResource(R.drawable.king_black_piece);
        }
        else if (position.equals(Board.RED_PIECE_NORMAL)) {
            pieceView.setImageResource(R.drawable.normal_red_piece);
        }
        else if (position.equals(Board.RED_PIECE_KING)) {
            pieceView.setImageResource(R.drawable.king_red_piece);
        }
        else {
            pieceView.setImageBitmap(null);
        }

        return view;
    }

}
