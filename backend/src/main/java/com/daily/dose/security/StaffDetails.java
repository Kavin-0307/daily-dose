package com.daily.dose.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.daily.dose.entity.Staff;

public class StaffDetails implements UserDetails {

    private final Staff staff;

    public StaffDetails(Staff staff) {
        this.staff = staff;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Keep simple for now
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return staff.getStaffPasswordHash();
    }

    @Override
    public String getUsername() {
        return staff.getStaffEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return staff.isStaffActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return staff.isStaffActive();
    }

    public Staff getStaff() {
        return staff;
    }
}
/*
 * SECURITY ACCOUNT MODEL NOTES
 *
 * This class adapts the Staff entity to Spring Security's UserDetails contract.
 *
 * Key behaviors enforced here:
 * - Authentication uses staff email as username
 * - Password is sourced from the stored hash only
 * - Account lock & enablement are directly tied to staffActive
 *
 * Design decisions:
 * - No roles/authorities are exposed yet (empty authority list)
 * - Account expiration and credential expiration are ignored for now
 *
 * IMPORTANT:
 * - Deactivated staff (staffActive = false) cannot authenticate
 *   due to isAccountNonLocked() and isEnabled() checks.
 
 */

