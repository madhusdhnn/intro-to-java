package com.learning.guessmovie.game;

import com.learning.guessmovie.data.Movie;

public interface Game {

    void start(Movie pickedMovie);

    boolean guess(char ch);

    Result stop();

}
