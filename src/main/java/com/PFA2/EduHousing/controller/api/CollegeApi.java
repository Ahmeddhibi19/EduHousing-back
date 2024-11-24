package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.Collegedto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@Tag(name = "college")
@RequestMapping("/api")
public interface CollegeApi {
    @PostMapping(value = APP_ROOT+"/college/admin/create/{city_id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save college", description = "Save college with the specified city ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "college saved successfully "),
            @ApiResponse(responseCode = "400",description = "the object college is not valid !!!")
    })
    public Collegedto save(@RequestBody Collegedto collegedto,@PathVariable("city_id") Integer cityId);
    @GetMapping(value = APP_ROOT+"/college/{college_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one college", description = "search college with the specified  ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "college found"),
            @ApiResponse(responseCode = "404",description = "college not found !!!")
    })
    public Collegedto findById(@PathVariable("college_id") Integer Id);
    @GetMapping(value = APP_ROOT+"/college/name/{college_name}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one college", description = "search college with the specified  name ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "college found"),
            @ApiResponse(responseCode = "404",description = "college not found !!!")
    })
    public Collegedto findByName(@PathVariable("college_name") String name);
    @GetMapping(value = APP_ROOT+"/college/findAll",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of colleges  ", description = "search all colleges ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "college list found")

    })
    public List<Collegedto> findAll();
    @GetMapping(value = APP_ROOT+"/college/city/{city_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one college", description = "search college with the specified  city id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "college found"),
            @ApiResponse(responseCode = "404",description = "college not found !!!")
    })
    public List<Collegedto> findByCityId(@PathVariable("city_id") Integer id);
    @PutMapping(value = APP_ROOT+"/college/admin/update",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update college... ",description = "you can only update the name.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "college updated successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public Collegedto update(@RequestBody Collegedto collegedto);
    @DeleteMapping(value = APP_ROOT+"/college/admin/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete college by id... ",description = "needs an existing college id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "college deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteById(@PathVariable("id") Integer id);
    @GetMapping(value = APP_ROOT+"/college/apartment/{apartment_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of colleges", description = "search colleges with the the same city as the apartment provided id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "college list found"),

    })
    public List<Collegedto> findCollegesByApartmentAndSameCity(@PathVariable("apartment_id") Integer apartmentId);

    @GetMapping(value = APP_ROOT+"/college/student/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search college id", description = "search college id with the provided student id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "college list found"),

    })
    public Integer findCollegesByStudentId(@PathVariable("id") Integer apartmentId);
}
