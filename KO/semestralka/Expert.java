/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expert;

import java.util.Scanner;

/**
 *
 * @author michal
 */
public class Expert {


  boolean[][] goal_board = {{true,true,true,true,true},
			    {true,true,true,true,true},
			    {true,true,true,true,true},
			    {true,true,true,true,true},
			    {true,true,true,true,true}};

  // The hint matrix was computed in Maple; multiplying it by
  // a vector representing the current board gives a vector
  // representing the moves necessary to produce the current
  // board; since everything is done mod 2, this also gives the
  // moves necessary to solve the game.

  int[][] hint_matrix = {
    { 0,1,1,1,0,0,0,1,0,1,0,0,0,1,1,0,0,0,0,1,0,0,0 },
    { 1,1,0,1,1,0,1,0,0,0,0,0,1,1,1,0,0,0,1,0,0,0,0 },
    { 1,0,1,1,1,1,0,1,1,0,0,0,1,1,0,1,1,1,1,1,0,1,0 },
    { 1,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,1 },
    { 0,1,1,0,1,1,0,0,0,0,1,0,1,0,1,0,0,1,0,1,1,1,0 },
    { 0,0,1,0,1,0,1,1,0,1,0,0,1,0,0,0,0,0,1,1,0,0,0 },
    { 0,1,0,1,0,1,1,0,1,1,0,0,0,1,0,1,1,1,0,0,0,1,0 },
    { 1,0,1,0,0,1,0,1,1,0,0,0,0,0,1,1,0,1,0,1,1,0,1 },
    { 0,0,1,0,0,0,1,1,1,0,1,0,0,1,1,1,0,0,1,0,0,1,1 },
    { 1,0,0,0,0,1,1,0,0,0,1,0,1,0,1,0,1,1,0,1,0,0,1 },
    { 0,0,0,0,1,0,0,0,1,1,0,0,1,0,1,1,1,1,1,0,0,1,0 },
    { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,1 },
    { 0,1,1,0,1,1,0,0,0,1,1,0,1,1,0,0,0,1,0,0,1,1,0 },
    { 1,1,1,0,0,0,1,0,1,0,0,0,1,1,1,0,0,0,1,0,0,0,0 },
    { 1,1,0,0,1,0,0,1,1,1,1,0,0,1,1,1,1,0,1,0,1,0,0 },
    { 0,0,1,0,0,0,1,1,1,0,1,0,0,0,1,1,0,1,0,1,1,0,1 },
    { 0,0,1,1,0,0,1,0,0,1,1,1,0,0,1,0,1,1,1,0,0,0,1 },
    { 0,0,1,0,1,0,1,1,0,1,1,0,1,0,0,1,1,0,1,1,1,0,0 },
    { 0,1,1,0,0,1,0,0,1,0,1,0,0,1,1,0,1,1,1,0,0,0,1 },
    { 1,0,1,0,1,1,0,1,0,1,0,0,0,0,0,1,0,1,0,1,1,0,1 },
    { 0,0,0,1,1,0,0,1,0,0,0,1,1,0,1,1,0,1,0,1,1,1,0 },
    { 0,0,1,1,1,0,1,0,1,0,1,1,1,0,0,0,0,0,0,0,1,1,1 },
    { 0,0,0,1,0,0,0,1,1,1,0,1,0,0,0,1,1,0,1,1,0,1,0 },
  };

  // These magic vectors allow us to find a shortest possible
  // solution.

  int[] n1 = {1,0,1,0,1,1,0,1,0,1,0,0,0,0,0,1,0,1,0,1,1,0,1,0,1};
  int[] n2 = {1,1,0,1,1,0,0,0,0,0,1,1,0,1,1,0,0,0,0,0,1,1,0,1,1};

  private void acpy (int[] src, int[] dest)
  {
    int i;
    for (i=0; i<src.length; i++) {
      dest[i] = src[i];
    }
  }

  private void addto (int[] src, int[] v)
  {
    int i;
    for (i=0; i<src.length; i++) {
      src[i] = (src[i] + v[i]) %2 ;
    }
  }

  private int wt (int[] v)
  {
    int i,t=0;
    for (i=0; i < v.length; i++) {
      t = t + v[i];
    }
    return(t);
  }


  public int[][] hint(int[][] current_board) {
    if(!current_solvable(current_board)) {
        return new int[0][0];
    }

    int[] current_state = new int[23];
    int[] hint_vector = new int[25];
    int[] best = new int[25];
    int[] next = new int[25];
    int i,j;

    for (i=0; i<25; i++) {
      hint_vector[i] = 0;
    }
    for (i=0; i<23; i++) {
      if (current_board[i%5][i/5] == 1) {
	current_state[i] = 0; }
      else {
	current_state[i] = 1; }
    }
    for (i=0; i<23; i++) {
      for (j=0; j<23; j++) {
	hint_vector[i] =
	  (hint_vector[i] + current_state[j]*hint_matrix[i][j])%2;
      }
    }

    // Now we have a working hint vector, but we test h+n1, h+n2
    // and h+n1+n2 to see which has the lowest weight, giving a
    // shortest solution.

    acpy(hint_vector,best);
    acpy(hint_vector,next);
    addto(next,n1);
    if ( wt(next) < wt(best) ) {
      acpy(next,best);
    }
    acpy(hint_vector,next);
    addto(next,n2);
    if ( wt(next) < wt(best) ) {
      acpy(next,best);
    }
    acpy(hint_vector,next);
    addto(next,n1);
    addto(next,n2);
    if ( wt(next) < wt(best) ) {
      acpy(next,best);
    }

    int N = 0;
    for (i=0; i<25; i++) {
      if (best[i] == 1) {
          N++;
      }
    }

    int k = 0;
    int[][] result = new int[5][5];
    for (i=0; i<25; i++) {
        if(best[i] == 1) {
            result[i%5][i/5] = 1;
        } else {
            result[i%5][i/5] = 0;
        }
    }
    return result;
  }

  boolean current_solvable(int[][] current_board)
  {
    int[] current_state = new int[25];
    int i,j,dotprod;

    for (i=0; i<25; i++) {
      if (current_board[i%5][i/5] == 1) {
	current_state[i] = 0; }
      else {
	current_state[i] = 1; }
    }

    dotprod = 0;
    for (i=0; i<25; i++) {
      dotprod = (dotprod + current_state[i]*n1[i])%2;
    }

    if (dotprod != 0) { return(false); }

    dotprod = 0;
    for (i=0; i<25; i++) {
      dotprod = (dotprod + current_state[i]*n2[i])%2;
    }

    if (dotprod != 0) { return(false); }
    else { return (true); }
  }
}