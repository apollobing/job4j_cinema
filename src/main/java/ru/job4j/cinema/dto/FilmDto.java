package ru.job4j.cinema.dto;

public class FilmDto {

    private int id;

    private String name;

    private String description;

    private int year;

    private String genre;

    private int minimalAge;

    private int durationInMinutes;

    private int fileId;

    public static class Builder {
        private int id;
        private String name;
        private String description;
        private int year;
        private String genre;
        private int minimalAge;
        private int durationInMinutes;
        private int fileId;

        public Builder buildId(int id) {
            this.id = id;
            return this;
        }

        public Builder buildName(String name) {
            this.name = name;
            return this;
        }

        public Builder buildDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder buildYear(int year) {
            this.year = year;
            return this;
        }

        public Builder buildGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder buildMinimalAge(int minimalAge) {
            this.minimalAge = minimalAge;
            return this;
        }

        public Builder buildDurationInMinutes(int durationInMinutes) {
            this.durationInMinutes = durationInMinutes;
            return this;
        }

        public Builder buildFileId(int fileId) {
            this.fileId = fileId;
            return this;
        }

        public FilmDto build() {
            FilmDto filmDto = new FilmDto();
            filmDto.id = id;
            filmDto.name = name;
            filmDto.description = description;
            filmDto.year = year;
            filmDto.genre = genre;
            filmDto.minimalAge = minimalAge;
            filmDto.durationInMinutes = durationInMinutes;
            filmDto.fileId = fileId;
            return filmDto;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
