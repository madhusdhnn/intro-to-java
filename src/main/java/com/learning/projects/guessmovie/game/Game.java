package com.learning.projects.guessmovie.game;

import com.learning.projects.guessmovie.data.Movie;

public interface Game {

    void start(Movie pickedMovie);

    boolean guess(char ch);

    Result stop();

}
