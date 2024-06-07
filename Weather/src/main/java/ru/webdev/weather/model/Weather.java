package ru.webdev.weather.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Weather {

    @Id
    @GeneratedValue
    @Column(insertable = false, updatable = false)
    private int id;

    @Embedded
    private Coord coord;

    @ElementCollection
    private List<WeatherInfo> weather;

    private String base;

    @Embedded
    private MainWeather main;

    private int visibility;

    @Embedded
    private Wind wind;

    @Embedded
    private Clouds clouds;

    private long dt;

    @Embedded
    private Sys sys;

    private int timezone;

    private String name;

    private int cod;

    private String errorMessage;

    public int getId() {
        return id;
    }

    public Coord getCoord() {
        return coord;
    }

    public List<WeatherInfo> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public MainWeather getMain() {
        return main;
    }

    public int getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public long getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public void setWeather(List<WeatherInfo> weather) {
        this.weather = weather;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setMain(MainWeather main) {
        this.main = main;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
