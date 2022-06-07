package br.com.univali.gabby_leo_kallil.quiz.api.access.role;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@SecurityRequirement(name = "vinicius-piai@hotmail.com")
public class RoleController {
}
