# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0-beta] - 2026-02-15

### Added

- **CORS Configuration**: Configured to accept requests from React at `localhost:3000`
- **Response DTOs**: Implemented `UserDTOResponse`, `ProductDTOResponse`, `OrderDTOResponse`
- **Global Exception Handler**: Consistent error handling with `@ControllerAdvice`
- **Custom Exceptions**: `ResourceNotFoundException` for 404 errors
- **Bean Validation**: Validations with `@Valid` on all input DTOs
- **Error Response Format**: Consistent JSON format for all errors
- **Update DTOs**: Specific DTOs for update operations
- **Complete JavaDoc**: Documentation in English for all public methods

### Changed

- Controllers now return DTOs instead of full entities
- Improved security: passwords and unnecessary relationships are not exposed
- Search endpoints use query params instead of path variables for better flexibility

### Fixed

- Resolved route conflicts in `OrderController` (path variables vs query params)
- Type correction in date and price endpoints
- Correct validation of `StatusType` in state transitions

### Security

- Passwords are not exposed in JSON responses
- DTOs prevent exposure of internal DB structure
- CORS limited to specific origins
