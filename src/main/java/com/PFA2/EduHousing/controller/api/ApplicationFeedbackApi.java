package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.ApplicationFeedbackdto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@Tag(name = "ApplicationFeedback")
@RequestMapping("/api")
public interface ApplicationFeedbackApi {
    @PostMapping(value = APP_ROOT+"/applicationFeedback/create/{user_id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save application feedback", description = "Save application feedback with the specified user ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "application feedback saved successfully "),
            @ApiResponse(responseCode = "400",description = "the object application feedback is not valid !!!")
    })
    public ApplicationFeedbackdto save(
           @RequestBody ApplicationFeedbackdto applicationFeedbackdto,
            @PathVariable("user_id") Integer userId
    );
    @GetMapping(value = APP_ROOT+"/applicationFeedback/{user_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search one application feedback", description = "search application feedback with the specified user  ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "application feedback  found"),
            @ApiResponse(responseCode = "404",description = "application feedback  not found !!!")
    })
    public ApplicationFeedbackdto findAppFeedbackByUser(@PathVariable("user_id") Integer userId);

    @GetMapping(value = APP_ROOT+"/applicationFeedback/findAll",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of application Feedback  ", description = "search all application Feedback ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment list found")

    })
    public List<ApplicationFeedbackdto> findAll();
    @GetMapping(value = APP_ROOT+"/applicationFeedback/userName/{user_name}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of application Feedback  ", description = "search all application Feedback by userName")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "application Feedback list found")

    })
    public List<ApplicationFeedbackdto> findAllByUserName(@PathVariable("user_name") String userName);
    @PutMapping(value = APP_ROOT+"/applicationFeedback/update",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "update application Feedback... ",description = "you can only update rating and content.."/*,response = Studentdto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "application Feedback updated successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public ApplicationFeedbackdto update(@RequestBody ApplicationFeedbackdto applicationFeedbackdto);
    @DeleteMapping(value = APP_ROOT+"/applicationFeedback/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete application Feedback by id... ",description = "needs an existing application Feedback id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "application Feedback deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteById(@PathVariable("id") Integer id);
}
