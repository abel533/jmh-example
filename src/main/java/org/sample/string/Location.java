package org.sample.string;

public class Location {
    private final String city;
    private final String region;
    private final String countryCode;
    private final double longitude;
    private final double latitude;

    public Location(String city, String region, String countryCode, double longitude, double latitude) {
        this.city = city;
        this.region = region;
        this.countryCode = countryCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
