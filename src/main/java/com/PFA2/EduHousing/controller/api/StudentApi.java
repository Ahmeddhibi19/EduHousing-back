package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.Studentdto;
/*import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;*/
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

import java.util.List;
@Tag(name = "student")
@RequestMapping("/api")
public interface StudentApi {
    @PostMapping(value = APP_ROOT+"/student/create/{college_id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save student", description = "Save student with the specified college ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "student saved successfully "),
            @ApiResponse(responseCode = "400",description = "the object student is not valid !!!")
    })
    String save(@RequestBody Studentdto studentdto ,@PathVariable("college_id") Integer collegeId);


    @GetMapping(value = APP_ROOT+"/student/{studentId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search student by Id... ",description = "needs the student Id.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "student found"),
            @ApiResponse(responseCode = "404",description = "no student found with the provided id !!!")
                    })
    Studentdto findById(@PathVariable("studentId") Integer id);

    @GetMapping(value = APP_ROOT+"/student/email/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search student by email... ",description = "needs the student email.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "student found"),
            @ApiResponse(responseCode = "404",description = "no student found with the provided email !!!")
    })
    Studentdto findByEmail(@PathVariable("email") String email);

    @GetMapping(value = APP_ROOT+"/student/firstName/{first_name}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search students list by first name... ",description = "needs first name.."/*,responseContainer = "list<StudentDto>"*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "students list found"),

    })
    List<Studentdto> findByFirstName(@PathVariable("first_name") String firstName);

    @GetMapping(value = APP_ROOT+"/student/phoneNumber/{phone_number}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search student by phone number... ",description = "needs the student phone number.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "student found"),
            @ApiResponse(responseCode = "404",description = "no student found with the provided phone number !!!")
    })
    Studentdto findByPhoneNumber(@PathVariable("phone_number") String phoneNumber);

    @PutMapping(value = APP_ROOT+"/student/update",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update student... ",description = "you can only update first name, last name, phone number and address.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "student updated successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    Studentdto update(@RequestBody Studentdto studentdto);
    @PutMapping(value = APP_ROOT+"/student/update_college/{studentId}/{collegeId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update student college... ",description = "needs only the id of the student/id of the new college.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "student's college updated successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    Studentdto updateCollege(@PathVariable("studentId") Integer studentId,@PathVariable("collegeId") Integer collegeId);

    @DeleteMapping(value = APP_ROOT+"/student/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete student by id... ",description = "needs an existing student id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "student deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    void deleteById(@PathVariable("id") Integer id);

    @DeleteMapping(value = APP_ROOT+"/student/delete_by_phone/{phone_number}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete student by phone number... ",description = "needs an existing student phone number.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "student deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    void deleteByPhoneNumber(@PathVariable("phone_number") String phoneNumber);

    @DeleteMapping(value = APP_ROOT+"/student/delete_by_email/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete student by email... ",description = "needs an existing student email.."/*response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "student deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    void deleteByEmail(@PathVariable("email") String email);

    @GetMapping(value = APP_ROOT+"/student/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "find all students.. ",description = "return list of all existing student .."/*,responseContainer = "list<StudentDto>"*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "student list found"),

    })
    List<Studentdto> findAll();

    @GetMapping(value = APP_ROOT+"/student/college/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "find all students by college Id.. ",description = "needs an existing college Id .."/*responseContainer = "list<StudentDto>"*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "student list found"),

    })
    List<Studentdto> findAllByCollegeId(@PathVariable("id") Integer collegeId);
}
