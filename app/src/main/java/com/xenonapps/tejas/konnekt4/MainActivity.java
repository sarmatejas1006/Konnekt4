package com.xenonapps.tejas.konnekt4;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // P1 = 0, P2 = 1
    int active = 1;

    //winlist
    String[] winList= {"Player 1", "Player 2"};

    //score
    int[] score = {0,0};

    String score1value = "Player 1: " + Integer.toString(score[0]);
    String score2value = "Player 2: " + Integer.toString(score[1]);


    boolean gameStart = true;

    // 2 = unplayed block
    int[] game = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};

    //winning positions
    int[][] wins = {{0,1,2},{1,2,3},{4,5,6},{5,6,7},{8,9,10},{9,10,11},{12,13,14},{13,14,15}, //horizontal
                   {0,4,8},{4,8,12},{1,5,9},{5,9,13},{2,6,10},{6,10,14},{3,7,11},{7,11,15},  //vertical
                   {0,5,10},{5,10,15},{3,6,9},{6,9,12}, //left diagonal
                   {1,6,11},{4,9,14},{2,5,8},{7,10,13}};//right diagonal

    public void dropIn(View view) {

        ImageView coin = (ImageView) view;
        int tapped = Integer.parseInt(coin.getTag().toString());
        final TextView score1 = (TextView) findViewById(R.id.score1);
        final TextView score2 = (TextView) findViewById(R.id.score2);

        if (game[tapped] == 2 && gameStart) {
            game[tapped] = active;
            coin.setAlpha(0f);

            score1.setText(score1value);
            score2.setText(score2value);

            if (active == 1) {

                coin.setImageResource(R.drawable.blue);
                active = 0;

            } else if (active == 0) {

                coin.setImageResource(R.drawable.green);
                active = 1;
            }
            coin.animate().alpha(1f).setDuration(0);

            //determine wins
            for (int[] win : wins) {

                if (game[win[0]] != 2 && game[win[0]] == game[win[1]] && game[win[1]] == game[win[2]]){

                    score[active]++;

                    MediaPlayer winner = MediaPlayer.create(getApplicationContext(), R.raw.win);
                    winner.start();

                    score1.setText(score1value);
                    score2.setText(score2value);

                    gameStart = false;
                    String message = winList[active] + " has won!";

                    new AlertDialog.Builder(this)
                            .setTitle(message)
                            .setMessage("What would you like to do?")
                            .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    active = 1;

                                    for (int i = 0; i < game.length; i++) {
                                        game[i] = 2;
                                    }
                                    GridLayout board = (GridLayout) findViewById(R.id.board);
                                    for (int i = 0; i < board.getChildCount(); i++) {
                                        ((ImageView) board.getChildAt(i)).setImageResource(0);
                                    }

                                    gameStart = true;
                                    for (int i = 0; i < score.length; i++) {
                                        score[i] = 0;
                                    }
                                    score1.setText(score1value);
                                    score2.setText(score2value);

                                    MediaPlayer start = MediaPlayer.create(getApplicationContext(), R.raw.start);
                                    start.start();

                                }
                            })
                            .setNegativeButton("Continue Series", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which) {
                                    active = 1;

                                    for (int i = 0; i < game.length; i++){
                                        game[i] = 2;
                                    }
                                    GridLayout board = (GridLayout) findViewById(R.id.board);
                                    for (int i = 0; i < board.getChildCount(); i++){
                                        ((ImageView) board.getChildAt(i)).setImageResource(0);
                                    }

                                    gameStart = true;
                                    score1.setText(score1value);
                                    score2.setText(score2value);
                                }

                            })
                            .setIcon(R.drawable.logo)
                            .show();

                } else {

                    boolean over = true;
                    for (int gam : game) {
                        if(gam == 2) over = false;
                    }
                    if (over){

                        new AlertDialog.Builder(this)
                                .setTitle("It's a draw!")
                                .setMessage("What would you like to do?")
                                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        active = 1;

                                        for (int i = 0; i < game.length; i++) {
                                            game[i] = 2;
                                        }
                                        GridLayout board = (GridLayout) findViewById(R.id.board);
                                        for (int i = 0; i < board.getChildCount(); i++) {
                                            ((ImageView) board.getChildAt(i)).setImageResource(0);
                                        }

                                        gameStart = true;
                                        for (int i = 0; i < score.length; i++) {
                                            score[i] = 0;
                                        }
                                        score1.setText(score1value);
                                        score2.setText(score2value);

                                    }
                                })
                                .setNegativeButton("Continue Series", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        active = 1;

                                        for (int i = 0; i < game.length; i++){
                                            game[i] = 2;
                                        }
                                        GridLayout board = (GridLayout) findViewById(R.id.board);
                                        for (int i = 0; i < board.getChildCount(); i++){
                                            ((ImageView) board.getChildAt(i)).setImageResource(0);
                                        }

                                        gameStart = true;
                                        score1.setText(score1value);
                                        score2.setText(score2value);
                                    }

                                })
                                .setIcon(R.drawable.logo)
                                .show();
                        break;
                    }
                }

            }
        }
    }

    //when RESET button is pressed
    public void reset(View view){
        active = 1;

        for (int i = 0; i < game.length; i++){
            game[i] = 2;
        }
        GridLayout board = (GridLayout) findViewById(R.id.board);
        for (int i = 0; i < board.getChildCount(); i++){
            ((ImageView) board.getChildAt(i)).setImageResource(0);
        }
        gameStart = true;

        for (int i = 0; i < score.length; i++){
            score[i] = 0;
        }
        TextView score1 = (TextView) findViewById(R.id.score1);
        TextView score2 = (TextView) findViewById(R.id.score2);

        score1.setText(score1value);
        score2.setText(score2value);

        MediaPlayer start = MediaPlayer.create(getApplicationContext(), R.raw.start);
        start.start();


    }

    @Override
    public void onBackPressed(){

        new AlertDialog.Builder(this)
                .setTitle("Do you really wish to exit the game ?")
                .setIcon(R.drawable.logo)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing. Game continues as is.
                    }
                }).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MediaPlayer mainstart = MediaPlayer.create(this, R.raw.mainstart);
        mainstart.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
