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

    public PieceAdapter(Context context, Board board) {
        layoutInflater = LayoutInflater.from(context.getApplicationContext());
        this.board = board;
    }

    @Override
    public int getCount() {
        return 64;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

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
