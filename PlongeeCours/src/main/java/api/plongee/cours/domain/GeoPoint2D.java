/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.cours.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author marin
 */
@Document
public class GeoPoint2D {
    private float latitude ;
    private float longitude;

    public GeoPoint2D(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GeoPoint2D() {
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    
}
