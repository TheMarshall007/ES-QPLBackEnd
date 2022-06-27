package br.com.univali.gabby_leo_kallil.quiz.api.access.user;

import br.com.univali.gabby_leo_kallil.quiz.api.access.user.DTO.UserDTOInsert;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.DTO.UserDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.security.security.jwt.JwtUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "vinicius-piai@hotmail.com")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping(value = "/insert_admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTOResponse> insertAdmin(@RequestBody UserDTOInsert dto){
        return new ResponseEntity<>(userService.insertAdmin(dto).getDTOResponse(), HttpStatus.OK);
    }

    @PostMapping(value = "/insert_professor", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTOResponse> insertProfessor(@RequestBody UserDTOInsert dto){
        return new ResponseEntity<>(userService.insertProfessor(dto).getDTOResponse(), HttpStatus.OK);
    }

    @PostMapping(value = "/insert_student", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSOR')")
    public ResponseEntity<UserDTOResponse> insertManager(@RequestBody UserDTOInsert dto){
        return new ResponseEntity<>(userService.insertStudent(dto).getDTOResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/get_students", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSOR')")
    public ResponseEntity<List<User>> getStudents(){
        return new ResponseEntity<>(userService.findAllStudents(),HttpStatus.OK);
    }

    @GetMapping(value = "/find_by_email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTOResponse> findByEmail(@RequestParam String email){
        return new ResponseEntity<>(userService.findUserByEmail(email).getDTOResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/find_by_username", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTOResponse> findUserByUserName(@RequestParam String username){
        return new ResponseEntity<>(userService.findUserByUserName(username).getDTOResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/find_current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTOResponse> findById(@RequestHeader(name="Authorization") String token){
        return new ResponseEntity<>(userService.findById(jwtUtils.getUserIdFromJwtToken(token)).getDTOResponse(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/has_email_available", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> hasEmailAvailable(@RequestParam String email){
        return new ResponseEntity<>(userService.findUserByEmail(email) == null, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/has_cpf_available", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> hasCPFAvailable(@RequestParam String cpf){
        return new ResponseEntity<>(userService.findUserByCPF(cpf) == null, HttpStatus.OK);
    }

    @CrossOrigin
    @PatchMapping(value = "/activate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTOResponse> activate(@RequestParam Integer userId){
        return ResponseEntity.ok(userService.active(userId).getDTOResponse());
    }

    @CrossOrigin
    @PatchMapping(value = "/deactivate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTOResponse> deactivate(@RequestParam Integer userId){
        return ResponseEntity.ok(userService.deactivate(userId).getDTOResponse());
    }

    @CrossOrigin
    @PatchMapping(value = "/change_password", produces = MediaType.APPLICATION_JSON_VALUE)
    public void changePassword(@RequestParam Integer userId){
        userService.changePassword(userId);
    }

}
