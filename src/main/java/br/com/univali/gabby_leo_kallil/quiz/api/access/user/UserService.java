package br.com.univali.gabby_leo_kallil.quiz.api.access.user;

import br.com.univali.gabby_leo_kallil.quiz.api.access.enum_role.EnumRole;
import br.com.univali.gabby_leo_kallil.quiz.api.access.role.Role;
import br.com.univali.gabby_leo_kallil.quiz.api.access.role.RoleService;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.DTO.UserDTOInsert;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.DTO.UserDTOResponse;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.DTO.UserDTOUpdate;
import br.com.univali.gabby_leo_kallil.quiz.component.email.EmailService;
import br.com.univali.gabby_leo_kallil.quiz.security.exception.WarningException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private User insert(UserDTOInsert dto){
        User user = new User();
        String password = generatePassword();
        user.setPassword(encryptPassword(password));
        user.setRegistration(dto.getRegistration());
        user.setActive(true);
        setValues(user, dto);
        user = userRepository.save(user);
        emailService.sendWelcome(dto.getEmail(), password);
        return user;
    }

    public User update(User user, UserDTOUpdate dto){
        setValues(user, dto.getDTOInsert());
        userRepository.updateEmail(dto.getEmail(), user.getId());
        return userRepository.save(user);
    }

    private void setValues(User user, UserDTOInsert dto) {
        if(dto.getUsername() != null)
            user.setUsername(dto.getUsername());
        if(dto.getEmail() != null)
            user.setEmail(dto.getEmail());
    }

    public User insertAdmin(UserDTOInsert dto){
        User user = insert(dto);
        user.setRoles(Collections.singleton(roleService.getByName(EnumRole.ROLE_ADMIN)));
        return userRepository.save(user);
    }

    public User insertStudent(UserDTOInsert dto){
        User user = insert(dto);
        Set<Role> enumRoleSet = new HashSet<>();
        enumRoleSet.add(roleService.getByName(EnumRole.ROLE_STUDENT));
        user.setRoles(enumRoleSet);
        return userRepository.save(user);
    }

    public User insertProfessor(UserDTOInsert dto){
        User user = insert(dto);
        Set<Role> enumRoleSet = new HashSet<>();
        enumRoleSet.add(roleService.getByName(EnumRole.ROLE_PROFESSOR));
        user.setRoles(enumRoleSet);
        return userRepository.save(user);
    }

    public List<User> findAllStudents(){
        List<User> opt = userRepository.findAllUsersByRole(BigInteger.valueOf(2));
        System.out.println(opt);
        return opt;
    }

    public User findById(Integer id){
        Optional<User> opt = userRepository.findById(id);
        if(!opt.isPresent()){
            throw new WarningException("Usuário não encontrada");
        }
        return opt.get();
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User findUserByCPF(String cpf){
        return userRepository.findByCPF(cpf);
    }

    public User findUserByUserName(String username){
        Optional<User> opt = userRepository.findByUsername(username);
        if(!opt.isPresent()){
            throw new WarningException("Usuário não encontrada");
        }
        return opt.get();
    }

    public User active(Integer userId){
        User user = findById(userId);
        user.setActive(true);
        return userRepository.save(user);
    }

    public User deactivate(Integer userId){
        User user = findById(userId);
        user.setActive(false);
        return userRepository.save(user);
    }

    public void delete(Integer userId){
        userRepository.deleteById(userId);
    }

    public void changePassword(Integer userId){
        User user = findById(userId);
        user.setPassword(encryptPassword("123456"));
        userRepository.save(user);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword(){
        /*String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);*/
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        /*String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);*/
        /*String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);*/
        String combinedChars = lowerCaseLetters.concat(numbers);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        return pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

}
