package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.Citydto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;
@Tag(name = "city")
@RequestMapping("/api")
public interface CityApi {
    @PostMapping(value = APP_ROOT+"/city/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save city", description = "Save valid city ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "city saved successfully "),
            @ApiResponse(responseCode = "400",description = "the object city is not valid !!!")
    })
    public Citydto save(@RequestBody Citydto citydto);
    @GetMapping(value = APP_ROOT+"/city/{city_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one city", description = "search city with the specified  ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "city found"),
            @ApiResponse(responseCode = "404",description = "city not found !!!")
    })
    public Citydto findById(@PathVariable("city_id") Integer id);
    @GetMapping(value = APP_ROOT+"/city/name/{city_name}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one city (name/city_name)", description = "search city with the specified  name ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "city found"),
            @ApiResponse(responseCode = "404",description = "city not found !!!")
    })
    public Citydto findByName(@PathVariable("city_name") String name);
    @GetMapping(value = APP_ROOT+"/city/postalCode/{city_code}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one city (postalCode/city_code)", description = "search city with the specified postal code ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "city found"),
            @ApiResponse(responseCode = "404",description = "city not found !!!")
    })
    public Citydto findByPostalCode(@PathVariable("city_code") String postalCode);
    @GetMapping(value = APP_ROOT+"/city/findAll",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of citys  ", description = "search all citys ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "city list found")

    })
    public List<Citydto> findAll();
    @PutMapping(value = APP_ROOT+"/city/update",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update city... ",description = "you can only update name and postal code.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "city updated successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public Citydto update(@RequestBody Citydto citydto);
    @DeleteMapping(value = APP_ROOT+"/city/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete city by id... ",description = "needs an existing city id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "city deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteById(@PathVariable("id") Integer id);
}
