package com.learning.projects.guessmovie.data;


import java.util.Objects;

public class Movie {

    private final String name;

    public Movie(String nameInLowerCase) {
        this.name = nameInLowerCase.toLowerCase();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return name.equals(movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name +
                '}';
    }

}
