// package com.melpy.perpus.config;

// import io.swagger.v3.oas.models.Operation;
// import io.swagger.v3.oas.models.PathItem;
// import io.swagger.v3.oas.models.Paths;
// import io.swagger.v3.oas.models.responses.ApiResponse;
// import io.swagger.v3.oas.models.responses.ApiResponses;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class OpenApiConfig {

// @Bean
// public OpenApiCustomiser memberApiCustomiser() {
// return openApi -> {
// Paths paths = openApi.getPaths();

// // Dokumentasi untuk GET /member
// PathItem pathItem = paths.get("/member");
// if (pathItem != null) {
// Operation getOperation = pathItem.getGet();
// if (getOperation != null) {
// getOperation.setSummary("Get all members")
// .setDescription("Retrieve a list of all members in the library");

// ApiResponses responses = getOperation.getResponses();
// responses.addApiResponse("200", new ApiResponse()
// .description("List of members retrieved successfully"));
// }
// }

// // Dokumentasi untuk POST /member
// if (pathItem != null) {
// Operation postOperation = new Operation();
// postOperation.setSummary("Add a new member")
// .setDescription("Create a new member in the library system");

// postOperation.setResponses(new ApiResponses().addApiResponse("201", new
// ApiResponse()
// .description("Member created successfully")));

// pathItem.post(postOperation);
// }

// // Dokumentasi untuk GET /member/{id}
// PathItem memberByIdPath = paths.get("/member/{id}");
// if (memberByIdPath != null) {
// Operation getByIdOperation = memberByIdPath.getGet();
// if (getByIdOperation != null) {
// getByIdOperation.setSummary("Get member by ID")
// .setDescription("Retrieve a member's details using their ID");

// ApiResponses responses = getByIdOperation.getResponses();
// responses.addApiResponse("200", new ApiResponse()
// .description("Member details retrieved successfully"));
// responses.addApiResponse("404", new ApiResponse()
// .description("Member not found"));
// }

// // Dokumentasi untuk PATCH /member/{id}
// Operation patchOperation = new Operation();
// patchOperation.setSummary("Update member details")
// .setDescription("Update specific details of an existing member");

// patchOperation.setResponses(new ApiResponses().addApiResponse("200", new
// ApiResponse()
// .description("Member updated successfully")));

// memberByIdPath.patch(patchOperation);
// }
// };
// }
// }