package com.bank.bank;

import com.bank.bank.models.Admin;
import com.bank.bank.models.utils.Role;
import com.bank.bank.models.utils.User;
import com.bank.bank.repositories.AdminRepository;
import com.bank.bank.repositories.RoleRepository;
import com.bank.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BankApplication implements CommandLineRunner {

	@Autowired
	AdminRepository adminRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (userRepository.findById(1L).isEmpty()) {
			Admin admin = new Admin("admin", "admin", "Admin");
			createAdminUser(admin);
		}
		if (roleRepository.findByRole("USER").isEmpty()) {
			List<User> users = new ArrayList<>();
			Role role = roleRepository.save(new Role("USER", users));
		}
	}

	public void createAdminUser(Admin admin) {
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		admin = adminRepository.save(admin);
		List<User> admins = new ArrayList<>();
		admins.add(admin);
		Role role = roleRepository.save(new Role("ADMIN", admins));
	}
}
