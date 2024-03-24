package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.Admindto;
/*import com.PFA2.EduHousing.dto.Studentdto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;*/
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;
@Tag(name = "admin")
@RequestMapping("/api")
public interface AdminApi {

    @PostMapping(value = APP_ROOT+"/admin/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "save admin... ",description = "needs admin object.."/*,response = Admindto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "admin saved successfully "),
            @ApiResponse(responseCode = "400",description = "the object admin is not valid !!!")
    })
    public String save(@RequestBody Admindto admindto);
    @GetMapping(value = APP_ROOT+"/admin/{adminId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search admin by Id... ",description = "needs the admin Id.."/*,response = Admindto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "admin found"),
            @ApiResponse(responseCode = "404",description = "no admin found with the provided id !!!")
    })
    public Admindto findById(@PathVariable("adminId") Integer id);
    @GetMapping(value = APP_ROOT+"/admin/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search admin by email... ",description = "needs the admin email.."/*,response = Admindto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "admin found"),
            @ApiResponse(responseCode = "404",description = "no admin found with the provided email !!!")
    })
    public Admindto findByEmail(@PathVariable("email") String Email);
    @GetMapping(value = APP_ROOT+"/admin/{phone_number}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search admin by phone number... ",description = "needs the admin phone number.."/*,response = Admindto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "admin found"),
            @ApiResponse(responseCode = "404",description = "no admin found with the provided phone number !!!")
    })
    public Admindto findByPhoneNumber(@PathVariable("phone_number") String phoneNumber);
    @GetMapping(value = APP_ROOT+"/admin/{first_name}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search admin list by first name... ",description = "needs a first name.."/*,responseContainer = "list<Admindto>"*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "admin list found"),

    })
    public List<Admindto> findByFirstName(@PathVariable("first_name") String firstName);
    @GetMapping(value = APP_ROOT+"/admin/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search all admins list... ",description = "return list of all admins.."/*,responseContainer = "list<Admindto>"*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "all admins..."),

    })
    public List<Admindto> findAll();
    @PutMapping(value = APP_ROOT+"/admin/update",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update student... ",description = "you can only update first name, last name, phone number and address.."/*,response = Admindto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "admin updated successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public Admindto update(@RequestBody Admindto admindto);
    @DeleteMapping (value = APP_ROOT+"/admin/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete admin by id... ",description = "needs an existing admin id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "admin deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteById(@PathVariable("id") Integer id);
    @DeleteMapping (value = APP_ROOT+"/admin/delete_by_phone_number/{phone_number}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete admin by phone number... ",description = "needs an existing admin phone number.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "admin deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteByPhoneNumber(@PathVariable("phone_number") String phoneNumber);
    @DeleteMapping (value = APP_ROOT+"/admin/delete_by_email/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete admin by email... ",description = "needs an existing admin email.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "admin deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteByEmail(@PathVariable("email") String email);

}
