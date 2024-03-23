package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.RentalFeedbackdto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@Tag(name = "rental feedback")
@RequestMapping("/api")
public interface RentalFeedbackApi {
    @PostMapping(value = APP_ROOT+"/rentalfeedback/create/{apartment_id}/{student_id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save rental feedback", description = "Save rental feedback with the specified apartment ID and student ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rental feedback saved successfully "),
            @ApiResponse(responseCode = "400",description = "the object rental feedback is not valid !!!")
    })
    public RentalFeedbackdto save(@RequestBody RentalFeedbackdto rentalFeedbackdto, @PathVariable("student_id") Integer studentId,@PathVariable("apartment_id") Integer apartmentId);
    @GetMapping(value = APP_ROOT+"/rentalfeedback/student_apartment/{student_id}/{apartment_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one rental feedback", description = "search rental feedback with the specified student id and apartment ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rental feedback found"),
            @ApiResponse(responseCode = "404",description = "rental feedback not found !!!")
    })
    public RentalFeedbackdto findByStudentAndApartment(@PathVariable("student_id") Integer studentId,@PathVariable("apartment_id") Integer apartmentId);
    @GetMapping(value = APP_ROOT+"/rentalfeedback/id/{rentalfeedback_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one rental feedback", description = "search rental feedback with the specified  ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rental feedback found"),
            @ApiResponse(responseCode = "404",description = "rental feedback not found !!!")
    })
    public RentalFeedbackdto findById(@PathVariable("rentalfeedback_id") Integer id);
    @GetMapping(value = APP_ROOT+"/rentalfeedback/student/{student_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of rental feedback", description = "search rental feedbacks with the specified  student iD ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rental feedback list found")

    })
    public List<RentalFeedbackdto> findAllByStudentId(@PathVariable("student_id") Integer studentId);
    @GetMapping(value = APP_ROOT+"/rentalfeedback/apartment/{apartment_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of rental feedback", description = "search rental feedbacks with the specified  apartment iD ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rental feedback list found")

    })
    public List<RentalFeedbackdto> findAllByApartmentId(@PathVariable("apartment_id") Integer apartmentId);
    @GetMapping(value = APP_ROOT+"/rentalfeedback/rating_less_than_apartment/{apartment_id}/{value}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of rental feedback", description = "search rental feedbacks with the specified  apartment iD and rating less than specified value ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rental feedback list found")

    })
    public List<RentalFeedbackdto> getRentalFeedbackByApartmentIdAndRatingLessThan(@PathVariable("apartment_id") Integer apartmentId,@PathVariable("value") Integer value);
    @GetMapping(value = APP_ROOT+"/rentalfeedback/rating_greater_than_apartment/{apartment_id}/{value}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of rental feedback", description = "search rental feedbacks with the specified  apartment iD and rating greater than specified value ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rental feedback list found")

    })
    public List<RentalFeedbackdto> getRentalFeedbackByApartmentIdAndRatingGreaterThan(@PathVariable("apartment_id") Integer apartmentId,@PathVariable("value") Integer value);
    @DeleteMapping(value = APP_ROOT+"/rentalfeedback/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete rental feedback by id... ",description = "needs an existing rental feedback id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "rental feedback deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteById(@PathVariable("id") Integer id);
    @PutMapping(value = APP_ROOT+"/rentalfeedback/update",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update rental feedback... ",description = "you can only update content and rating.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "rental feedback updated successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public RentalFeedbackdto update(@RequestBody RentalFeedbackdto rentalFeedbackdto);
}
