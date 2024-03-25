package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.RentalDetailsdto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@Tag(name = "rentalDetails")
@RequestMapping("/api")
public interface RentalDetailsApi {
    @PostMapping(value = APP_ROOT+"/rentalDetails/homeowner/create/{apartment_id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save rentalDetails", description = "Save rentalDetails with the specified apartment ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rentalDetails saved successfully "),
            @ApiResponse(responseCode = "400",description = "the object rentalDetails is not valid !!!")
    })
    public RentalDetailsdto save(@RequestBody RentalDetailsdto rentalDetailsdto, @PathVariable("apartment_id") Integer apartmentId);
    @GetMapping(value = APP_ROOT+"/rentalDetails/{rentalDetails_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one rentalDetails", description = "search rentalDetails with the specified  ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rentalDetails found"),
            @ApiResponse(responseCode = "404",description = "rentalDetails not found !!!")
    })
    public RentalDetailsdto findById(@PathVariable("rentalDetails_id") Integer id);
    @GetMapping(value = APP_ROOT+"/rentalDetails/homeowner_admin/apartment/{apartment_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of rentalDetails", description = "search rentalDetails with the specified  apartment iD ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rentalDetails list found")

    })
    public List<RentalDetailsdto> findAllByApartmentId(@PathVariable("apartment_id") Integer apartmentId);
    @GetMapping(value = APP_ROOT+"/rentalDetails/current/{price}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of rentalDetails", description = "search current rentalDetails with price less than specific value ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rentalDetails list found")

    })
    public List<RentalDetailsdto> findRentalByPriceAndIsCurrent(@PathVariable("price") Double val);
    @GetMapping(value = APP_ROOT+"/rentalDetails/startDate/{start_date}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of rentalDetails", description = "search current rentalDetails with start date after specific date ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rentalDetails list found")

    })
    public List<RentalDetailsdto> findRentalByStartDate(@PathVariable("start_date") Date val);
    @GetMapping(value = APP_ROOT+"/rentalDetails/homeowner_admin/validated/apartment/{apartment_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of rentalDetails", description = "search history of rental details by apartment ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rentalDetails list found")

    })
    public List<RentalDetailsdto> getRentalHistoryByApartment(@PathVariable("apartment_id") Integer apartmentId);
    @GetMapping(value = APP_ROOT+"/rentalDetails/period/{start_date}/{end_date}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of rentalDetails", description = "search list of rental details by periods ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rentalDetails list found")

    })
    public List<RentalDetailsdto> getRentalDetailsByPeriod(@PathVariable("start_date") Date startDate,@PathVariable("end_date") Date endDate);
    @GetMapping(value = APP_ROOT+"/rentalDetails/city/{city_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of rentalDetails", description = "search current rental details by the city of the student's college ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rentalDetails list found")

    })
    public List<RentalDetailsdto> getRentalDetailsByStudentCollegeCity(@PathVariable("city_id") Integer studentCollegeCityId);
    @GetMapping(value = APP_ROOT+"/rentalDetails/student/{student_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of rentalDetails", description = "search history of rental details by student's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "rentalDetails list found")

    })
    public List<RentalDetailsdto> getRentalHistoryByStudent(@PathVariable("student_id") Integer studentId);
    @PutMapping(value = APP_ROOT+"/rentalDetails/homeowner/update",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update rentalDetails... ",description = "you can only update monthly amount and description.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "rentalDetails updated successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public RentalDetailsdto update(@RequestBody RentalDetailsdto rentalDetailsdto);
    @DeleteMapping(value = APP_ROOT+"/rentalDetails/homeowner/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete rentalDetails by id... ",description = "needs an existing rentalDetails id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "rentalDetails deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteById(@PathVariable("id") Integer id);

}
