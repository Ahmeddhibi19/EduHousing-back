package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.Apartmentdto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@Tag(name = "apartment")
@RequestMapping("/api")
public interface ApartmentApi {
    @PostMapping(value = APP_ROOT+"/apartment/create/{homeowner_id}/{city_id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save apartment", description = "Save apartment with the specified homeowner ID and college ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment saved successfully "),
            @ApiResponse(responseCode = "400",description = "the object apartment is not valid !!!")
    })
    public Apartmentdto save(@RequestBody Apartmentdto apartmentdto,@PathVariable("homeowner_id") Integer homeownerId,@PathVariable("city_id") Integer cityId);

    @GetMapping(value = APP_ROOT+"/apartment/{apartment_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one apartment", description = "search apartment with the specified  ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment found"),
            @ApiResponse(responseCode = "404",description = "apartment not found !!!")
    })
    public Apartmentdto findById(@PathVariable("apartment_id") Integer id);
    @GetMapping(value = APP_ROOT+"/apartment/city/{city_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment", description = "search apartments with the specified  college iD ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<Apartmentdto> findByCityId(@PathVariable("city_id") Integer cityId);
    @GetMapping(value = APP_ROOT+"/apartment/homeowner/{homeowner_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment", description = "search apartments with the specified  homeowner iD ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<Apartmentdto> findByHomeownerId(@PathVariable("homeowner_id") Integer id);
    @GetMapping(value = APP_ROOT+"/apartment/type/{type}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment", description = "search apartments with the specified type")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<Apartmentdto> findAllByType(@PathVariable("type") String type);
    @GetMapping(value = APP_ROOT+"/apartment/city_homeowner/{city_id}/{homeowner_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment (city_id/homeowner_id)", description = "search apartments with the specified homeowner id and city id")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<Apartmentdto> findAllByCityIdAndHomeownerId(@PathVariable("city_id") Integer cityId,@PathVariable("homeowner_id") Integer homeownerId);
    @GetMapping(value = APP_ROOT+"/apartment/city_type/{city_id}/{type}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment (city_id/type)", description = "search apartments with the specified type and city id")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<Apartmentdto> findAllByCityIdAndType(@PathVariable("city_id") Integer cityId,@PathVariable("type") String type);
    @GetMapping(value = APP_ROOT+"/apartment/homeowner_type/{homeowner_id}/{type}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment (homeowner_id/type)", description = "search apartments with the specified type and homeowner id")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<Apartmentdto> findAllByHomeownerIdAndType(@PathVariable("homeowner_id") Integer id,@PathVariable("type") String type);
    @GetMapping(value = APP_ROOT+"/apartment/isRented/{homeowner_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment (homeowner_id/true)", description = "search apartments with homeowner id and rented")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })

    public List<Apartmentdto> findAllByHomeownerIdAndIsRented(@PathVariable("homeowner_id") Integer homeownerId);
    @GetMapping(value = APP_ROOT+"/apartment/notRented/{city_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment (city/false)", description = "search apartments with city id and not rented")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<Apartmentdto> findAllByCityIdNotIsRented(@PathVariable("city_id") Integer cityId);
    @DeleteMapping(value = APP_ROOT+"/apartment/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete apartment by id... ",description = "needs an existing apartment id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "apartment deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteById(@PathVariable("id") Integer id);

    @PutMapping(value = APP_ROOT+"/apartment/update",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update apartment... ",description = "you can only update latitude,longitude,type,address and description.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "apartment updated successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public Apartmentdto update(@RequestBody Apartmentdto apartmentdto);
    @GetMapping(value = APP_ROOT+"/apartment/RatingLessThan/{city_id}/{rating}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment (city/rating)", description = "search apartments with city id and less than specific rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })

    public List<Apartmentdto> findApartmentByRatingLessThanAndCityId(@PathVariable("rating") Double rating,@PathVariable("city_id") Integer cityId);
    @GetMapping(value = APP_ROOT+"/apartment/RatingGreaterThan/{city_id}/{rating}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment (city/rating)", description = "search apartments with city id and greater than specific rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<Apartmentdto> findApartmentByRatingGreaterThanAndCityId(@PathVariable("rating") Double rating,@PathVariable("city_id") Integer cityId);
    @GetMapping(value = APP_ROOT+"/apartment/collegeid/{college_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment ", description = "search apartments in the same city with the related college id")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<Apartmentdto> findApartmentsByCollegeAndSameCity(@PathVariable("college_id") Integer collegeId);
    @GetMapping(value = APP_ROOT+"/apartment/college_distance_less_than/{college_id}/{distance_value}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment (college/distance) ", description = "search apartments by college id and distance is less than specific value")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<Apartmentdto> findApartmentsByCollegeAndDistanceLessThan(@PathVariable("college_id") Integer collegeId,@PathVariable("distance_value") BigDecimal distanceValue);
    @GetMapping(value = APP_ROOT+"/apartment/city/{college_id}/{distance_value}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment (college/distance) ", description = "search apartments in the same city with the related college id and distance is less than specific value")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<Apartmentdto> findApartmentsByCollegeAndCityAndDistanceLessThan(@PathVariable("college_id") Integer collegeId,@PathVariable("distance_value") BigDecimal distanceValue);
    @GetMapping(value = APP_ROOT+"/apartment/findAll",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of apartment  ", description = "search all apartments ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<Apartmentdto> findAll();
}
