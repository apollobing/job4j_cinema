package ru.job4j.cinema.dto;

import java.time.LocalDateTime;

public class FilmSessionDto {

    private int id;

    private String filmName;

    private String filmDescription;

    private int filmYear;

    private String filmGenre;

    private int filmMinimalAge;

    private int filmDurationInMinutes;

    private int filmFileId;

    private String hallName;

    private int[] hallRowCount;

    private int[] hallPlaceCount;

    private String hallDescription;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int price;

    public static class Builder {
        private int id;
        private String filmName;
        private String filmDescription;
        private int filmYear;
        private String filmGenre;
        private int filmMinimalAge;
        private int filmDurationInMinutes;
        private int filmFileId;
        private String hallName;
        private int[] hallRowCount;
        private int[] hallPlaceCount;
        private String hallDescription;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private int price;

        public Builder buildId(int id) {
            this.id = id;
            return this;
        }

        public Builder buildFilmName(String filmName) {
            this.filmName = filmName;
            return this;
        }

        public Builder buildFilmDescription(String filmDescription) {
            this.filmDescription = filmDescription;
            return this;
        }

        public Builder buildFilmYear(int filmYear) {
            this.filmYear = filmYear;
            return this;
        }

        public Builder buildFilmGenre(String filmGenre) {
            this.filmGenre = filmGenre;
            return this;
        }

        public Builder buildFilmMinimalAge(int filmMinimalAge) {
            this.filmMinimalAge = filmMinimalAge;
            return this;
        }

        public Builder buildFilmDurationInMinutes(int filmDurationInMinutes) {
            this.filmDurationInMinutes = filmDurationInMinutes;
            return this;
        }

        public Builder buildFilmFileId(int filmFileId) {
            this.filmFileId = filmFileId;
            return this;
        }

        public Builder buildHallName(String hallName) {
            this.hallName = hallName;
            return this;
        }

        public Builder buildHallRowCount(int hallRowCount) {
            int[] rows = new int[hallRowCount];
            for (int i = 0; i < hallRowCount; i++) {
                rows[i] = i + 1;
            }
            this.hallRowCount = rows;
            return this;
        }

        public Builder buildHallPlaceCount(int hallPlaceCount) {
            int[] places = new int[hallPlaceCount];
            for (int i = 0; i < hallPlaceCount; i++) {
                places[i] = i + 1;
            }
            this.hallPlaceCount = places;
            return this;
        }

        public Builder buildHallDescription(String hallDescription) {
            this.hallDescription = hallDescription;
            return this;
        }

        public Builder buildStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder buildEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder buildPrice(int price) {
            this.price = price;
            return this;
        }

        public FilmSessionDto build() {
            FilmSessionDto filmSessionDto = new FilmSessionDto();
            filmSessionDto.id = id;
            filmSessionDto.filmName = filmName;
            filmSessionDto.filmDescription = filmDescription;
            filmSessionDto.filmYear = filmYear;
            filmSessionDto.filmGenre = filmGenre;
            filmSessionDto.filmMinimalAge = filmMinimalAge;
            filmSessionDto.filmDurationInMinutes = filmDurationInMinutes;
            filmSessionDto.filmFileId = filmFileId;
            filmSessionDto.hallName = hallName;
            filmSessionDto.hallRowCount = hallRowCount;
            filmSessionDto.hallPlaceCount = hallPlaceCount;
            filmSessionDto.hallDescription = hallDescription;
            filmSessionDto.startTime = startTime;
            filmSessionDto.endTime = endTime;
            filmSessionDto.price = price;
            return filmSessionDto;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmDescription() {
        return filmDescription;
    }

    public void setFilmDescription(String filmDescription) {
        this.filmDescription = filmDescription;
    }

    public int getFilmYear() {
        return filmYear;
    }

    public void setFilmYear(int filmYear) {
        this.filmYear = filmYear;
    }

    public String getFilmGenre() {
        return filmGenre;
    }

    public void setFilmGenre(String filmGenre) {
        this.filmGenre = filmGenre;
    }

    public int getFilmMinimalAge() {
        return filmMinimalAge;
    }

    public void setFilmMinimalAge(int filmMinimalAge) {
        this.filmMinimalAge = filmMinimalAge;
    }

    public int getFilmDurationInMinutes() {
        return filmDurationInMinutes;
    }

    public void setFilmDurationInMinutes(int filmDurationInMinutes) {
        this.filmDurationInMinutes = filmDurationInMinutes;
    }

    public int getFilmFileId() {
        return filmFileId;
    }

    public void setFilmFileId(int filmFileId) {
        this.filmFileId = filmFileId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int[] getHallRowCount() {
        return hallRowCount;
    }

    public void setHallRowCount(int hallRowCount) {
        int[] rows = new int[hallRowCount];
        for (int i = 0; i < hallRowCount; i++) {
            rows[i] = i + 1;
        }
        this.hallRowCount = rows;
    }

    public int[] getHallPlaceCount() {
        return hallPlaceCount;
    }

    public void setHallPlaceCount(int hallPlaceCount) {
        int[] places = new int[hallPlaceCount];
        for (int i = 0; i < hallPlaceCount; i++) {
            places[i] = i + 1;
        }
        this.hallPlaceCount = places;
    }

    public String getHallDescription() {
        return hallDescription;
    }

    public void setHallDescription(String hallDescription) {
        this.hallDescription = hallDescription;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
