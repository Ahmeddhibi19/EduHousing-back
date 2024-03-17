package com.PFA2.EduHousing.dto;

import com.PFA2.EduHousing.model.Apartment;
import com.PFA2.EduHousing.model.College;
import com.PFA2.EduHousing.model.Distance;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;

@Data
@Builder
public class Distancedto {

    private Integer id;

    private BigDecimal distanceValue;

    @JsonIgnore
    private Apartmentdto apartment;

    @JsonIgnore
    private Collegedto college;

    public static  Distancedto fromEntity(Distance distance){
        if(distance==null){
            return null;
        }
        return Distancedto.builder()
                .id(distance.getId())
                .distanceValue(distance.getDistanceValue())
                .build();
    }

    public static Distance toEntity(Distancedto distancedto){
        if(distancedto==null){
            return null;
        }
        Distance distance = new Distance();
        distance.setId(distancedto.getId());
        distance.setDistanceValue(distancedto.getDistanceValue());
        return distance;
    }

    public static BigDecimal calculateDistance(
            BigDecimal collegeLatitude, BigDecimal collegeLongitude,
            BigDecimal apartmentLatitude, BigDecimal apartmentLongitude
    ){
        final double EARTH_RADIUS_KM = 6371.0;
        double lat1 = Math.toRadians(collegeLatitude.doubleValue());
        double lon1 = Math.toRadians(collegeLongitude.doubleValue());
        double lat2 = Math.toRadians(apartmentLatitude.doubleValue());
        double lon2 = Math.toRadians(apartmentLongitude.doubleValue());

        // Haversine formula
        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;
        double a = Math.pow(Math.sin(dlat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_KM * c;

        return BigDecimal.valueOf(distance);
    }


}
