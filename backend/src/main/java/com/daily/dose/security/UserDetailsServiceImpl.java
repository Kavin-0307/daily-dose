package com.daily.dose.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.daily.dose.entity.Staff;
import com.daily.dose.repository.StaffRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StaffRepository staffRepository;

    public UserDetailsServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Staff staff = staffRepository.findByStaffEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Staff not found with email: " + email));

        return new StaffDetails(staff);
    }
}
/*
 * SECURITY INTEGRATION NOTES
 *
 * This class acts as the authentication bridge between Spring Security
 * and the application's Staff domain.
 *
 * Key points:
 * - Uses staff email as the username for authentication
 * - Delegates password validation to Spring Security via UserDetails
 * - Returns a StaffDetails wrapper, not the entity directly
 *
 * Design assumptions:
 * - Staff email is globally unique
 * - Inactive staff handling is expected to be enforced
 *   inside StaffDetails (isEnabled / isAccountNonLocked)
 * - Authorization (roles/permissions) is NOT handled here
 *
 * This class must remain lightweight and side-effect free.
 */
