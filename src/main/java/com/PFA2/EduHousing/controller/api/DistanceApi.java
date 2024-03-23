package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.Distancedto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@Tag(name = "distance")
@RequestMapping("/api")
public interface DistanceApi {
    @GetMapping(value = APP_ROOT+"/distance/{distance_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one distance", description = "search distance with the specified  ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "distance found"),
            @ApiResponse(responseCode = "404",description = "distance not found !!!")
    })
    public Distancedto findById(@PathVariable("distance_id") Integer id);
    @GetMapping(value = APP_ROOT+"/distance/findAll",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of distances  ", description = "search all distances ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "distances list found")

    })
    public List<Distancedto> findAll();
    @GetMapping(value = APP_ROOT+"/distance/apartment/{apartment_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of distance", description = "search list of distance with the specified apartment ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "distance list found"),

    })
    public List<Distancedto> findAllByApartmentId(@PathVariable("apartment_id") Integer apartmentId);
    @GetMapping(value = APP_ROOT+"/distance/college/{college_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of distance", description = "search list of distance with the specified college ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "distance list found"),

    })
    public List<Distancedto> findAllByCollegeId(@PathVariable("college_id") Integer collegeId);
    @GetMapping(value = APP_ROOT+"/distance/city/{city_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of distance", description = "search list of distance with the specified city ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "distance list found"),

    })
    public List<Distancedto> findAllByInSameCity(@PathVariable("city_id") Integer cityId);
}
