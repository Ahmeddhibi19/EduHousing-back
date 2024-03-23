package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.Requestdto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;
@Tag(name = "request")
@RequestMapping("/api")
public interface RequestApi {
    @PostMapping(value = APP_ROOT+"/request/create/{student_id}/{rentalDetails_id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save request", description = "Save request with the specified student ID and rental details ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "request saved successfully "),
            @ApiResponse(responseCode = "400",description = "the object request is not valid !!!")
    })
    public Requestdto save(@RequestBody Requestdto requestdto, @PathVariable("student_id") Integer studentId,@PathVariable("rentalDetails_id") Integer rentalDetailsId);
    @GetMapping(value = APP_ROOT+"/request/id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one request", description = "search request with the specified  ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "request found"),
            @ApiResponse(responseCode = "404",description = "request not found !!!")
    })
    public Requestdto findById(@PathVariable("id") Integer id);
    @GetMapping(value = APP_ROOT+"/request/student/{student_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of requests", description = "search requests with the specified  student iD ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "request list found")

    })
    public List<Requestdto> findAllByStudentId(@PathVariable("student_id") Integer studentId);
    @GetMapping(value = APP_ROOT+"/request/student_rentalDetails/{student_id}/{rentalDetails_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one request", description = "search request with the specified student ID and rental details id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "request found"),
            @ApiResponse(responseCode = "404",description = "request not found !!!")
    })
    public Requestdto getRequestByStudentIdAndRentalDetailsId(@PathVariable("student_id") Integer studentId,@PathVariable("rentalDetails_id") Integer rentalDetailsId);
    @GetMapping(value = APP_ROOT+"/request/rentalDetails/{rentalDetails_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of requests", description = "search requests with the specified  rentalDetails id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "request list found")

    })
    public List<Requestdto> findAllByRentalDetailsId(@PathVariable("rentalDetails_id") Integer rentalDetailsId);
    @GetMapping(value = APP_ROOT+"/request/validated_rentalDetails/{rentalDetails_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of requests", description = "search validated requests with the specified  rentalDetails id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "request list found")

    })
    public Requestdto getValidatedRequestByRentalDetailsId(@PathVariable("rentalDetails_id") Integer rentalDetailsId);
    @GetMapping(value = APP_ROOT+"/request/validated_student/{student_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of requests", description = "search validated requests with the specified  student id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "request list found")

    })
    public List<Requestdto> getAllValidatedRequestsByStudentId(@PathVariable("student_id") Integer studentID);
    @PutMapping(value = APP_ROOT+"/request/accept/{request_id}/{rentalDetails_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "accpet one request", description = "accept request with the specified request ID and rental details id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "request accepted successfully"),
            @ApiResponse(responseCode = "404",description = "something went wrong !!!")
    })
    public void acceptRequest(@PathVariable("request_id") Integer requestId,@PathVariable("rentalDetails_id") Integer rentalDetailsId);
    @PutMapping(value = APP_ROOT+"/request/removeacceptance/{request_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "remove acceptance from one request", description = "remove acceptance from request with the specified request ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "acceptance removed successfully"),
            @ApiResponse(responseCode = "404",description = "something went wrong !!!")
    })
    public void removeAcceptance(@PathVariable("request_id") Integer requestId);
    @PutMapping(value = APP_ROOT+"/request/validate/{request_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "validate one request", description = "validate request with the specified request ID and rental details id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "request accepted successfully"),
            @ApiResponse(responseCode = "404",description = "something went wrong !!!")
    })
    public void validateRequest(@PathVariable("request_id") Integer requestId);
    @PutMapping(value = APP_ROOT+"/request/update",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update one request", description = "update request")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "request updated successfully"),
            @ApiResponse(responseCode = "404",description = "something went wrong !!!")
    })
    public Requestdto update(@RequestBody Requestdto requestdto);
    @DeleteMapping(value = APP_ROOT+"/request/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete request by id... ",description = "needs an existing request id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "apartment deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteById(@PathVariable("id") Integer id);
    @PutMapping(value = APP_ROOT+"/request/reject/{request_id}/{rentalDetails_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "reject one request", description = "reject request with the specified id and rental details id")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "request rejected successfully"),
            @ApiResponse(responseCode = "404",description = "something went wrong !!!")
    })
    public void rejectRequest(@PathVariable("request_id") Integer requestId,@PathVariable("rentalDetails_id") Integer rentalDetailsId);
}
