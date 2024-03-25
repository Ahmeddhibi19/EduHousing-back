package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.Homeownerdto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@Tag(name = "homeowner")
@RequestMapping("/api")
public interface HomeownerApi {
    @PostMapping(value = APP_ROOT+"/homeowner/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save homeowner", description = "Save homeowner")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "homeowner saved successfully "),
            @ApiResponse(responseCode = "400",description = "the object homeowner is not valid !!!")
    })
    public Homeownerdto save(@RequestBody Homeownerdto homeownerdto);
    @GetMapping(value = APP_ROOT+"/homeowner/{homeowner_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search homeowner by Id... ",description = "needs the homeowner Id.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "homeowner found"),
            @ApiResponse(responseCode = "404",description = "no homeowner found with the provided id !!!")
    })
    public Homeownerdto findById(@PathVariable("homeowner_id") Integer homeownerId);
    @GetMapping(value = APP_ROOT+"/homeowner/email/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search homeowner by email... ",description = "needs the homeowner email.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "homeowner found"),
            @ApiResponse(responseCode = "404",description = "no homeowner found with the provided email !!!")
    })
    public Homeownerdto findByEmail(@PathVariable("email") String email);
    @GetMapping(value = APP_ROOT+"/homeowner/firstName/{first_name}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search homeowners list by first name... ",description = "needs first name.."/*,responseContainer = "list<StudentDto>"*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "homeowners list found"),

    })
    public List<Homeownerdto> findByFirstName(@PathVariable("first_name") String firstName);
    @GetMapping(value = APP_ROOT+"/homeowner/admin/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "find all homeowners.. ",description = "return list of all existing homeowners .."/*,responseContainer = "list<StudentDto>"*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "homeowners list found"),

    })
    public List<Homeownerdto> findAll();
    @GetMapping(value = APP_ROOT+"/homeowner/phoneNumber/{phone_number}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search homeowner by phone number... ",description = "needs the homeowner phone number.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "homeowner found"),
            @ApiResponse(responseCode = "404",description = "no homeowner found with the provided phone number !!!")
    })
    public Homeownerdto findByPhoneNumber(@PathVariable("phone_number") String phoneNumber);
    @PutMapping(value = APP_ROOT+"/homeowner/homeowner/update",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update homeowner... ",description = "you can only update first name, last name, phone number and address.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "homeowner updated successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public Homeownerdto update(@RequestBody Homeownerdto homeownerdto);
    @DeleteMapping(value = APP_ROOT+"/homeowner/admin/delete_by_phone/{phone_number}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete homeowner by phone number... ",description = "needs an existing homeowner phone number.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "homeowner deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    void deleteByPhoneNumber(@PathVariable("phone_number") String phoneNumber);
    @DeleteMapping(value = APP_ROOT+"/homeowner/admin/delete_by_email/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete homeowner by email... ",description = "needs an existing homeowner email.."/*response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "homeowner deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    void deleteByEmail(@PathVariable("email") String email);

    @DeleteMapping(value = APP_ROOT+"/homeowner/admin/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete homeowner by id... ",description = "needs an existing homeowner id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "homeowner deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    void deleteById(@PathVariable("id") Integer id);
}
