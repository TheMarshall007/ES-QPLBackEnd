package br.com.univali.gabby_leo_kallil.quiz.setup;

import br.com.univali.gabby_leo_kallil.quiz.api.access.enum_role.EnumRole;
import br.com.univali.gabby_leo_kallil.quiz.api.access.role.Role;
import br.com.univali.gabby_leo_kallil.quiz.api.access.role.RoleRepository;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.User;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class SetupData implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) {
            return;
        }
        insertRoles();
        insertAdmin();

        insertProfessor();
        alreadySetup = true;
    }

    private void insertAdmin(){
        User user = userRepository.findByEmail("vinicius-piai@hotmail.com");
        if(user == null){
            user = new User();
            user.setUsername("Vinicius de Aquino Piai");
            user.setEmail("vinicius-piai@hotmail.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRegistration("098.912.589-04");
            Set<Role> enumRoleSet = new HashSet<>();
            enumRoleSet.add(roleRepository.findByName(EnumRole.ROLE_ADMIN));
            user.setRoles(enumRoleSet);
            user.setActive(true);
            userRepository.save(user);
        }
    }

    private void insertProfessor(){
        User user = userRepository.findByEmail("leonardovinicius_silva@hotmail.com");
        if(user == null){
            user = new User();
            user.setUsername("Leonardo Vinicius");
            user.setEmail("leonardovinicius_silva@hotmail.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRegistration("058.932.545-04");
            Set<Role> enumRoleSet = new HashSet<>();
            enumRoleSet.add(roleRepository.findByName(EnumRole.ROLE_PROFESSOR));
            user.setRoles(enumRoleSet);
            user.setActive(true);
            userRepository.save(user);
        }
    }

    private void insertRoles(){
        List<EnumRole> roleList = Arrays.asList(EnumRole.ROLE_PROFESSOR, EnumRole.ROLE_STUDENT, EnumRole.ROLE_ADMIN);
        for(EnumRole enumRole : roleList){
            Role role = roleRepository.findByName(enumRole);
            if(role == null){
                role = new Role();
                role.setName(enumRole);
                roleRepository.save(role);
            }
        }
    }

}
