package br.com.univali.gabby_leo_kallil.quiz.api.access.role;

import br.com.univali.gabby_leo_kallil.quiz.api.access.enum_role.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role insert(EnumRole name){
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

    public Role getByName(EnumRole name){
        return roleRepository.findByName(name);
    }
}
